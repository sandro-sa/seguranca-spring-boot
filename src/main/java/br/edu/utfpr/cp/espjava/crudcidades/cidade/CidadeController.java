package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CidadeController {

    private final CidadeRepository repository;

    public CidadeController(final CidadeRepository repository){
        this.repository = repository;
    }
    
    @GetMapping("/")
    public String listar(Model memoria){
        memoria.addAttribute("listaCidades", this.converteCidade(repository.findAll()));
        return "/crud";
    }

    private List<Cidade> converteCidade(List<CidadeEntidade> cidades){
        return cidades.stream()
                  .map(cidade ->
                        new Cidade(
                                cidade.getNome(),
                                cidade.getEstado()))
                  .collect(Collectors.toList());
    }


    @PostMapping("/criar")
    public String criar(@Validated Cidade cidade, BindingResult validacao, Model memoria){

        if(validacao.hasErrors()){
           validacao
               .getFieldErrors()
               .forEach(
                       error -> memoria.addAttribute(error.getField(),error.getDefaultMessage())
               );
                memoria.addAttribute("nomeInformado", cidade.getNome());
                memoria.addAttribute("estadoInformado", cidade.getEstado());
                memoria.addAttribute("listaCidades", this.converteCidade(repository.findAll()));
            return "/crud";
        }else {
            repository.save(cidade.clonar());
        }

        return "redirect:/";
    }



    @GetMapping("/excluir")
    public String criar(@RequestParam String nome, @RequestParam String estado){

        var cidadeEstatadoEncontrada = repository.findByNomeAndEstado(nome, estado);

        cidadeEstatadoEncontrada.ifPresent(repository::delete);

        return "redirect:/";

    }

    @GetMapping("prepararAlterar")
    public String preparaAlterar(@RequestParam String nome, @RequestParam String estado, Model memoria){

        var cidadeAtual = repository.findByNomeAndEstado(nome,estado);

        cidadeAtual.ifPresent(cidadeEncontrada -> {
            memoria.addAttribute("cidadeAtual", cidadeEncontrada);
            memoria.addAttribute("listaCidades", this.converteCidade(repository.findAll()));
        });

        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(@RequestParam String nomeAtual, @RequestParam String estadoAtual, Cidade cidade, BindingResult validacao, Model memoria){

        var cidadeAtual = repository.findByNomeAndEstado(nomeAtual, estadoAtual);

        if(cidadeAtual.isPresent()){

            var cidadeEncontrada = cidadeAtual.get();
            cidadeEncontrada.setNome(cidade.getNome());
            cidadeEncontrada.setEstado(cidade.getEstado());

            repository.saveAndFlush(cidadeEncontrada);
        }

        return "redirect:/";
    }

}

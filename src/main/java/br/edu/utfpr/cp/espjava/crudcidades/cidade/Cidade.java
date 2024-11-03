package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class Cidade {

    @NotBlank(message="{app.cidade.black}")
    @Size(min=4, max=60,message="{app.cidade.size}")
    private final String nome;

    @NotBlank(message="{app.estado.black}")
    @Size(min=2, max=2,message="{app.estado.size}")
    private final String estado;

    public Cidade(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }

    public CidadeEntidade clonar(){
        var cidadeEntidade = new CidadeEntidade();

        cidadeEntidade.setNome(this.getNome());
        cidadeEntidade.setEstado(this.getEstado());

        return cidadeEntidade;
    }

    public Cidade clonar(CidadeEntidade cidade){
        return new Cidade(cidade.getNome(), cidade.getEstado());
    }
}

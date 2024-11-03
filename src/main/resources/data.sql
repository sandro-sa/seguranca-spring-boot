
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    senha VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS usuario_papeis (
    usuario_id BIGINT,
    papel VARCHAR(100),
    PRIMARY KEY (usuario_id, papel),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

INSERT INTO `usuario`  VALUES (1,'Maria','$2a$10$g1M./6sp500q288Jn4qL0./ghJrsg8H0fwx3qSmh.WUTo97TCgK5a');
INSERT INTO `usuario` VALUES (2,'Ana','$2a$10$g1M./6sp500q288Jn4qL0./ghJrsg8H0fwx3qSmh.WUTo97TCgK5a');

INSERT INTO `usuario_papeis` VALUES (1, 'listar');
INSERT INTO `usuario_papeis`  VALUES (2, 'listar');
INSERT INTO `usuario_papeis`  VALUES (2, 'admin');

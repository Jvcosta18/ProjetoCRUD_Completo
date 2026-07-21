
CREATE DATABASE IF NOT EXISTS bd_aula;
USE bd_aula;


CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    rua VARCHAR(100),
    numero VARCHAR(10),
    bairro VARCHAR(100),
    cep VARCHAR(9),
    cidade VARCHAR(100),
    estado CHAR(2)
);


CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    data VARCHAR(50),
    status VARCHAR(50),
    produtos TEXT,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS pedido_produto (
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    PRIMARY KEY (pedido_id, produto_id),
    CONSTRAINT fk_pp_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_pp_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT
);



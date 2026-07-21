
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

CREATE INDEX IF NOT EXISTS idx_pedido_cliente ON pedido(cliente_id);
CREATE INDEX IF NOT EXISTS idx_pp_produto ON pedido_produto(produto_id);


INSERT INTO produto (descricao, preco, estoque) VALUES ('Milho', 20.85, 50);
INSERT INTO produto (descricao, preco, estoque) VALUES ('Picanha', 60.85, 20);
INSERT INTO produto (descricao, preco, estoque) VALUES ('Arroz', 15.50, 100);

INSERT INTO cliente (cpf, nome, email, rua, numero, bairro, cep, cidade, estado)
  VALUES ('123.456.789-00', 'João Silva', 'joao@email.com', 'Rua das Flores', '100', 'Centro', '89000-000', 'Blumenau', 'SC');
INSERT INTO cliente (cpf, nome, email, rua, numero, bairro, cep, cidade, estado)
  VALUES ('987.654.321-00', 'Maria Santos', 'maria@email.com', 'Avenida Principal', '250', 'Vila Nova', '89001-000', 'Blumenau', 'SC');


INSERT INTO pedido (cliente_id, data, status, produtos) VALUES (1, '2026-07-21', 'ABERTO', '1,2');
INSERT INTO pedido (cliente_id, data, status) VALUES (2, '2026-07-21', 'PENDENTE');
INSERT INTO pedido_produto (pedido_id, produto_id, quantidade) VALUES (2, 2, 1);
INSERT INTO pedido_produto (pedido_id, produto_id, quantidade) VALUES (2, 3, 2);


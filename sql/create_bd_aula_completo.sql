-- Script completo: create_bd_aula_completo.sql
-- Cria o banco e as tabelas usadas pelo projeto (produto, cliente, pedido, pedido_produto)
-- Compatível com MySQL / MariaDB. Ajuste para outros SGBDs se necessário.

-- ==========================================================
-- Script de criação do banco de dados usado pelo projeto
-- Banco: bd_aula (mesmo nome já configurado em ConectaDB.java)
-- ==========================================================

CREATE DATABASE IF NOT EXISTS bd_aula;

USE bd_aula;

-- Tabela produto
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL DEFAULT 0
);

-- Se a tabela "produto" já existir sem a coluna estoque, rode esta linha:
-- ALTER TABLE produto ADD COLUMN estoque INT NOT NULL DEFAULT 0;

-- Tabela cliente
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

-- ==========================================================
-- Tabelas de pedido (modelagem normalizada) - recomendada
-- ==========================================================

-- Tabela pedido: registra informações gerais do pedido
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    data VARCHAR(50),
    status VARCHAR(50),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
);

-- Tabela pedido_produto: itens do pedido (relacionamento N:N entre pedido e produto)
CREATE TABLE IF NOT EXISTS pedido_produto (
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    PRIMARY KEY (pedido_id, produto_id),
    CONSTRAINT fk_pp_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_pp_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_pedido_cliente ON pedido(cliente_id);
CREATE INDEX IF NOT EXISTS idx_pp_produto ON pedido_produto(produto_id);

-- ==========================================================
-- Observações e inserts de teste
-- ==========================================================
-- 1) A modelagem acima é relacional: cada pedido tem vários itens em `pedido_produto`.
-- 2) Se preferir uma abordagem simples (armazenar ids de produtos em uma coluna CSV), use o script
--    create_pedido_simple.sql (o projeto já inclui um DAO que grava CSV). A modelagem relacional
--    é mais recomendada para integridade e consultas.

-- Inserts de exemplo (ajuste ids conforme necessário):
INSERT INTO produto (descricao, preco, estoque) VALUES ('Milho', 20.85, 50);
INSERT INTO produto (descricao, preco, estoque) VALUES ('Picanha', 60.85, 20);
INSERT INTO cliente (cpf, nome, email, rua, numero, bairro, cep, cidade, estado)
  VALUES ('123.456.789-00', 'João Silva', 'joao@email.com', 'Rua das Flores', '100', 'Centro', '89000-000', 'Blumenau', 'SC');

-- Exemplo criando um pedido e vinculando produtos
INSERT INTO pedido (cliente_id, data, status) VALUES (1, '2026-07-16', 'ABERTO');
-- Supondo que o id do pedido acima seja 1 e produtos 1 e 2 existam
INSERT INTO pedido_produto (pedido_id, produto_id, quantidade) VALUES (1, 1, 2);
INSERT INTO pedido_produto (pedido_id, produto_id, quantidade) VALUES (1, 2, 1);

-- Se quiser limpar (usar com cuidado):
-- DROP TABLE IF EXISTS pedido_produto;
-- DROP TABLE IF EXISTS pedido;

-- FIM


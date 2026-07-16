-- ==========================================================
-- Script de criação do banco de dados usado pelo projeto
-- Banco: bd_aula (mesmo nome já configurado em ConectaDB.java)
-- ==========================================================

CREATE DATABASE IF NOT EXISTS bd_aula;

USE bd_aula;

CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

-- (Opcional) alguns registros de teste
-- INSERT INTO produto (descricao, preco) VALUES ('Milho', 20.85);
-- INSERT INTO produto (descricao, preco) VALUES ('Arroz', 25.90);

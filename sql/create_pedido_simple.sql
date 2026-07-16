-- Script: create_pedido_simple.sql
-- Descrição: cria a tabela `pedido` usando uma coluna `produtos` que armazena ids de produtos separados por vírgula.
-- Ajuste tipos e sintaxe conforme o seu SGBD (MySQL, PostgreSQL, SQLite, etc.).

-- Exemplo para MySQL / MariaDB
DROP TABLE IF EXISTS pedido;

CREATE TABLE pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cliente_id INT NOT NULL,
  data VARCHAR(50),
  status VARCHAR(50),
  produtos TEXT,
  CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
);

-- Observações:
-- 1) A coluna `produtos` armazena uma lista de ids separados por vírgula (ex: "1,2,5").
-- 2) Esta modelagem é simples, porém não normalizada. Para consultas complexas e integridade referencial completa
--    é recomendável usar uma tabela relacional (veja o script create_pedido_relacional.sql).

-- Ajuste para PostgreSQL:
-- ALTER TABLE ... auto increment uses SERIAL or IDENTITY; exemplo:
-- CREATE TABLE pedido (
--   id SERIAL PRIMARY KEY,
--   cliente_id INT,
--   data TEXT,
--   status TEXT,
--   produtos TEXT,
--   FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
-- );


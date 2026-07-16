package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.ICRUD;
import modelos.Produto;
import utils.ConectaDB;

public class ProdutoDao implements ICRUD {

	@Override
	public Produto salvar(Produto prod) {

		String sql = "INSERT INTO produto (descricao, preco, estoque) VALUES (?, ?, ?)";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, prod.getDescricao());
			stmt.setDouble(2, prod.getPreco());
			stmt.setInt(3, prod.getEstoque());

			stmt.execute();

			// Recupera o ID gerado automaticamente pelo banco (AUTO_INCREMENT)
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					prod.setId(rs.getInt(1));
				}
			}

			System.out.println("Produto cadastrado!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return prod;
	}

	@Override
	public void deletar(int id) {

		String sql = "DELETE FROM produto WHERE id = ?";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.execute();

			System.out.println("Produto removido!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void alterar(Produto prod) {

		String sql = "UPDATE produto SET descricao = ?, preco = ?, estoque = ? WHERE id = ?";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, prod.getDescricao());
			stmt.setDouble(2, prod.getPreco());
			stmt.setInt(3, prod.getEstoque());
			stmt.setInt(4, prod.getId());

			stmt.execute();

			System.out.println("Atualizado!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Produto consultar(int id) {

		String sql = "SELECT * FROM produto WHERE id = ?";
		Produto produto = null;

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					produto = new Produto(
							rs.getInt("id"),
							rs.getString("descricao"),
							rs.getDouble("preco"),
							rs.getInt("estoque")
					);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return produto;
	}

	@Override
	public List<Produto> consultar() {

		String sql = "SELECT * FROM produto";
		List<Produto> produtos = new ArrayList<>();

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Produto produto = new Produto(
						rs.getInt("id"),
						rs.getString("descricao"),
						rs.getDouble("preco"),
						rs.getInt("estoque")
				);
				produtos.add(produto);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return produtos;
	}

}

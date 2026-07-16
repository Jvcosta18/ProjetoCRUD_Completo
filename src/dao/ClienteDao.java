package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.ICRUD;
import modelos.Cliente;
import utils.ConectaDB;

public class ClienteDao implements ICRUD<Cliente> {

	@Override
	public Cliente salvar(Cliente cli) {

		String sql = "INSERT INTO cliente (cpf, nome, email, rua, numero, bairro, cep, cidade, estado) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, cli.getCpf());
			stmt.setString(2, cli.getNome());
			stmt.setString(3, cli.getEmail());
			stmt.setString(4, cli.getRua());
			stmt.setString(5, cli.getNumero());
			stmt.setString(6, cli.getBairro());
			stmt.setString(7, cli.getCep());
			stmt.setString(8, cli.getCidade());
			stmt.setString(9, cli.getEstado());

			stmt.execute();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					cli.setId(rs.getInt(1));
				}
			}

			System.out.println("Cliente cadastrado!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return cli;
	}

	@Override
	public void deletar(int id) {

		String sql = "DELETE FROM cliente WHERE id = ?";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.execute();

			System.out.println("Cliente removido!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void alterar(Cliente cli) {

		String sql = "UPDATE cliente SET cpf = ?, nome = ?, email = ?, rua = ?, numero = ?, " +
				"bairro = ?, cep = ?, cidade = ?, estado = ? WHERE id = ?";

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, cli.getCpf());
			stmt.setString(2, cli.getNome());
			stmt.setString(3, cli.getEmail());
			stmt.setString(4, cli.getRua());
			stmt.setString(5, cli.getNumero());
			stmt.setString(6, cli.getBairro());
			stmt.setString(7, cli.getCep());
			stmt.setString(8, cli.getCidade());
			stmt.setString(9, cli.getEstado());
			stmt.setInt(10, cli.getId());

			stmt.execute();

			System.out.println("Atualizado!");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Cliente consultar(int id) {

		String sql = "SELECT * FROM cliente WHERE id = ?";
		Cliente cliente = null;

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					cliente = montarCliente(rs);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return cliente;
	}

	@Override
	public List<Cliente> consultar() {

		String sql = "SELECT * FROM cliente";
		List<Cliente> clientes = new ArrayList<>();

		try (Connection con = ConectaDB.conectar();
			 PreparedStatement stmt = con.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				clientes.add(montarCliente(rs));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return clientes;
	}

	private Cliente montarCliente(ResultSet rs) throws Exception {
		return new Cliente(
				rs.getInt("id"),
				rs.getString("cpf"),
				rs.getString("nome"),
				rs.getString("email"),
				rs.getString("rua"),
				rs.getString("numero"),
				rs.getString("bairro"),
				rs.getString("cep"),
				rs.getString("cidade"),
				rs.getString("estado")
		);
	}

}

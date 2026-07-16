package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import interfaces.ICRUD;
import modelos.Pedido;
import modelos.Produto;
import modelos.Cliente;
import utils.ConectaDB;

public class PedidoDao implements ICRUD<Pedido> {

    private ProdutoDao produtoDao = new ProdutoDao();
    private ClienteDao clienteDao = new ClienteDao();

    @Override
    public Pedido salvar(Pedido pedido) {
        String sql = "INSERT INTO pedido (cliente_id, data, status, produtos) VALUES (?, ?, ?, ?)";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getCliente() != null ? pedido.getCliente().getId() : 0);
            stmt.setString(2, pedido.getData());
            stmt.setString(3, pedido.getStatus());
            stmt.setString(4, produtosToCsv(pedido.getProdutos()));

            stmt.execute();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }

            System.out.println("Pedido cadastrado!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pedido;
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

            System.out.println("Pedido removido!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void alterar(Pedido pedido) {
        String sql = "UPDATE pedido SET cliente_id = ?, data = ?, status = ?, produtos = ? WHERE id = ?";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getCliente() != null ? pedido.getCliente().getId() : 0);
            stmt.setString(2, pedido.getData());
            stmt.setString(3, pedido.getStatus());
            stmt.setString(4, produtosToCsv(pedido.getProdutos()));
            stmt.setInt(5, pedido.getId());

            stmt.execute();

            System.out.println("Pedido atualizado!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Pedido consultar(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        Pedido pedido = null;

        try (Connection con = ConectaDB.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = montarPedido(rs);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pedido;
    }

    @Override
    public List<Pedido> consultar() {
        String sql = "SELECT * FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection con = ConectaDB.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(montarPedido(rs));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pedidos;
    }

    private Pedido montarPedido(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        int clienteId = rs.getInt("cliente_id");
        String data = rs.getString("data");
        String status = rs.getString("status");
        String produtosCsv = rs.getString("produtos");

        Cliente cliente = clienteDao.consultar(clienteId);
        List<Produto> produtos = produtoIdsFromCsv(produtosCsv);

        return new Pedido(id, cliente, data, status, produtos);
    }

    private String produtosToCsv(List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            if (p != null) {
                sb.append(p.getId());
                if (i < produtos.size() - 1) sb.append(",");
            }
        }
        return sb.toString();
    }

    private List<Produto> produtoIdsFromCsv(String csv) {
        List<Produto> lista = new ArrayList<>();
        if (csv == null || csv.trim().isEmpty()) return lista;
        String[] parts = csv.split(",");
        for (String p : parts) {
            try {
                int pid = Integer.parseInt(p.trim());
                Produto produto = produtoDao.consultar(pid);
                if (produto != null) lista.add(produto);
            } catch (NumberFormatException e) {
                // ignora
            }
        }
        return lista;
    }
}


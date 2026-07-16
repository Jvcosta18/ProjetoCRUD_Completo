package modelos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private String data; // formato simples: yyyy-MM-dd ou livre
    private String status;
    private List<Produto> produtos = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Cliente cliente, String data, String status, List<Produto> produtos) {
        this.cliente = cliente;
        this.data = data;
        this.status = status;
        if (produtos != null) this.produtos = produtos;
    }

    public Pedido(int id, Cliente cliente, String data, String status, List<Produto> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.status = status;
        if (produtos != null) this.produtos = produtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" - Cliente: ");
        sb.append(cliente != null ? cliente.getNome() : "(n/d)");
        sb.append(" - Data: ").append(data).append(" - Status: ").append(status).append("\n");
        sb.append("    Produtos: ");
        if (produtos == null || produtos.isEmpty()) {
            sb.append("(nenhum)");
        } else {
            for (int i = 0; i < produtos.size(); i++) {
                Produto p = produtos.get(i);
                sb.append(p != null ? p.getId() + ": " + p.getDescricao() : "(n/d)");
                if (i < produtos.size() - 1) sb.append("; ");
            }
        }
        return sb.toString();
    }
}


import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import dao.ClienteDao;
import dao.ProdutoDao;
import dao.PedidoDao;
import modelos.Cliente;
import modelos.Produto;
import modelos.Pedido;


public class CarrinhoController {

    private final Scanner sc;
    private final ProdutoDao produtoDao;
    private final ClienteDao clienteDao;
    private final PedidoDao pedidoDao;
    private final List<Produto> carrinho = new ArrayList<>();

    public CarrinhoController(Scanner sc, ProdutoDao produtoDao, ClienteDao clienteDao, PedidoDao pedidoDao) {
        this.sc = sc;
        this.produtoDao = produtoDao;
        this.clienteDao = clienteDao;
        this.pedidoDao = pedidoDao;
    }

    public void adicionarAoCarrinhoInteractive() {
        System.out.print("Informe o id do produto a adicionar ao carrinho: ");
        int id = lerInt();

        Produto produto = produtoDao.consultar(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        carrinho.add(produto);
        System.out.println("Produto adicionado ao carrinho: " + produto.getDescricao());
    }

    public void verCarrinho() {
        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        System.out.println("\n--- Carrinho ---");
        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Produto> prodMap = new HashMap<>();
        for (Produto p : carrinho) {
            counts.put(p.getId(), counts.getOrDefault(p.getId(), 0) + 1);
            prodMap.put(p.getId(), p);
        }
        for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
            Produto p = prodMap.get(e.getKey());
            System.out.println(p.getId() + " - " + p.getDescricao() + " - Qtde: " + e.getValue() + " - Preço unit.: " + p.getPreco());
        }
    }

    public void removerDoCarrinhoInteractive() {
        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }

        System.out.print("Informe o id do produto a remover (remove 1 unidade): ");
        int id = lerInt();

        for (int i = 0; i < carrinho.size(); i++) {
            if (carrinho.get(i).getId() == id) {
                Produto removed = carrinho.remove(i);
                System.out.println("Removido do carrinho: " + removed.getDescricao());
                return;
            }
        }

        System.out.println("Produto não encontrado no carrinho.");
    }

    public void finalizarPedidoCarrinhoInteractive() {
        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio. Adicione produtos antes de finalizar.");
            return;
        }


        Map<Integer, Integer> quantidades = new HashMap<>();
        for (Produto p : carrinho) {
            quantidades.put(p.getId(), quantidades.getOrDefault(p.getId(), 0) + 1);
        }


        Map<Integer, Produto> produtosAtualizados = new HashMap<>();
        double total = 0;
        for (Map.Entry<Integer, Integer> e : quantidades.entrySet()) {
            int produtoId = e.getKey();
            int qtdDesejada = e.getValue();

            Produto produtoAtual = produtoDao.consultar(produtoId);
            if (produtoAtual == null) {
                System.out.println("Produto id " + produtoId + " não existe mais. Remova-o do carrinho.");
                return;
            }
            if (produtoAtual.getEstoque() < qtdDesejada) {
                System.out.println("Estoque insuficiente para \"" + produtoAtual.getDescricao() + "\". "
                        + "Disponível: " + produtoAtual.getEstoque() + ", no carrinho: " + qtdDesejada);
                return;
            }

            produtosAtualizados.put(produtoId, produtoAtual);
            total += produtoAtual.getPreco() * qtdDesejada;
        }

        System.out.print("Informe o id do cliente para o pedido: ");
        int clienteId = lerInt();
        Cliente cliente = clienteDao.consultar(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Data do pedido (ex: 2026-07-16): ");
        String data = sc.nextLine();

        System.out.print("Status do pedido: ");
        String status = sc.nextLine();

        List<Produto> itens = new ArrayList<>(carrinho);

        Pedido pedido = new Pedido(cliente, data, status, itens);
        pedidoDao.salvar(pedido);


        for (Map.Entry<Integer, Integer> e : quantidades.entrySet()) {
            Produto produto = produtosAtualizados.get(e.getKey());
            produto.setEstoque(produto.getEstoque() - e.getValue());
            produtoDao.alterar(produto);
        }

        System.out.println("Pedido finalizado com id: " + pedido.getId());
        System.out.println("Valor total do pedido: R$ " + String.format("%.2f", total));
        carrinho.clear();
    }


    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, considerando 0.");
            return 0;
        }
    }

}
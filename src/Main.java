import dao.ClienteDao;
import dao.ProdutoDao;
import dao.PedidoDao;
import modelos.Cliente;
import modelos.Produto;
import modelos.Pedido;

import java.util.List;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static ProdutoDao produtoDao = new ProdutoDao();
	static ClienteDao clienteDao = new ClienteDao();
	static PedidoDao pedidoDao = new PedidoDao();
    static CarrinhoController carrinhoCtrl = new CarrinhoController(sc, produtoDao, clienteDao, pedidoDao);

	public static void main(String[] args) {

		int opcao;

		do {
			System.out.println("\n===== MENU PRINCIPAL =====");
			System.out.println("1 - Produtos");
			System.out.println("2 - Clientes");
			System.out.println("3 - Pedidos");
			System.out.println("0 - Sair");
			System.out.print("Escolha uma opção: ");
			opcao = lerOpcao();

			switch (opcao) {
				case 1 -> menuProdutos();
				case 2 -> menuClientes();
				case 3 -> menuPedidos();
				case 0 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida!");
			}

		} while (opcao != 0);

		sc.close();
	}



	static void menuProdutos() {
		int opcao;

		do {
			System.out.println("\n===== MENU PRODUTOS =====");
			System.out.println("1 - Cadastrar produto");
			System.out.println("2 - Listar produtos");
			System.out.println("3 - Consultar produto por id");
			System.out.println("4 - Atualizar produto");
			System.out.println("5 - Excluir produto");
			System.out.println("0 - Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = lerOpcao();

			switch (opcao) {
				case 1 -> cadastrarProduto();
				case 2 -> listarProdutos();
				case 3 -> consultarProdutoPorId();
				case 4 -> atualizarProduto();
				case 5 -> excluirProduto();
				case 0 -> System.out.println("Voltando...");
				default -> System.out.println("Opção inválida!");
			}

		} while (opcao != 0);
	}

	static void cadastrarProduto() {
		System.out.print("Descrição do produto: ");
		String descricao = sc.nextLine();

		System.out.print("Preço do produto: ");
		double preco = lerDouble();

		System.out.print("Quantidade em estoque: ");
		int estoque = lerInt();

		Produto produto = new Produto(descricao, preco, estoque);
		produtoDao.salvar(produto);

		System.out.println("Produto cadastrado com o id: " + produto.getId());
	}

	static void listarProdutos() {
		List<Produto> produtos = produtoDao.consultar();

		if (produtos.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
			return;
		}

		System.out.println("\n--- Lista de produtos ---");
		for (Produto p : produtos) {
			System.out.println(p);
		}
	}

	static void consultarProdutoPorId() {
		System.out.print("Informe o id do produto: ");
		int id = lerInt();

		Produto produto = produtoDao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
		} else {
			System.out.println(produto);
		}
	}

	static void atualizarProduto() {
		System.out.print("Informe o id do produto que deseja atualizar: ");
		int id = lerInt();

		Produto produto = produtoDao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}

		System.out.println("Produto atual: " + produto);

		System.out.print("Nova descrição: ");
		String descricao = sc.nextLine();

		System.out.print("Novo preço: ");
		double preco = lerDouble();

		System.out.print("Novo estoque: ");
		int estoque = lerInt();

		produto.setDescricao(descricao);
		produto.setPreco(preco);
		produto.setEstoque(estoque);

		produtoDao.alterar(produto);
	}

	static void excluirProduto() {
		System.out.print("Informe o id do produto que deseja excluir: ");
		int id = lerInt();

		Produto produto = produtoDao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}

		produtoDao.deletar(id);
	}



	static void menuClientes() {
		int opcao;

		do {
			System.out.println("\n===== MENU CLIENTES =====");
			System.out.println("1 - Cadastrar cliente");
			System.out.println("2 - Listar clientes");
			System.out.println("3 - Consultar cliente por id");
			System.out.println("4 - Consultar cliente por CPF");
			System.out.println("5 - Buscar clientes por nome");
			System.out.println("6 - Ver total de clientes");
			System.out.println("7 - Atualizar cliente");
			System.out.println("8 - Excluir cliente");
			System.out.println("0 - Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = lerOpcao();

			switch (opcao) {
				case 1 -> cadastrarCliente();
				case 2 -> listarClientes();
				case 3 -> consultarClientePorId();
				case 4 -> consultarClientePorCpf();
				case 5 -> buscarClientesPorNome();
				case 6 -> verTotalClientes();
				case 7 -> atualizarCliente();
				case 8 -> excluirCliente();
				case 0 -> System.out.println("Voltando...");
				default -> System.out.println("Opção inválida!");
			}

		} while (opcao != 0);
	}

	static void cadastrarCliente() {
		System.out.print("CPF: ");
		String cpf = sc.nextLine();

		System.out.print("Nome: ");
		String nome = sc.nextLine();

		System.out.print("Email: ");
		String email = sc.nextLine();

		System.out.print("Rua: ");
		String rua = sc.nextLine();

		System.out.print("Número: ");
		String numero = sc.nextLine();

		System.out.print("Bairro: ");
		String bairro = sc.nextLine();

		System.out.print("CEP: ");
		String cep = sc.nextLine();

		System.out.print("Cidade: ");
		String cidade = sc.nextLine();

		System.out.print("Estado (sigla, ex: SC): ");
		String estado = sc.nextLine();

		Cliente cliente = new Cliente(cpf, nome, email, rua, numero, bairro, cep, cidade, estado);
		clienteDao.salvar(cliente);

		System.out.println("Cliente cadastrado com o id: " + cliente.getId());
	}

	static void listarClientes() {
		List<Cliente> clientes = clienteDao.consultar();

		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}

		System.out.println("\n--- Lista de clientes ---");
		for (Cliente c : clientes) {
			System.out.println(c);
		}
	}

	static void consultarClientePorId() {
		System.out.print("Informe o id do cliente: ");
		int id = lerInt();

		Cliente cliente = clienteDao.consultar(id);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
		} else {
			System.out.println(cliente);
		}
	}

	static void consultarClientePorCpf() {
		System.out.print("Informe o CPF do cliente: ");
		String cpf = sc.nextLine();

		Cliente cliente = clienteDao.consultarPorCpf(cpf);

		if (cliente == null) {
			System.out.println("Cliente com CPF " + cpf + " não encontrado.");
		} else {
			System.out.println(cliente);
		}
	}

	static void buscarClientesPorNome() {
		System.out.print("Informe o nome (ou parte dele) para buscar: ");
		String nome = sc.nextLine();

		List<Cliente> clientes = clienteDao.consultarPorNome(nome);

		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente encontrado com nome contendo '" + nome + "'.");
			return;
		}

		System.out.println("\n--- Clientes encontrados ---");
		for (Cliente c : clientes) {
			System.out.println(c);
		}
	}

	static void verTotalClientes() {
		int total = clienteDao.contar();
		System.out.println("\nTotal de clientes cadastrados: " + total);
	}

	static void atualizarCliente() {
		System.out.print("Informe o id do cliente que deseja atualizar: ");
		int id = lerInt();

		Cliente cliente = clienteDao.consultar(id);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}

		System.out.println("Cliente atual: " + cliente);

		System.out.print("Novo CPF: ");
		String cpf = sc.nextLine();

		System.out.print("Novo nome: ");
		String nome = sc.nextLine();

		System.out.print("Novo email: ");
		String email = sc.nextLine();

		System.out.print("Nova rua: ");
		String rua = sc.nextLine();

		System.out.print("Novo número: ");
		String numero = sc.nextLine();

		System.out.print("Novo bairro: ");
		String bairro = sc.nextLine();

		System.out.print("Novo CEP: ");
		String cep = sc.nextLine();

		System.out.print("Nova cidade: ");
		String cidade = sc.nextLine();

		System.out.print("Novo estado (sigla): ");
		String estado = sc.nextLine();

		cliente.setCpf(cpf);
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setRua(rua);
		cliente.setNumero(numero);
		cliente.setBairro(bairro);
		cliente.setCep(cep);
		cliente.setCidade(cidade);
		cliente.setEstado(estado);

		clienteDao.alterar(cliente);
	}

	static void excluirCliente() {
		System.out.print("Informe o id do cliente que deseja excluir: ");
		int id = lerInt();

		Cliente cliente = clienteDao.consultar(id);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}

		clienteDao.deletar(id);
	}


	static void menuPedidos() {
		int opcao;

		do {
			System.out.println("\n===== MENU PEDIDOS =====");
			System.out.println("1 - Adicionar produto ao carrinho");
			System.out.println("2 - Ver carrinho");
			System.out.println("3 - Remover item do carrinho");
			System.out.println("4 - Finalizar pedido (criar a partir do carrinho)");
			System.out.println("5 - Cadastrar pedido (manual)");
			System.out.println("6 - Listar pedidos");
			System.out.println("7 - Consultar pedido por id");
			System.out.println("8 - Atualizar pedido");
			System.out.println("9 - Excluir pedido");
			System.out.println("0 - Voltar");
			System.out.print("Escolha uma opção: ");
			opcao = lerOpcao();

			switch (opcao) {
				case 1 -> carrinhoCtrl.adicionarAoCarrinhoInteractive();
				case 2 -> carrinhoCtrl.verCarrinho();
				case 3 -> carrinhoCtrl.removerDoCarrinhoInteractive();
				case 4 -> carrinhoCtrl.finalizarPedidoCarrinhoInteractive();
				case 5 -> cadastrarPedido();
				case 6 -> listarPedidos();
				case 7 -> consultarPedidoPorId();
				case 8 -> atualizarPedido();
				case 9 -> excluirPedido();
				case 0 -> System.out.println("Voltando...");
				default -> System.out.println("Opção inválida!");
			}

		} while (opcao != 0);
	}

	static void cadastrarPedido() {
		System.out.print("Informe o id do cliente: ");
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

		System.out.print("Ids dos produtos (separados por vírgula): ");
		String entrada = sc.nextLine();
		List<Produto> produtos = new java.util.ArrayList<>();
		if (!entrada.trim().isEmpty()) {
			String[] parts = entrada.split(",");
			for (String p : parts) {
				try {
					int pid = Integer.parseInt(p.trim());
					Produto prod = produtoDao.consultar(pid);
					if (prod != null) produtos.add(prod);
				} catch (NumberFormatException e) {
					// ignora
				}
			}
		}

		Pedido pedido = new Pedido(cliente, data, status, produtos);
		pedidoDao.salvar(pedido);

		System.out.println("Pedido cadastrado com o id: " + pedido.getId());
	}

	static void listarPedidos() {
		List<Pedido> pedidos = pedidoDao.consultar();

		if (pedidos.isEmpty()) {
			System.out.println("Nenhum pedido cadastrado.");
			return;
		}

		System.out.println("\n--- Lista de pedidos ---");
		for (Pedido p : pedidos) {
			System.out.println(p);
		}
	}

	static void consultarPedidoPorId() {
		System.out.print("Informe o id do pedido: ");
		int id = lerInt();

		Pedido pedido = pedidoDao.consultar(id);

		if (pedido == null) {
			System.out.println("Pedido não encontrado.");
		} else {
			System.out.println(pedido);
		}
	}

	static void atualizarPedido() {
		System.out.print("Informe o id do pedido que deseja atualizar: ");
		int id = lerInt();

		Pedido pedido = pedidoDao.consultar(id);
		if (pedido == null) {
			System.out.println("Pedido não encontrado.");
			return;
		}

		System.out.println("Pedido atual: " + pedido);

		System.out.print("Novo id do cliente: ");
		int clienteId = lerInt();
		Cliente cliente = clienteDao.consultar(clienteId);
		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}

		System.out.print("Nova data (ex: 2026-07-16): ");
		String data = sc.nextLine();

		System.out.print("Novo status: ");
		String status = sc.nextLine();

		System.out.print("Ids dos produtos (separados por vírgula): ");
		String entrada = sc.nextLine();
		List<Produto> produtos = new java.util.ArrayList<>();
		if (!entrada.trim().isEmpty()) {
			String[] parts = entrada.split(",");
			for (String p : parts) {
				try {
					int pid = Integer.parseInt(p.trim());
					Produto prod = produtoDao.consultar(pid);
					if (prod != null) produtos.add(prod);
				} catch (NumberFormatException e) {
					// ignora
				}
			}
		}

		pedido.setCliente(cliente);
		pedido.setData(data);
		pedido.setStatus(status);
		pedido.setProdutos(produtos);

		pedidoDao.alterar(pedido);
	}

	static void excluirPedido() {
		System.out.print("Informe o id do pedido que deseja excluir: ");
		int id = lerInt();

		Pedido pedido = pedidoDao.consultar(id);
		if (pedido == null) {
			System.out.println("Pedido não encontrado.");
			return;
		}

		pedidoDao.deletar(id);
	}


	static void adicionarAoCarrinho() {
		carrinhoCtrl.adicionarAoCarrinhoInteractive();
	}

	static void verCarrinho() {
		carrinhoCtrl.verCarrinho();
	}

	static void removerDoCarrinho() {
		carrinhoCtrl.removerDoCarrinhoInteractive();
	}




	static int lerOpcao() {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	static int lerInt() {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Valor inválido, considerando 0.");
			return 0;
		}
	}

	static double lerDouble() {
		try {
			return Double.parseDouble(sc.nextLine().replace(",", "."));
		} catch (NumberFormatException e) {
			System.out.println("Valor inválido, considerando 0.0.");
			return 0.0;
		}
	}

}

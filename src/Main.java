import dao.ProdutoDao;
import modelos.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static ProdutoDao dao = new ProdutoDao();

	public static void main(String[] args) {

		int opcao;

		do {
			exibirMenu();
			opcao = lerOpcao();

			switch (opcao) {
				case 1 -> cadastrar();
				case 2 -> listar();
				case 3 -> consultarPorId();
				case 4 -> atualizar();
				case 5 -> excluir();
				case 0 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida!");
			}

		} while (opcao != 0);

		sc.close();
	}

	static void exibirMenu() {
		System.out.println("\n===== MENU PRODUTOS =====");
		System.out.println("1 - Cadastrar produto");
		System.out.println("2 - Listar produtos");
		System.out.println("3 - Consultar produto por id");
		System.out.println("4 - Atualizar produto");
		System.out.println("5 - Excluir produto");
		System.out.println("0 - Sair");
		System.out.print("Escolha uma opção: ");
	}

	static int lerOpcao() {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	static void cadastrar() {
		System.out.print("Descrição do produto: ");
		String descricao = sc.nextLine();

		System.out.print("Preço do produto: ");
		double preco = lerDouble();

		Produto produto = new Produto(descricao, preco);
		dao.salvar(produto);

		System.out.println("Produto cadastrado com o id: " + produto.getId());
	}

	static void listar() {
		List<Produto> produtos = dao.consultar();

		if (produtos.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
			return;
		}

		System.out.println("\n--- Lista de produtos ---");
		for (Produto p : produtos) {
			System.out.println(p);
		}
	}

	static void consultarPorId() {
		System.out.print("Informe o id do produto: ");
		int id = lerInt();

		Produto produto = dao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
		} else {
			System.out.println(produto);
		}
	}

	static void atualizar() {
		System.out.print("Informe o id do produto que deseja atualizar: ");
		int id = lerInt();

		Produto produto = dao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}

		System.out.println("Produto atual: " + produto);

		System.out.print("Nova descrição: ");
		String descricao = sc.nextLine();

		System.out.print("Novo preço: ");
		double preco = lerDouble();

		produto.setDescricao(descricao);
		produto.setPreco(preco);

		dao.alterar(produto);
	}

	static void excluir() {
		System.out.print("Informe o id do produto que deseja excluir: ");
		int id = lerInt();

		Produto produto = dao.consultar(id);

		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}

		dao.deletar(id);
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

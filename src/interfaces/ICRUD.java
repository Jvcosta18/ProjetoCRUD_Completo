package interfaces;

import java.util.List;

import modelos.Produto;

public interface ICRUD {
	Produto salvar(Produto prod);
	void deletar(int id);
	void alterar(Produto prod);
	Produto consultar(int id);
	List<Produto> consultar();
}

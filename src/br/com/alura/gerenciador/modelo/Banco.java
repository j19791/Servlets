package br.com.alura.gerenciador.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Banco {

	private static List<Empresa> lista = new ArrayList<>();
	private static List<Usuario> listaUsuarios = new ArrayList<>();
	private static Integer chaveSequencial = 1; 
	
	//qdo a maquina virtual carrega a classe, o bloco static ja é executado
	static {
		
		Empresa e1 = new Empresa();
		e1.setId(chaveSequencial++);
		e1.setNome("Alura");
		Empresa e2 = new Empresa();
		e2.setId(chaveSequencial++);
		e2.setNome("Caelum");
		
		lista.add(e1);
		lista.add(e2);
		
		Usuario u1 = new Usuario();
		u1.setLogin("nico");
		u1.setSenha("12345");
		Usuario u2 = new Usuario();
		u2.setLogin("ana");
		u2.setSenha("12345");
		
		listaUsuarios.add(u1);
		listaUsuarios.add(u2);
		
		
	}
	
	
	public void adiciona(Empresa empresa) {
		empresa.setId(chaveSequencial++);
		lista.add(empresa);
	}
	
	public List<Empresa> getEmpresas(){
		return Banco.lista; //nao pode colocar this pois é um atributo da classe
	}

	public void removeEmpresa(Integer id) {
		Iterator<Empresa> it = lista.iterator(); 
		
		while(it.hasNext()) {//se tem uma proxima empresa na lista de empresas
			Empresa emp = it.next(); //me devolve essa empresa
			if(emp.getId() == id) { //se essa empresa é a q eu quero remover
				it.remove(); //remove da lista
			}
		}
		
	
	}

	public Empresa buscaEmpresa(Integer id) {
		for (Empresa empresa : lista) {
			if(empresa.getId() == id) {
				return empresa;
			}
		}
		return null;
	}

	public Usuario existeUsuario(String login, String senha) {
	
		for(Usuario usuario : listaUsuarios) {
			if(usuario.ehIgual(login, senha)) {
				return usuario;
			}
		}
		
		
		return null;
		
	}

	

}

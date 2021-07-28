package br.com.alura.gerenciador.acao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;

public class ListaEmpresas {

	//codigo de listar encapsulado
	public void executa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		List<Empresa> lista = new Banco().getEmpresas();
		PrintWriter out = response.getWriter();
		
		
		
		request.setAttribute("empresas", lista); //pendurou o objeto na requisicao
		RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas.jsp");
		rd.forward(request, response); //vai
		
		
	}
	
}

package br.com.alura.gerenciador.acao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;

public class NovaEmpresa {

	public void executa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	String paramId = request.getParameter("id");//sempre volta string
			
	String nomeEmpresa = request.getParameter("nome"); //lendo os parametros passados visivelmente  atraves do navegador (requisição) http://localhost:8080/gerenciador/novaEmpresa?nome=Alura
	String dataAberturaString = request.getParameter("data"); //sempre recebe string mas dataAbertura é Date
	
	Date dataAbertura;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	try { //nao pode incluir throws pois doPost esta implementado
		 dataAbertura = sdf.parse(dataAberturaString); //parse é checked
	} catch (ParseException e) {
		throw new ServletException(e);
	}
	
	Empresa empresa = new Empresa();
	empresa.setNome(nomeEmpresa);
	empresa.setDataAbertura(dataAbertura);
	
	Banco banco = new Banco();
	banco.adiciona(empresa);
	
	
	
	request.setAttribute("empresa", empresa.getNome()); //joga o atributo dentro da requisição (apelido p/ ser usado no jsp, valor)
	
	
			
	response.sendRedirect("entrada?acao=ListaEmpresas");
	}
	
}

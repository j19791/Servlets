package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AlteraEmpresaServlet
 */
@WebServlet("/alteraEmpresa")
public class AlteraEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nomeEmpresa = request.getParameter("nome"); //lendo os parametros passados visivelmente  atraves do navegador (requisição) http://localhost:8080/gerenciador/novaEmpresa?nome=Alura
		String dataAberturaString = request.getParameter("data"); //sempre recebe string mas dataAbertura é Date
		
		
		Integer id = Integer.valueOf(request.getParameter("id"));
		
		
		Date dataAbertura;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try { //nao pode incluir throws pois doPost esta implementado
			 dataAbertura = sdf.parse(dataAberturaString); //parse é checked
		} catch (ParseException e) {
			throw new ServletException(e);
		}
		
		Banco banco = new Banco();
		
		Empresa empresa = banco.buscaEmpresa(id);
		empresa.setNome(nomeEmpresa);
		empresa.setDataAbertura(dataAbertura);
		
		
		
		
		//banco.altera(empresa);
		response.sendRedirect("listaEmpresas");
		
	}

}

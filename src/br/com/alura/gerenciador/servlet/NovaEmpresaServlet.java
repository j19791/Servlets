package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NovaEmpresaServlet
 */
@WebServlet("/novaEmpresa")
public class NovaEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/*
	 * protected void service(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * System.out.println("Caadastrando nova empresa"); String nomeEmpresa =
	 * request.getParameter("nome"); //lendo os parametros passados visivelmente
	 * atraves do navegador (requisição)
	 * http://localhost:8080/gerenciador/novaEmpresa?nome=Alura PrintWriter out =
	 * response.getWriter(); out.println("<html><body>Empresa " + nomeEmpresa +
	 * " cadastrada c/ sucesso</body></html>"); }
	 */
	
	//nao pode mais passar parametros na url com get, senão : HTTP Status 405 – Method Not Allowed
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Caadastrando nova empresa");
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
		
		//utilizando jsp agora
		/*
		PrintWriter out = response.getWriter();
		out.println("<html><body>Empresa " + nomeEmpresa + " cadastrada c/ sucesso</body></html>");
		*/
		
		request.setAttribute("empresa", empresa.getNome()); //joga o atributo dentro da requisição (apelido p/ ser usado no jsp, valor)
		
		//despachar a requisicção p/ frente (jsp). Nao fica mais na classe
		//RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
		
		//RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas"); //agora vai chamar o servlet de listagem (redirecionamento server side 
		
		//rd.forward(request, response); //vai p/ o jsp
		
		//3- p/ evitar multiplos cadastrados c/ F5 (reenvio de dados do formulario na listagem) redirecionamento client-side : servlet NovaEmpresa -> Navegador -> servlet Lista Empresas
		
		response.sendRedirect("listaEmpresas"); //response c/ redirect navegador recebe resposta status 302
	}

}

package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MostraEmpresaServlet
 */
@WebServlet("/mostraEmpresa")
public class MostraEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
String paramId = request.getParameter("id");//sempre volta string
		
		Integer id = Integer.valueOf(paramId);
		
		Banco banco = new Banco();
		Empresa empresa = banco.buscaEmpresa(id);
		
		request.setAttribute("empresa", empresa); //joga o atributo dentro da requisição (apelido p/ ser usado no jsp, valor)
		
		//despachar a requisicção p/ frente (jsp). Nao fica mais na classe
		RequestDispatcher rd = request.getRequestDispatcher("/formAlteraEmpresa.jsp");
		
		rd.forward(request, response);
		
		
	}

}

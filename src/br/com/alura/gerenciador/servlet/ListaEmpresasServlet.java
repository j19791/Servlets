package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;


//@WebServlet("/listaEmpresas") - agora esta usando controlador
public class ListaEmpresasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    //nao pode ser mais doGet pois a lista esta sendo usada agora pelo servlet de novas empresas q utiliza post
	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Empresa> lista = new Banco().getEmpresas();
		PrintWriter out = response.getWriter();
		
		//utilizando jsp
		/*
		out.println("<html><body>");
		out.println("<ul>");
		for (Empresa empresa : lista) {
			out.println("<li>" + empresa.getNome() + "</li>");
		}
		
		
		out.println("</ul>");
		out.println("</body></html>");
		*/
		
		request.setAttribute("empresas", lista); //pendurou o objeto na requisicao
		RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas.jsp");
		rd.forward(request, response); //vai
		
	}

}

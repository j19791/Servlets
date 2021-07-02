package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/oi") //mapeado p/ http://localhost:8080/gerenciador/oi
public class OiMundoServlet extends HttpServlet{//servidorzinho

	//metodo p/ implementar do HttpServlet
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		//imprime p/ o fluxo de resposta p/ o navegador
		out.println("<html>");
		out.println("<body>");
		out.println("Teste de servlet");
		out.println("</body>");
		out.println("</html>");
		
		System.out.println("o servlet OiMundoServlet foi chamado");
	}
	
	//servlet container - tomcat -middleware - cria o main
	//preguiçoso - cria qdo é chamado o servlet nao qdo inicial o servidor
	//tomcat reaproveita o mesmo servlet - instancia única : singleton - vive p/ sempre enquanto o tomcat estiver no ar
	//inversao de controle IoC - 
	
}

package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//utilizando web.xml p/ definir a ordem de execução dos filtros
//@WebFilter("/entrada")
public class AutorizacaoFilter implements Filter {

   
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("AutorizacaoFilter");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest; //cast: interface + generica (ServletRequest) p/ uma mais especifica (HttpServletRequest)
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String paramAcao = request.getParameter("acao");
		
		HttpSession sessao = request.getSession();
		boolean usuarioNaoEstaLoOgado = (sessao.getAttribute("usuarioLogado") == null);
		boolean ehAcaoProtegida = !(paramAcao.equals("Login") || paramAcao.equals("LoginForm"));
		
		if(ehAcaoProtegida //Login e LoginForm nao precisa ser protegido
				&& usuarioNaoEstaLoOgado) {
			response.sendRedirect("redirect:entrada?acao=LoginForm");
			return; //sair do método p/ não continuar o processamento da requisição no resto do método
		}
		
		
		chain.doFilter(request, response);
	}

	
}

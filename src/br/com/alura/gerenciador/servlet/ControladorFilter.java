package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.acao.Acao;

/**
 * Servlet Filter implementation class ControladorFilter
 */
//@WebFilter("/ControladorFilter") utilizadno web.xml
public class ControladorFilter implements Filter {

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		System.out.println("ControladorFilter");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest; //cast: interface + generica (ServletRequest) p/ uma mais especifica (HttpServletRequest)
		HttpServletResponse response = (HttpServletResponse) servletResponse;	
		
		String paramAcao = request.getParameter("acao");	
		
		String nomeClasse = "br.com.alura.gerenciador.acao." + paramAcao;
		
		//padrao (contrato, interface): 1) instanciar acao 2) chamar acao
		//simplificando o controlador, tirando os ifs e usando + oo c/ Reflection
		//qq ação nova não é necessário atualização do controlador, apenas utilizar convenções
		String nome;
		try {			
			Class classe = Class.forName(nomeClasse);//JRE carrega/devolve a classe e deixa em memoria e p/ eu ter a instancia
			Acao acao = (Acao) classe.newInstance();//pedir p/ a classe criar uma nova instancia (construtor)
			nome = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}
		
		
		//o controlador tem agora a terfa de manda p/ a camada view correspondente
		String []tipoEEndereco = nome.split(":"); //quebra o retorno 	
		
		if(tipoEEndereco[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEEndereco[1]); //os jsp agora estão escondidos na WEB-INF
			rd.forward(request, response);
		}
		else //redirect		
			response.sendRedirect(tipoEEndereco[1]); //navegador envia nova requisição
		
		
		//chain.doFilter(request, response); nao chama mais nenhum outro filtro
	}

	

}

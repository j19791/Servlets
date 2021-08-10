package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//utilizando web.xml p/ definir a ordem de execu��o dos filtros
//@WebFilter("/entrada") //todas as requisi��es v�o chegar antes no filtro e delegar p/ o controlador
public class MonitoramentoFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("MonitoramentoFilter");
		
		Long antes = System.currentTimeMillis(); //ms desde 1970(ano 0 na computa��o): + depois, - antes de 1970
		
		
		
		//filtro: continua executando a acao (servlet) (cadeia)
		chain.doFilter(request, response);
		
		Long depois = System.currentTimeMillis();
		
		System.out.println("tempo de execu��o da acao " + request.getParameter("acao") + ": " + (depois- antes) + "ms");
	}

}

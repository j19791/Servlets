package br.com.alura.gerenciador.acao;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;

public class MostraEmpresa implements Acao {
	public String executa(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
String paramId = request.getParameter("id");//sempre volta string
		
		Integer id = Integer.valueOf(paramId);
		
		Banco banco = new Banco();
		Empresa empresa = banco.buscaEmpresa(id);
		
		request.setAttribute("empresa", empresa); //joga o atributo dentro da requisição (apelido p/ ser usado no jsp, valor)
		
		//utilizando agora o direcionamento no contrador
		return "forward:formAlteraEmpresa.jsp";
	}

}

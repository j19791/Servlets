package br.com.alura.gerenciador.acao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Acao {

	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession sessao = request.getSession();
		//sessao.removeAttribute("usuarioLogado"); //objeto httpSession continua na memoria
		sessao.invalidate(); //melhor: destroi o objeto antigo httpsession e o cookie associado
		
		
		return "redirect:entrada?acao=LoginForm";
	}

}

package br.com.alura.gerenciador.acao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Usuario;

public class Login implements Acao {

	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		System.out.println("logando" + login);
		
		Banco banco = new Banco();
		Usuario usuario = banco.existeUsuario(login,senha);
		
		if(usuario != null) { 
			HttpSession sessao = request.getSession(); // navegadores diferentes (ie x chrome) são sessions diferentes
			sessao.setAttribute("usuarioLogado", usuario); //p/ ser usado nas views
			return "redirect:entrada?acao=ListaEmpresas";
		}
			
		else return "redirect:entrada?acao=LoginForm";
		
		
	}

}

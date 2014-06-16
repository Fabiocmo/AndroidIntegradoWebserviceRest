package br.com.dao;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.business.Cliente;

public class ClienteDao {
	
	private static String Classe= "ClienteDao";
	
	public String JsonCliente(Cliente cli) throws JSONException{
		
		JSONObject json=new JSONObject();
		json.put("Id",cli.getId());
		json.put("Nome", cli.getNome());
		json.put("Endereco", cli.getEndereco());
		json.put("Telefone", cli.getTelefone());
		json.put("Foto", cli.getFoto());
		return json.toString();
	}

public String Inserir(Cliente cli) throws JSONException{
	JSONObject json=new JSONObject();
	json.put("Classe",Classe);
	json.put("Metodo", "Inserir");
	json.put("Json", JsonCliente(cli));
	
	return json.toString();
}

public String Pesquisar(Cliente cli) throws JSONException{
	JSONObject json=new JSONObject();
	json.put("Classe",Classe);
	json.put("Metodo", "Pesquisar");

	
	return json.toString();
}	
}

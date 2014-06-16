package br.com.item;

public class ItemListCliente {
	
	private int id;
	private String nome;
	private String endereco;
	private String telefone;
	private byte[] foto;
	
	public ItemListCliente(int id,String nome,String endereco,String telefone,  byte[] foto){
	this.setId(id);;
	this.setNome(nome);
	this.setEndereco(endereco);
	this.setTelefone(telefone);
		this.setFoto(foto);
	}
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}

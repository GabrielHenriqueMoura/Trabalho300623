package entidades;

public class Usuario {
	private int id;
	private String NomeTime;
	private String senha;
	
	public Usuario(String NomeTime, String senha) {
		this.NomeTime = NomeTime;
		this.senha = senha;
	}
	
	public Usuario(int id, String NomeTime, String senha) {
		this.id = id;
		this.NomeTime = NomeTime;
		this.senha = senha;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeTime() {
		return NomeTime;
	}
	
	public void setNomeTime(String NomeTime) {
		this.NomeTime = NomeTime;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}

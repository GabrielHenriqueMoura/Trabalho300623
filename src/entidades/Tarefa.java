package entidades;

public class Tarefa {
	
	private int id;
	private String NomeEsporte;
	private int QuantiaJogador;
	private Usuario usuario;
	
	public Tarefa(String NomeEsporte, int QuantiaJogador, Usuario usuario) {
		this.NomeEsporte = NomeEsporte;
		this.QuantiaJogador = QuantiaJogador;
		this.usuario = usuario;
	}
	
	public Tarefa(int id, String getNomeEsporte, int QuantiaJogador, Usuario usuario) {
		this.id = id;
		this.NomeEsporte = NomeEsporte;
		this.QuantiaJogador = QuantiaJogador;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeEsporte() {
		return NomeEsporte;
	}
	
	public void setNomeEsporte(String NomeEsporte) {
		this.NomeEsporte = NomeEsporte;
	}
	
	public int getQuantiaJogador() {
		return QuantiaJogador;
	}
	
	public void setQuantiaJogador(int QuantiaJogador) {
		this.QuantiaJogador = QuantiaJogador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

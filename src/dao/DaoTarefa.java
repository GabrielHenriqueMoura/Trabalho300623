package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Tarefa;
import entidades.Usuario;

public class DaoTarefa {
	
	public boolean inserir(Tarefa tarefa) throws SQLException {
				
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into InfoTimes (NomeEsporte, QuantiaJogador,IDTime) values(? , ? , ?);";
		PreparedStatement ps = conexao.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

		ps.setString(1, tarefa.getNomeEsporte());
		ps.setInt(2, tarefa.getQuantiaJogador());
		ps.setInt(3, tarefa.getUsuario().getId() );

		int linhasAfetadas = ps.executeUpdate();
		
		ResultSet r = ps.getGeneratedKeys();
		
		if( r.next() ) {
			int id = r.getInt(1);	
			tarefa.setId(id);
		}
		
		return (linhasAfetadas == 1 ? true : false);
	}

	public boolean atualizar(Tarefa tarefa) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "update InfoTimes set NomeEsporte = ?, QuantiaJogador = ? where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tarefa.getNomeEsporte());
		ps.setInt(2, tarefa.getQuantiaJogador());
		ps.setInt(3, tarefa.getId());
		
		if( ps.executeUpdate() == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean excluir(int id) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "DELETE FROM InfoTimes WHERE id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		
		int linhasAfetadas = ps.executeUpdate();
		
		ps.close();
		con.close();
		
		return linhasAfetadas > 0;
	}


	public Tarefa buscar(int idBuscado) throws SQLException {
		
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from InfoTimes where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		Tarefa tarefa = null;
		
		if( result.next() ) {
			int id = result.getInt("id");
			String NomeEsporte = result.getString("NomeEsporte");
			int QuantiaJogador = result.getInt("QuantiaJogador");
			int idUsuario = result.getInt("IDTime");
			
			DaoUsuario daoUser = new DaoUsuario();
			Usuario u = daoUser.buscarPorId(idUsuario);
			
			tarefa = new Tarefa(id, NomeEsporte, QuantiaJogador, u);
		}
		
		return tarefa;
	}

	public List<Tarefa> buscarTodas() throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from InfoTimes";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa> InfoTimes = new ArrayList<Tarefa>();
		
		DaoUsuario daoUser = new DaoUsuario();

		while( result.next() ) {
			int id = result.getInt("id");
			String NomeEsporte = result.getString("NomeEsporte");
			int QuantiaJogador = result.getInt("QuantiaJogador");
			int idUsuario = result.getInt("IDTime");
			
			Usuario u = daoUser.buscarPorId(idUsuario);
			
			Tarefa t = new Tarefa(id, NomeEsporte, QuantiaJogador, u);
	
			InfoTimes.add(t);
		}
		
		return InfoTimes;
	}

	public List<Tarefa> pesquisarPorNomeEsporte(String texto) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from InfoTimes where NomeEsporte like ? ";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+texto+"%");
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa> InfoTimes = new ArrayList<Tarefa>();
		
		DaoUsuario daoUser = new DaoUsuario();
		
		while( result.next() ) {
			int id = result.getInt("id");
			String NomeEsporte = result.getString("NomeEsporte");
			int QuantiaJogador = result.getInt("QuantiaJogador");
			int idUsuario = result.getInt("IDTime");
			
			Usuario u = daoUser.buscarPorId(idUsuario);
			Tarefa t = new Tarefa(id, NomeEsporte, QuantiaJogador, u);
	
			InfoTimes.add(t);
		}
		
		return InfoTimes;
	}
	
	public List<Tarefa> InfoTimesPorUsuario(String NomeTime) throws SQLException {
		Connection con = ConnectionFactory.getConexao();
		
		String sql = "select * from InfoTimes left join times on InfoTimes.IDTime = times.id where times.NomeTime = ?;";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, NomeTime);
		
		ResultSet result = ps.executeQuery();
		
		List<Tarefa>InfoTimes = new ArrayList<Tarefa>();
		
		
		if( result.next() ) {			
			int idUser = result.getInt("IDTime");
			String senha = result.getString("senha");
			
			Usuario usuario = new Usuario(idUser, NomeTime, senha);
			
			do {
				int id = result.getInt("id");
				String NomeEsporte = result.getString("NomeEsporte");
				int QuantiaJogador = result.getInt("QuantiaJogador");
				
				Tarefa t = new Tarefa(id, NomeEsporte, QuantiaJogador, usuario);
		
				InfoTimes.add(t);
			}while(result.next());
		}
		
		return InfoTimes;
	}
}

package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import dao.DaoTarefa;
import dao.DaoUsuario;
import entidades.Tarefa;
import entidades.Usuario;

public class Programa {

	private static DaoTarefa daoTarefa = new DaoTarefa();
	private static DaoUsuario daoUsuario = new DaoUsuario();

	public static void main(String[] args) throws SQLException {

		Scanner scanner = new Scanner(System.in);
		int opcao;

		do {
			System.out.println("Digite:");
			System.out.println("1 - Cadastrar esporte");
			System.out.println("2 - Cadastrar time da casa");
			System.out.println("3 - Buscar esporte");
			System.out.println("4 - Excluir time");
			System.out.println("5 - Listar esportes cadastrados");
			System.out.println("6 - Buscar oponente");
			System.out.println("7 - Atualizar esporte");
			System.out.println("8 - Listar times por id");
			System.out.println("9 - Listar esportes por time");
			System.out.println("10 - Marcar data do jogo");
			
			System.out.println("0 - Sair");

			opcao = Integer.parseInt(scanner.nextLine());

			switch (opcao) {
				case 1:
					cadastrarEsporte();
					break;
				case 2:
					cadastrarUsuario();
					break;
				case 3:
					buscarTarefa();
					break;
				case 4:
					excluirTarefa();
					break;
				case 5:
					listarInfoTimes();
					break;
				case 6:
					encontrarAdversario();
					break;
				case 7:
					atualizarTarefa();
					break;
				case 8:
					listartimes();
					break;
				case 9:
					listarInfoTimesPorUsuario();
					break;
				case 10:
					dataDoJogo(args);
					break;
				case 0:
					System.out.println("Até mais.");
					break;
				default:
					System.out.println("Opção inválida!");
			}

		} while (opcao != 0);
	}

	public static void cadastrarEsporte() throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do esporte: ");
		String NomeEsporte = scanner.nextLine();

		System.out.println("Informe quantos jogadores vão jogar: ");
		int QuantiaJogador = Integer.parseInt(scanner.nextLine());

		System.out.println("Informe o ID do time: ");
		int idUser = Integer.parseInt(scanner.nextLine());

		Usuario u = daoUsuario.buscarPorId(idUser);

		if (u != null) {
			Tarefa t = new Tarefa(NomeEsporte, QuantiaJogador, u);

			if (daoTarefa.inserir(t)) {
				System.out.println("Esporte cadastrado sob o ID " + t.getId());
			} else {
				System.out.println("Falha no cadastro...");
			}
		} else {
			System.out.println("Não foi encontrado um time com este ID");
		}
	}

	public static void atualizarTarefa() throws SQLException {
		System.out.println("-- Alterando o esporte --");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Informe o ID do time: ");
		int id = Integer.parseInt(scanner.nextLine());

		Tarefa tarefa = daoTarefa.buscar(id);

		if (tarefa != null) {

			System.out.println("Esporte: " + tarefa.getNomeEsporte());
			System.out.println("Informe o novo esporte:");

			String desc = scanner.nextLine();

			if (!desc.isEmpty()) {
				tarefa.setNomeEsporte(desc);
			}

			System.out.println("Número atual de jogadores no esporte cadastrados anteriormente: " + tarefa.getQuantiaJogador());
			System.out.println("Informe o número de jogadores deste novo esporte ou pressione enter:");

			String priori = scanner.nextLine();

			if (!priori.isEmpty()) {
				tarefa.setQuantiaJogador(Integer.parseInt(priori));
			}

			if (daoTarefa.atualizar(tarefa)) {
				System.out.println("Esporte alterado!");
			} else {
				System.out.println("Houve um erro ao atualizar.");
			}

		} else {
			System.out.println("Erro ao localizar o time " + id + ", ele existe?");
		}
	}

	public static void buscarTarefa() throws SQLException {
		System.out.println("\n-- Buscando esporte por ID --");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Informe o ID do esporte: ");
		int id = Integer.parseInt(scanner.nextLine());

		Tarefa t = daoTarefa.buscar(id);

		if (t != null) {
			System.out.println("ID: " + t.getId());
			System.out.println("Nome do Esporte: " + t.getNomeEsporte());
			System.out.println("Número de jogadores: " + t.getQuantiaJogador());
			System.out.println("Nome do time: " + t.getUsuario().getNomeTime() + "\n");
		} else {
			System.out.println("Esporte não existe...");
		}
	}

	public static void excluirTarefa() throws SQLException {
		System.out.println("\n-- Excluindo esporte por ID --");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Informe o ID do esporte: ");
		int id = Integer.parseInt(scanner.nextLine());

		boolean sucesso = daoTarefa.excluir(id);

		if (sucesso) {
			System.out.println("Esporte excluído com sucesso!");
		} else {
			System.out.println("Houve um problema ao excluir o esporte " + id + ", verifique se ele existe.");
		}
	}
	public static void listarInfoTimes() throws SQLException {
		System.out.println("\n-- Listar Esportes Cadastrados --\n");

		List<Tarefa> tasks = daoTarefa.buscarTodas();

		Scanner scanner = new Scanner(System.in);

		for (Tarefa t : tasks) {
			System.out.println("ID do time: " + t.getId());
			System.out.println("Esporte: " + t.getNomeEsporte());
			System.out.println("Quantidade de jogadores no time: " + t.getQuantiaJogador());
			System.out.println("Time: " + t.getUsuario().getNomeTime() + "\n");

			scanner.nextLine();
		}
	}

	public static void pesquisarInfoTimes() throws SQLException {
		System.out.println("\n-- Buscando Esportes por ID do time --");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Informe o ID do time: ");
		String pesquisa = scanner.nextLine();

		List<Tarefa> tasks = daoTarefa.pesquisarPorNomeEsporte(pesquisa);

		for (Tarefa t : tasks) {
			System.out.println("ID do time: " + t.getId());
			System.out.println("Esporte: " + t.getNomeEsporte());
			System.out.println("Quantidade de jogadores no time: " + t.getQuantiaJogador());
			System.out.println("Time: " + t.getUsuario().getNomeTime() + "\n");

			scanner.nextLine();
		}
	}

	public static void cadastrarUsuario() throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do time: ");
		String NomeTime = scanner.nextLine();

		System.out.println("Senha do time: ");
		String senha = scanner.nextLine();

		Usuario usuario = new Usuario(NomeTime, senha);

		System.out.println(daoUsuario.inserir(usuario) ? "Cadastrou!" : "Falha no cadastro...");

		System.out.println("Usuário cadastrado sob o time de ID:  " + usuario.getId());
	}

	public static void listartimes() throws SQLException {
		System.out.println("\n-- Listar Usuários --\n");

		List<Usuario> users = daoUsuario.buscarTodos();

		for (Usuario u : users) {
			System.out.println("ID do time: " + u.getId());
			System.out.println("Time: " + u.getNomeTime());
			System.out.println("Senha: " + u.getSenha());
		}
	}

	public static void listarInfoTimesPorUsuario() throws SQLException {
		System.out.println("\n-- Informações sobre um time --\n");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do time: ");
		String NomeTime = scanner.nextLine();

		List<Tarefa> tasks = daoTarefa.InfoTimesPorUsuario(NomeTime);

		for (Tarefa t : tasks) {
			System.out.println("ID: " + t.getId());
			System.out.println("Esporte: " + t.getNomeEsporte());
			System.out.println("Quantidade de jogadores no time: " + t.getQuantiaJogador());
			System.out.println("Time: " + t.getUsuario().getNomeTime() + "\n");
		}
	}

	public static void dataDoJogo(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Insira a data atual (formato: ano-mês-dia):");
		String dataAtualString = scanner.next();
		LocalDate dataAtual = LocalDate.parse(dataAtualString);

		System.out.println("Insira a data do jogo (formato: ano-mês-dia):");
		String dataJogoString = scanner.next();
		LocalDate dataJogo = LocalDate.parse(dataJogoString);

		long intervaloDias = ChronoUnit.DAYS.between(dataAtual, dataJogo);

		System.out.println("O jogo vai acontecer em: " + intervaloDias + " dia(s).");
	}
	public static void encontrarAdversario() throws SQLException {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Digite o nome do seu time: ");
	    String TimeTime = scanner.nextLine();

	
	    String url = "jdbc:mysql://localhost:3306/TimesCadastro";
	    String username = "root";
	    String password = "";

	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	       
	        String query = "SELECT t2.NomeTime FROM InfoTimes t1 JOIN times t2 ON t1.IDTime = t2.id WHERE t2.NomeTime != ? ORDER BY RAND() LIMIT 1";

	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, TimeTime);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    String adversario = resultSet.getString("NomeTime");
	                    System.out.printf("O seu time  '%s' enfrentará o time '%s'.%n", TimeTime, adversario);
	                } else {
	                    System.out.println("Não foi possível encontrar um adversário.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}

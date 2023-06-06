package com.mycompany.lethicia.jar.individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Timer;

public class TesteLogin {

    public static void main(String[] args) {
        ApiLooca metodosLooca = new ApiLooca();

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        JdbcTemplate conexao = conexaoBanco.getConnection();

        Conexaosql conexaoBancosql = new Conexaosql();
        JdbcTemplate conexaosql = conexaoBancosql.getConnection();

        Scanner leitor = new Scanner(System.in);
        int escolha;

        System.out.println("""
                               -------------------------------
                               | Olá bem vindo a Hemera Tech |
                               -------------------------------
                               |  Escolha uma opção do menu  |
                               -------------------------------
                               | 1 - Fazer Login             |
                               | 0 - Sair                    |
                               -------------------------------
                               """);
        escolha = leitor.nextInt();
        leitor.nextLine(); // Consumir a quebra de linha pendente

        switch (escolha) {
            case 1:
                System.out.println("Email: ");
                String email = leitor.nextLine();
                System.out.println("Senha: ");
                String senha = leitor.nextLine();
                // O símbolo ? na query serão substituídos pelas variáveis "email" e "senha"

                String selectUsuario = "SELECT * FROM Funcionario WHERE email = ? AND senha = ?";
                List<Usuario> usuarioLogadoBanco = conexao.query(selectUsuario, new BeanPropertyRowMapper<>(Usuario.class), email, senha);
                List<Usuario> usuarioLogadoMySql = conexaosql.query(selectUsuario, new BeanPropertyRowMapper<>(Usuario.class), email, senha);

                List<Usuario> computadoresMySql = new ArrayList<>();
                List<Usuario> computadoresBanco = new ArrayList<>();

                if (usuarioLogadoMySql.isEmpty() || usuarioLogadoBanco.isEmpty()) {
                    System.out.println("Login não efetuado! Erro!");
                } else {
                    if (!usuarioLogadoMySql.isEmpty()) {
                        computadoresMySql = conexaosql.query("SELECT idEmpresa, MacAddress FROM Computador WHERE idEmpresa = ? AND MacAddress = ?",
                                new BeanPropertyRowMapper<>(Usuario.class), usuarioLogadoMySql.get(0).getIdEmpresa(), metodosLooca.macAddress());

                    }

                    if (!usuarioLogadoBanco.isEmpty()) {
                        computadoresBanco = conexao.query("SELECT idEmpresa, MacAddress FROM Computador WHERE idEmpresa = ? AND MacAddress = ?",
                                new BeanPropertyRowMapper<>(Usuario.class), usuarioLogadoBanco.get(0).getIdEmpresa(), metodosLooca.macAddress());

                    }

                    System.out.println("Login efetuado com sucesso");

                    if (computadoresMySql.isEmpty()) {
                        System.out.println("Vamos cadastrar esse computador no banco");

                        conexaosql.update(String.format("insert into Computador (sistema_operacional, modelo, MacAddress, total_memoria, total_armazenamento, idEmpresa) values ('%s','%s','%s','%s','%s', %d)", metodosLooca.sistemaOperacional(), metodosLooca.modeloProcessador(), metodosLooca.macAddress(), metodosLooca.totalMemoria(), metodosLooca.totalDisco(), usuarioLogadoMySql.get(0).getIdEmpresa()));
                    } else if (computadoresBanco.isEmpty()) {
                         System.out.println("Vamos cadastrar esse computador na Azure");

                        conexao.update(String.format("insert into Computador (sistema_operacional, modelo, MacAddress, total_memoria, total_armazenamento, idEmpresa) values ('%s','%s','%s','%s','%s',%d)", metodosLooca.sistemaOperacional(), metodosLooca.modeloProcessador(), metodosLooca.macAddress(), metodosLooca.totalMemoria(), metodosLooca.totalDisco(), usuarioLogadoBanco.get(0).getIdEmpresa()));

                    } else {
                        System.out.println("Computador já está cadastrado");
                    }
                    System.out.println("COMEÇAR A REGISTRAR DADOS A CADA X SEGUNDOS");
                    Timer tempo = new Timer();
                    int intervaloSegundos = 20; // Defina o intervalo desejado em segundos
                    tempo.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            String selectComputador = "SELECT * FROM Computador WHERE idEmpresa = ? AND MacAddress = ?";
                            Computador computadoresBanco = conexao.queryForObject(selectComputador, new BeanPropertyRowMapper<>(Computador.class), usuarioLogadoBanco.get(0).getIdEmpresa(), metodosLooca.macAddress());
                            Computador computadoresMySql = conexaosql.queryForObject(selectComputador, new BeanPropertyRowMapper<>(Computador.class), usuarioLogadoMySql.get(0).getIdEmpresa(), metodosLooca.macAddress());

                            try {
                                metodosLooca.inserirDadosAzure(computadoresBanco);
                                metodosLooca.inserirDadosMySql(computadoresMySql);
                                System.out.println("Registros sendo capturados");
                            } catch (Exception e) {
                                System.out.println("Erro ao inserir dados: " + e.getMessage());
                            }
                        }
                    }, 0, intervaloSegundos * 1000); // O intervalo é definido em milissegundos

                }
                break;
            default:
                System.out.println("Digite uma opção válida!");
        }
    }
}

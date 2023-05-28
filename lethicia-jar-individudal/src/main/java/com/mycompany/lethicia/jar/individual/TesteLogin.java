/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lethicia.jar.individual;

import com.github.britooo.looca.api.core.Looca;
import java.util.List;
import java.util.Scanner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lethi
 */
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

                String selectUsuario = "select *from Funcionario where email = ? and senha =? ";
                List<Usuario> usuarioLogadoBanco = conexao.query(selectUsuario, new BeanPropertyRowMapper<>(Usuario.class), email, senha);
                List<Usuario> usuarioLogadoMySql = conexaosql.query(selectUsuario, new BeanPropertyRowMapper<>(Usuario.class), email, senha);

                if (usuarioLogadoMySql.get(0) == null && usuarioLogadoBanco.get(0) == null) {
                    System.out.println("Login não efetuado! Erro!");
                } else {

                    List<Usuario> computadoresBanco = conexao.query("select idEmpresa, MacAddress from Computador where idEmpresa = ? and MacAddress = ?",
                            new BeanPropertyRowMapper(Usuario.class), usuarioLogadoBanco.get(0).getIdEmpresa(), metodosLooca.hostName);
                    List<Usuario> computadoresMySql = conexaosql.query("select idEmpresa, MacAddress from Computador where idEmpresa = ? and MacAddress = ?",
                            new BeanPropertyRowMapper(Usuario.class), usuarioLogadoMySql.get(0).getIdEmpresa(), metodosLooca.hostName);
                    System.out.println(usuarioLogadoMySql);
                    System.out.println(computadoresMySql);
                    System.out.println("Login efetuado com sucesso");

                    if (computadoresMySql.isEmpty() && computadoresBanco.isEmpty()) {
                        System.out.println("vamos cadastrar esse computador");

                        conexaosql.update(String.format("INSERT INTO Computador (sistema_operacional, modelo, MacAddress, total_memoria, total_armazenamento, idEmpresa) values ('%s',' Intel(R) Core(TM) ','%s','%s','%s','%d')", metodosLooca.sistemaOperacional, metodosLooca.hostName, String.valueOf(metodosLooca.totalRam), String.valueOf(metodosLooca.totalDisco), usuarioLogadoMySql.get(0).getIdEmpresa()));
                        conexao.update(String.format("INSERT INTO Computador (sistema_operacional, modelo, MacAddress, total_memoria, total_armazenamento, idEmpresa) values ('%s',' Intel(R) Core(TM)','%s','%s','%s','%d')", metodosLooca.sistemaOperacional, metodosLooca.hostName, String.valueOf(metodosLooca.totalRam), String.valueOf(metodosLooca.totalDisco), usuarioLogadoBanco.get(0).getIdEmpresa()));
                    } else {
                        System.out.println("computador já está cadastrado");
                    }
                }
                break;
            default:
                System.out.println("Digite uma opção válida!");
        }
    }
}

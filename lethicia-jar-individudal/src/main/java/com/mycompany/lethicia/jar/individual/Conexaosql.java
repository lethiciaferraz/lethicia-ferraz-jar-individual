/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lethicia.jar.individual;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lethi
 */
public class Conexaosql {
     private JdbcTemplate connection;

    public Conexaosql() {

        BasicDataSource dataSource = new BasicDataSource();

        
        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //dataSource​.setUrl("jdbc:mysql://54.175.61.147:3306/HemeraTech?useTimezone=true&userTimerzone=UTC");
        dataSource.setUrl("jdbc:mysql://0.0.0.0:3306/HemeraTech?autoReconnect=true&useSSL=false");

        //dataSource​.setUrl("jdbc:mysql://localhost:3306/hemeratech?serverTimezone=America/Sao_Paulo");

        dataSource​.setUsername("root");
        dataSource​.setPassword("urubu100");

        this.connection = new JdbcTemplate(dataSource);

    }

    public JdbcTemplate getConnection() {

        return connection;

    }

}

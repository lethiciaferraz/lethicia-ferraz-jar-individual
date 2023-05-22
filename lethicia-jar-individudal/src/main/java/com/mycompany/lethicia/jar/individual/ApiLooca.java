package com.mycompany.lethicia.jar.individual;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lethi
 */
public class ApiLooca {
    Looca apiLooca =  new Looca();
    
    Sistema sistema = apiLooca.getSistema();
    DiscoGrupo discos = apiLooca.getGrupoDeDiscos();
    Memoria memoria = apiLooca.getMemoria();
    Processador processador = apiLooca.getProcessador();
    Rede rede = apiLooca.getRede();
    Temperatura temperatura = apiLooca.getTemperatura();
    
    String sistemaOperacional = sistema.getSistemaOperacional();
    String modeloProcessador = processador.getFabricante();
    String hostName = rede.getParametros().getHostName();
    Long totalRam = memoria.getDisponivel();
    Integer totalDisco = discos.getQuantidadeDeDiscos();
   
}

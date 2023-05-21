/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lethicia.jar.individual;

/**
 *
 * @author lethi
 */
public class Usuario {

    private String email;
    private String senha;
    private Integer idEmpresa;

    public Usuario(String email, String senha, Integer idEmpresa) {
        this.email = email;
        this.senha = senha;
        this.idEmpresa = idEmpresa;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "Usuario{" + "email=" + email + ", senha=" + senha + ", idEmpresa=" + idEmpresa + '}';
    }

}

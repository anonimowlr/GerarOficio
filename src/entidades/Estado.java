/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author PC_LENOVO
 */
public class Estado {
    
    private String area_km2;
    private int codigo_ibge;
    private String nome;

    public Estado() {
    }
    
    

    public String getArea_km2() {
        return area_km2;
    }

    public void setAre_km2(String are_km2) {
        this.area_km2 = are_km2;
    }

    public int getCodigo_ibge() {
        return codigo_ibge;
    }

    public void setCodigo_ibge(int codigo_ibge) {
        this.codigo_ibge = codigo_ibge;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}

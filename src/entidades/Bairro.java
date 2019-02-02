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
public class Bairro {
    private String bairro;//nome do bairro
    private String cidade;//nome da cidade
    private String logradouro;// rua do bairro
    private Estado estado_info;//informaçoes do estado
    private Cidade cidade_info;//informaçoes da cidade
    private String estado;//sigla do estado ao qiual o bairro pertence
    private String cep ;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Bairro() {
    }

    
    
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Estado getEstado_info() {
        return estado_info;
    }

    public void setEstado_info(Estado estado_info) {
        this.estado_info = estado_info;
    }

    public Cidade getCidade_info() {
        return cidade_info;
    }

    public void setCidade_info(Cidade cidade_info) {
        this.cidade_info = cidade_info;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}

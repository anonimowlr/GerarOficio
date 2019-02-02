/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import apicorreios.HttpClientApiCorreio;
import com.google.gson.Gson;
import entidades.Bairro;
import entidades.Cidade;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC_LENOVO
 */
public class BairroDAO {

    public List<Bairro> buscar(String cep)  {
        List<Bairro> listaBairros = new ArrayList<>();
 
      
        HttpClientApiCorreio consumidor = HttpClientApiCorreio.getInstance();

        listaBairros = leitorJson(consumidor.doRequest(Integer.toString(Integer.parseInt("" + cep + ""))));
      
        return listaBairros;

    }

    private static List<Bairro> leitorJson(String conteudo) {
        if(conteudo==null){
            return  null;
        }
        
        List<Bairro> lista = null;

       
            Bairro bairro = null;
            Gson gson = new Gson();
            String reader = conteudo;
            bairro = gson.fromJson(reader, Bairro.class);
            lista = new ArrayList<>();
            lista.add(bairro);
     
        return lista;

    }

}

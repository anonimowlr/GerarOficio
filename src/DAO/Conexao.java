/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author f2419488
 */
public class Conexao {

    private Connection conn;
    private boolean conectado;
    private final String driver;
    private final String banco;
    private final String host;
    private final String url;
    private final String usuario;
    private final String senha;

    //conexão no BD
    public Conexao() {
    String usarBanco;    
    usarBanco = "local";    
    this.conn = null;
    this.conectado = false;
    this.driver = "com.mysql.jdbc.Driver"; //Classe do driver JDBC
    this.banco = "bdjudicial"; ///servidor
        if ("local".equals(usarBanco)) {//Para gravar local-deixar apenas "local" na condição
            this.host = "localhost"; //ip do banco de dados
            this.usuario = "root";
            this.senha = "root";
            this.url = "jdbc:mysql://" + host + ":3306/" + banco; //URL de conexão
        }else{
            this.host = "10.105.87.250";
            this.usuario = "basso";  
            this.senha = "2579368410";
           this.url = "jdbc:mysql://" + host + ":3306/" + banco; //URL de conexão
        }

 
    
    }

    public boolean autenticar() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            conectado = false;
            return conectado;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            conectado = false;
            return conectado;
        }
        conectado = true;
        return conectado;
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apicorreios;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author PC_LENOVO
 */
public class HttpClientApiCorreio {
    
    
    private static  String  URLBase = "http://api.postmon.com.br/v1/cep//";
   private static String token = "3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3";
    private static HttpClientApiCorreio instance;
    
    private CloseableHttpClient clienteHTTP;
    
    
    
    private HttpClientApiCorreio (){
        this.clienteHTTP = HttpClients.createDefault();
        
    }
        
    
    public static HttpClientApiCorreio getInstance(){
        
        if(instance == null){
            
            instance = new HttpClientApiCorreio();
            
        }
         
        return instance;
    }
    
    
    
    public void doLogin (){
         try {
            
             HttpPost httpPost = new HttpPost(HttpClientApiCorreio.URLBase + "/Login/Autenticar?token=" + HttpClientApiCorreio.token);
             ResponseHandler<String>  responseHandler =  new ResponseHandler<String>() {
                 @Override
                 public String handleResponse(final HttpResponse response) throws  ClientProtocolException,IOException{
                     int status = response.getStatusLine().getStatusCode();
                            if(status>=200 && status<300){
                                
                                HttpEntity entity = response.getEntity();
                                return entity != null ? EntityUtils.toString(entity) : null;
                                
                            } else{
                                throw  new  ClientProtocolException("Inesperado status de responsta:" + status);
                            }
                     
                

                 }
             };
             
             String responseBody = this.clienteHTTP.execute(httpPost, responseHandler);
             System.out.println("---------------------------------------------------------------------------");
             System.out.println(responseBody);
             
        } catch (Exception e) {
            Logger.getLogger(HttpClientApiCorreio.class.getName()).log(Level.SEVERE, null,e);
        }
        
    }
    
    
    
    public String doRequest(String path){
        String responseBody = null;
        
        try {
            
            HttpGet httpGet =  new  HttpGet(HttpClientApiCorreio.URLBase+path);
            
             ResponseHandler<String>  responseHandler =  new ResponseHandler<String>() {
                 @Override
                 public String handleResponse(final HttpResponse response) throws  ClientProtocolException,IOException{
                     int status = response.getStatusLine().getStatusCode();
                            if(status>=200 && status<300){
                                
                                HttpEntity entity = response.getEntity();
                                return entity !=null ? EntityUtils.toString(entity) : null;
                                
                            } else{
                                throw  new  ClientProtocolException("Inesperado status de resposta:" + status);
                            }
                     
                

                 }
             };
             
             responseBody = this.clienteHTTP.execute(httpGet, responseHandler);
             System.out.println("----------------------------------------------------------------------");
            
        } catch (Exception e) {
            
        }
        return responseBody;
        
    }
    
    
    
}

package com.distribuidos.servicorastreamento.servidor;

import com.distribuidos.servicorastreamento.erroMensagens.ErroMensagens;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;


public class Servidor {

    private final ArrayList listaDePacotes;
    private ServerSocket servidorSocket;
    private Socket clienteSocket;
    private boolean isRunning;
    private ServidorInterface servidorInterface;
    
    
    public Servidor(){
        listaDePacotes = new ArrayList<JSONObject>();
    }
    
    private void start(){
        try
        {
            servidorSocket = new ServerSocket(65000);
            System.out.println("Servidor iniciado");
        }
        catch(IOException ex)
        {
            String msg = "Servidor não iniciado";
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, msg, ex);
            ErroMensagens.avisoErro(msg);
        }
    }
    
    private void running(){
        aceitarConexoes();
        fecharServidor();
    }
    
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.servidorInterface = new ServidorInterface(servidor);
        servidor.servidorInterface.setVisible(true);
        
        servidor.start();
        servidor.running();
    }
    
    private void aceitarConexoes(){
        isRunning = true;
        try 
        {
            while(isRunning){
                clienteSocket = servidorSocket.accept();
                new ThreadServidor(clienteSocket, this).start();     
            }
        }
        catch (IOException ex) 
        {
            String msg = "Erro ao aceitar conexões";
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }
    
    public void fecharServidor(){
        try
        {
            servidorSocket.close();
        } 
        catch (IOException ex)
        {
            String msg = "Erro ao fechar servidor";
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, msg, ex);
            ErroMensagens.avisoErro(msg);
        }
    }
    
    public void addPacote(JSONObject pacote) {
        listaDePacotes.add(pacote);
        System.out.println(pacote);
        
        servidorInterface.atualizarTabela(listaDePacotes);
    }
    
    public JSONObject getPacote(long codigoRastreio){
        Iterator<JSONObject> iterator = listaDePacotes.iterator();
       
        while(iterator.hasNext()){
            
            JSONObject pacote = iterator.next();

            if(pacote.getLong("codigoRastreio") == codigoRastreio){
                return pacote;
            }
        }
        
        return new JSONObject("{}");
    }
}

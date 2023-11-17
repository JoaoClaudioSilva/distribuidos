package com.distribuidos.servicorastreamento.servidor;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class ThreadServidor extends Thread {

    private final Socket clienteSocket;
    private final Servidor servidor;
    private DataInputStream entrada;
    private DataOutputStream saida;
    private long codigoRastreio;

    public ThreadServidor(Socket clienteSocket, Servidor servidor) {
        this.clienteSocket = clienteSocket;
        this.servidor = servidor;
    }
        
    @Override
    public void run(){

        try
        {
            while(true) {
                entrada = new DataInputStream(new BufferedInputStream(clienteSocket.getInputStream()));
                saida = new DataOutputStream(new BufferedOutputStream(clienteSocket.getOutputStream())); 

                codigoRastreio = entrada.readLong();
                JSONObject pacote = servidor.getPacote(codigoRastreio);


                saida.writeUTF(pacote.toString());
                saida.flush();
            }
        }
        catch(IOException ex)
        {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, "Conexão com o cliente fechada", ex);
        }
        
        try
        {
            saida.close();
            entrada.close();
            clienteSocket.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

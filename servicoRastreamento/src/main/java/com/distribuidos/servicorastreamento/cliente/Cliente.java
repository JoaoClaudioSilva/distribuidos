package com.distribuidos.servicorastreamento.cliente;

import com.distribuidos.servicorastreamento.erroMensagens.ErroMensagens;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;


public class Cliente {
       
    private Socket clienteSocket;
    private DataInputStream entrada;
    private DataOutputStream saida;
    private ClienteInterface clienteInterface;
    private ArrayList listaDePacotes;

    public ArrayList getListaDePacotes() {
        return listaDePacotes;
    }
        
    public Cliente(){
        listaDePacotes = new ArrayList<JSONObject>();
    }
    
    private void start() {
        try 
        {
            clienteSocket = new Socket("Servidor", 65000);
         
            entrada = new DataInputStream(clienteSocket.getInputStream());
            saida = new DataOutputStream(clienteSocket.getOutputStream());
        } 
        
        catch(IOException ex)
        {
            String msg = "Erro ao conectar cliente";
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, msg, ex);
            ErroMensagens.avisoErro(msg);
        }
    }
    
    public void consultarCodigo(long codigoRastreio){
        try
        {
            saida.writeLong(codigoRastreio);
                       
            JSONObject pacote = new JSONObject(entrada.readUTF());
            System.out.println(pacote);
            
            if(!pacote.isEmpty()) {
                if(!listaDePacotes.equals((Object)pacote)){
                    listaDePacotes.add(pacote);
                    clienteInterface.atualizarCombo(listaDePacotes);
                }
            }
            else
            {
                ErroMensagens.avisoErro("Erro: Código de rastreio não existe no servidor");
            }
        }
        catch(IOException ex)
        {
            String msg = "Erro ao enviar código";
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, msg, ex);
            ErroMensagens.avisoErro(msg);
        }
    }
    

    
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.clienteInterface = new ClienteInterface(cliente);
        cliente.clienteInterface.setVisible(true);
        
        cliente.start();
    }

    public void fecharCliente() {
        try
        {
            entrada.close();
            saida.close();
            clienteSocket.close();
        } 
        catch (IOException ex)
        {
            String msg = "Erro ao fechar servidor";
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, msg, ex);
            ErroMensagens.avisoErro(msg);
        }
    }
}

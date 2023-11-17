
package com.distribuidos.servicorastreamento.erroMensagens;

import javax.swing.JOptionPane;


public class ErroMensagens {
    public static void avisoErro(String mensagemErro) {    
        JOptionPane.showMessageDialog(null, mensagemErro, "Erro", JOptionPane.ERROR_MESSAGE);   
    }
}

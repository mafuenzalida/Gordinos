package com.me.gordinos.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;



public class Cliente {
	
	private Client cliente;
    private String nombre = "pepito"; //Nombre perfil
    private int imagen; //Numero imagen de perfil
    
    public String nombre1 = "";
    public String nombre2 = "";
    public int imagen1 = 0;
    public int imagen2 = 0;
    public String id1 = "";
    public String id2 = "";
    
    public Cliente(final String nombre, final int imagen) throws IOException {
    	
    	cliente = new Client();
        cliente.start();
        
        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the cliente and server.  
        Network.register(cliente);
        

    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public int getImagen() {
    	return imagen;
    }
    
    public Client getCliente() {
    	return cliente;
    }
    
    public void agregarListener(Listener listener)	{
    	cliente.addListener(listener);
    }
    
    public void quitarListener(Listener listener) {
    	cliente.removeListener(listener);
    }    
    
}

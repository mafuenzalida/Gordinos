package com.me.gordinos.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Servidor {

	private Server servidor;
	public int contusers = 0;

	public Servidor() throws IOException {
		servidor = new Server() {
			protected Connection newConnection() {
				// By providing our own connection implementation, we can store
				// per
				// connection state without a connection ID to state look up.
				return new GameConnection();
			}
		};

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(servidor);

		//servidor.bind(Network.port);
		//servidor.start();
	}
	
	public Server getServidor() {
		return servidor;
	}

	public static class GameConnection extends Connection {
		public String nombre;
		public int imagen;
	}
	
	public void agregarListener(Listener listener)	{
    	servidor.addListener(listener);
    }
    
    public void quitarListener(Listener listener) {
    	servidor.removeListener(listener);
    }    
}

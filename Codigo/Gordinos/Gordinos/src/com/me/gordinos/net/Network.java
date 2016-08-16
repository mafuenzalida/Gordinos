package com.me.gordinos.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 9990;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(RegisterName.class);
		kryo.register(RegisterNames.class);
		kryo.register(String[].class);
		kryo.register(ListaImagenes.class);
		kryo.register(Message.class);
	}

	static public class RegisterName {
		public String name;
		public int img;
	}
	
	static public class RegisterNames {
		public String name1;
		public String name2;
		public int img1;
		public int img2;
		public String id1;
		public String id2;
	}
	
	static public class UpdateNames {
		public String[] names;
	}

	static public class Message {
		public String text;
	}
	
	static public class ListaImagenes {
		public String imagenes;
	}
}
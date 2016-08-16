package com.me.gordinos;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.me.gordinos.net.*;
import com.me.gordinos.net.Network.Message;
import com.me.gordinos.net.Network.RegisterName;
import com.me.gordinos.net.Servidor.GameConnection;
import com.me.gordinos.net.Network.RegisterNames;

public class ConexionScreen extends AbstractScreen {

	private Table table;
	private Cliente cliente;
	private Servidor servidor;

	public ConexionScreen(Gordinos game) {
		super(game);
	}

	@Override
	public void show() {

		super.show();

		table = super.getTable();
		table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2,
				(Gdx.graphics.getHeight() - table.getHeight()) / 2);
		table.add("Escoja una opcion:").spaceBottom(50);
		table.row();

		TextButton servidorGameButton = new TextButton("Servidor", getSkin());
		servidorGameButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				try {
					comenzarServidor();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					// servidor.getServidor().bind(Network.port);s
					servidor.getServidor().bind(Network.port, 8000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				servidor.agregarListener(new ServidorListener());

				servidor.getServidor().start();
				Gdx.app.log(Gordinos.LOG, "SERVIDOR CONECTADO");

				try {
					comenzarCliente();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cliente.agregarListener(new ClienteListener());

				try {

					InetAddress address = InetAddress.getLocalHost();

					TextButton text1 = new TextButton(
							"TRATANDO DE CONECTAR EN: "
									+ address.getHostAddress(), getSkin());
					text1.setPosition(100, 400);
					stage.addActor(text1);
					cliente.getCliente().connect(5000, address, Network.port,
							8000);

					// }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Gdx.app.log(Gordinos.LOG, "Y CLIENTE CONECTADO");

				game.setScreen(new Conexion2Screen(game, cliente, servidor));
			}
		});
		table.add(servidorGameButton).size(300, 60).uniform().spaceBottom(50);
		table.row();

		TextButton clienteButton = new TextButton("Cliente", getSkin());
		clienteButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;

			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				try {
					comenzarCliente();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cliente.agregarListener(new ClienteListener());

				try {

					InetAddress address = cliente.getCliente().discoverHost(
							8000, 5000);
					if (address != null) {
						TextButton text1 = new TextButton(
								"TRATANDO DE CONECTAR EN: "
										+ address.getHostAddress(), getSkin());
						text1.setPosition(100, 400);
						stage.addActor(text1);
					
					cliente.getCliente().connect(5000, address, Network.port,
							8000);
					
					game.setScreen(new Conexion2Screen(game, cliente, servidor));
					}
					else {
						// TODO Auto-generated catch block
						TextButton text1 = new TextButton(
								"NO HAY SERVIDOR DISPONIBLE PORFAVOR INTENTE DENUEVO",
								getSkin());
						text1.setPosition(100, 400);
						stage.addActor(text1);
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}

				Gdx.app.log(Gordinos.LOG, "CLIENTE CONECTADO");
			}
		});
		table.add(clienteButton).uniform().fill().spaceBottom(10);

		stage.addActor(table);

	}

	public void comenzarCliente() throws IOException {
		cliente = new Cliente(nombre, imagen);
	}

	public void comenzarServidor() throws IOException {
		servidor = new Servidor();
	}

	public class ClienteListener extends Listener {

		public void connected(Connection connection) {
			RegisterName registerName = new RegisterName();
			registerName.name = nombre;
			registerName.img = imagen;
			cliente.getCliente().sendTCP(registerName);
		}

		public void received(Connection connection, Object object) {

			if (object instanceof RegisterNames) {

				RegisterNames registerNames = (RegisterNames) object;

				cliente.nombre1 = registerNames.name1;
				cliente.imagen1 = registerNames.img1;
				cliente.id1 = registerNames.id1;

				cliente.nombre2 = registerNames.name2;
				cliente.imagen2 = registerNames.img2;
				cliente.id2 = registerNames.id2;
			}

			if (object instanceof Message) {
				empezar = true;
			}

		}
	}

	public class ServidorListener extends Listener {
		public void received(Connection c, Object object) {
			// We know all connections for this server are actually
			// GameConnections.
			GameConnection connection = (GameConnection) c;
			if (object instanceof RegisterName) {
				RegisterName registerName = (RegisterName) object;
				connection.nombre = registerName.name;
				connection.imagen = registerName.img;
				servidor.contusers++;
				if (servidor.contusers == 3) {

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					for (int i = 0; i < 3; i++) {
						RegisterNames registerNames = new RegisterNames();
						int id = servidor.getServidor().getConnections()[i]
								.getID();
						int cont = 1;
						for (int j = 0; j < 3; j++) {
							if (servidor.getServidor().getConnections()[j]
									.getID() != id) {
								GameConnection user = (GameConnection) servidor
										.getServidor().getConnections()[j];
								if (cont == 1) {
									registerNames.name1 = user.nombre;
									registerNames.img1 = user.imagen;
									registerNames.id1 = user.getID() + "";
								} else {
									registerNames.name2 = user.nombre;
									registerNames.img2 = user.imagen;
									registerNames.id2 = user.getID() + "";
								}
								cont++;
							}
						}

						servidor.getServidor().sendToTCP(id, registerNames);

					}

					Message msg = new Message();
					msg.text = "Empezar";
					servidor.getServidor().sendToAllTCP(msg);
				}
				return;
			}

		}
	}

	@Override
	public void hide() {
		stage.clear();
		dispose();
	}

	@Override
	public void render(float delta) {
		if (empezar) {
			game.setScreen(new LunchTimeScreen(game, cliente, servidor));
		}
		super.render(delta);
	}

}

package com.me.gordinos;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.me.gordinos.net.Cliente;
import com.me.gordinos.net.Network.ListaImagenes;
import com.me.gordinos.net.Network.Message;
import com.me.gordinos.net.Servidor;
import com.me.gordinos.net.Servidor.GameConnection;

public class LunchTimeScreen extends AbstractScreen {

	private Cliente cliente;
	private Servidor servidor;
	private int cont = 0;
	private int cont1 = 0;
	private int cont2 = 0;
	private int[] listaImagenes;
	private int contfinal = 0;

	private Music music = Gdx.audio.newMusic(Gdx.files
			.internal("data/music.mp3"));

	public LunchTimeScreen(Gordinos game, Cliente cliente, Servidor servidor) {
		super(game);
		// TODO Auto-generated constructor stub
		this.cliente = cliente;
		this.servidor = servidor;
		listaImagenes = new int[100];
	}

	@Override
	public void show() {

		cliente.agregarListener(new ListenerLunchTimeCliente());
		
		//Esperamos un poco para que alcanze a agregar listener
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Agregamos los Listeners tanto de cliente y servidor
		if (servidor != null)
			servidor.agregarListener(new ListenerLunchTimeServidor(servidor
					.getServidor()));

		// Solo si se maneja servidor
		if (servidor != null) {
			// Creamos lista de imagenes a hacer loop y enviamos a clientes
			String lista = ""; // 30 pueden ser mas
			Random random = new Random();
			int num = 0;
			int anterior = -1;
			for (int i = 0; i < 100; i++) {
				do {
					num = random.nextInt(13);
				} while (anterior == num);

				anterior = num;
				if (i != 0) {
					lista += "&" + num;
				} else {
					lista += num;
				}
			}
			ListaImagenes listaImagenes = new ListaImagenes();
			listaImagenes.imagenes = lista;
			servidor.getServidor().sendToAllTCP(listaImagenes);

		}

		// Preparamos todo el tablero para jugar
		// AQUI DIBUJAR TODO LO ESENCIAL
		music.setLooping(true);
		music.play();
		prepararPantalla();
		Gdx.input.setInputProcessor(stage);
		// Agregamos thread para logica de
		// juego

		// Main loop del juego
		conteoRegresivo();
		
		//new Thread(new mainLoop()).start();

	}

	public class mainLoop implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// Aqui va el loop principal del juego
			for (int i = 20; i < 100; i++) {
				// Mostramos la imagen

				Gdx.app.postRunnable(new pintorImagen(i));

				// Esperamos segundos segun fase
				if (i < 20) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (i < 40) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (i < 60) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (i < 80) {
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			Gdx.app.postRunnable(new terminarJuego());

		}

	}

	public class terminarJuego implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			game.setScreen(new PuntuacionesScreen(game, contfinal, cont, cont1,
					cont2, cliente));
		}

	}

	public class pintorImagen implements Runnable {
		private int i;

		public pintorImagen(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			if (listaImagenes[i] <= 6) {
				contfinal++;
			} else {
				contfinal--;
			}
			Texture Textaux = new Texture(comidas[listaImagenes[i]]);
			Image comidaaux = new Image(Textaux);
			comidaaux.setSize(250, 250);
			comidaaux.setPosition(50, 200);
			stage.getActors().removeIndex(11);
			stage.getActors().insert(11, comidaaux);
		}

	}

	public void conteoRegresivo() {
		// load the texture with the splash image
				Texture UnoTexture = new Texture( "images/Extras/uno.png" );
				Texture DosTexture = new Texture( "images/Extras/dos.png" );
				Texture TresTexture = new Texture( "images/Extras/tres.png" );

		        // set the linear texture filter to improve the image stretching
				UnoTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
				DosTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
				TresTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
		        

		        // in the image atlas, our splash image begins at (0,0) of the
		        // upper-left corner and has a dimension of 512x301
		        TextureRegion UnoRegion = new TextureRegion( UnoTexture, 0, 0, 256, 256 );
		        TextureRegion DosRegion = new TextureRegion( DosTexture, 0, 0, 256, 256 );
		        TextureRegion TresRegion = new TextureRegion( TresTexture, 0, 0, 256, 256 );

		        // here we create the splash image actor and set its size
		        final Image UnoImage = new Image( UnoRegion);
		        final Image DosImage = new Image( DosRegion);
		        Image TresImage = new Image( TresRegion);
		        
		        
		        UnoImage.setSize(256,256);
		        UnoImage.setPosition((Gdx.graphics.getWidth() - UnoImage.getWidth())/2, (Gdx.graphics.getHeight() - UnoImage.getHeight())/2);
		        
		        DosImage.setSize(256,256);
		        DosImage.setPosition((Gdx.graphics.getWidth() - UnoImage.getWidth())/2, (Gdx.graphics.getHeight() - UnoImage.getHeight())/2);
		        
		        TresImage.setSize(256,256);
		        TresImage.setPosition((Gdx.graphics.getWidth() - UnoImage.getWidth())/2, (Gdx.graphics.getHeight() - UnoImage.getHeight())/2);

		        // this is needed for the fade-in effect to work correctly; we're just
		        // making the image completely transparent
		        UnoImage.getColor().a = 0f;
		        DosImage.getColor().a = 0f;
		        TresImage.getColor().a = 0f;

		        UnoImage.addAction( Actions.sequence( Actions.fadeIn(0f), Actions.delay(0f), Actions.fadeOut( 3f ),
		                new Action() {
		                    @Override
		                    public boolean act(
		                        float delta )
		                    {
		                        // the last action will move to the next screen
		                    	
		                    	// A PERFIL!!!!
		                        new Thread(new mainLoop()).start();
		                        return true;
		                    }
		                } ) );
		        
		        DosImage.addAction( Actions.sequence( Actions.fadeIn(0f), Actions.delay(0f), Actions.fadeOut( 3f ),
		                new Action() {
		                    @Override
		                    public boolean act(
		                        float delta )
		                    {
		                        stage.addActor( UnoImage );
		                        return true;
		                    }
		                } ) );
		        
		        TresImage.addAction( Actions.sequence(  Actions.fadeIn(0f), Actions.delay(0f), Actions.fadeOut( 3f ),
		                new Action() {
		                    @Override
		                    public boolean act(
		                        float delta )
		                    {
		                    	stage.addActor(DosImage);
		                        return true;
		                    }
		                } ) );

		        // and finally we add the actors to the stage, on the correct order
		        stage.addActor(TresImage);
	}

	public void prepararPantalla() {
		// Personajes
		Texture Text1 = new Texture(personajes[cliente.imagen1]);
		Image Personaje1 = new Image(Text1);

		Texture Text2 = new Texture(personajes[imagen]);
		Image Personaje2 = new Image(Text2);

		Texture Text3 = new Texture(personajes[cliente.imagen2]);
		Image Personaje3 = new Image(Text3);

		// Contadores
		Label cont1 = new Label(this.cont1 + "", getSkin());
		Label cont2 = new Label(cont + "", getSkin());
		Label cont3 = new Label(this.cont2 + "", getSkin());

		// Nombres
		Label name1 = new Label(cliente.nombre1, getSkin());
		Label name2 = new Label(nombre, getSkin()); // Propio
													// nombre
		Label name3 = new Label(cliente.nombre2, getSkin());

		// Image de las comidas
		Texture Text4 = new Texture("images/personajes/petter_grifin.png");
		Image comida = new Image(Text4);

		// Botones

		// MAS
		Texture Text5 = new Texture("images/Extras/mas.png");
		Image mas = new Image(Text5);
		Texture Text6 = new Texture("images/Extras/masDown.png");
		Image masDown = new Image(Text6);

		Drawable masDraw = mas.getDrawable();
		Drawable masDownDraw = masDown.getDrawable();
		Button ButtonMas = new Button(masDraw, masDownDraw);

		// MENOS
		Texture Text7 = new Texture("images/Extras/menos.png");
		Image menos = new Image(Text7);
		Texture Text8 = new Texture("images/Extras/menosDown.png");
		Image menosDown = new Image(Text8);

		Drawable menosDraw = menos.getDrawable();
		Drawable menosDownDraw = menosDown.getDrawable();
		Button ButtonMenos = new Button(menosDraw, menosDownDraw);

		// Listeners de Botones
		ButtonMas.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				cont++;
				Label contAux = new Label(cont + "", getSkin());
				contAux.setSize(50, 50);
				contAux.setPosition(345, 155);
				stage.getActors().removeIndex(7);
				stage.getActors().insert(7, contAux);

				// Enviamos informacion a servidor
				Message msg = new Message();
				msg.text = "++&";
				cliente.getCliente().sendTCP(msg);

			}

		});

		ButtonMenos.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				cont--;
				Label contAux = new Label(cont + "", getSkin());
				contAux.setSize(50, 50);
				contAux.setPosition(345, 155);
				stage.getActors().removeIndex(7);
				stage.getActors().insert(7, contAux);

				// Enviamos informacion a servidor
				Message msg = new Message();
				msg.text = "--&";
				cliente.getCliente().sendTCP(msg);

			}

		});

		// Seteamos los tamaños, posiciones, etc de TODOS los elementos a
		// agregar

		// Personajes
		Personaje1.setSize(100, 100);
		Personaje2.setSize(100, 100);
		Personaje3.setSize(100, 100);
		Personaje1.setPosition(100, 50);
		Personaje2.setPosition(300, 50);
		Personaje3.setPosition(500, 50);

		// Nombres
		name1.setSize(100, 30);
		name2.setSize(100, 30);
		name3.setSize(100, 30);
		name1.setPosition(125, 10);
		name2.setPosition(325, 10);
		name3.setPosition(525, 10);

		// Contadores
		cont1.setSize(50, 50);
		cont2.setSize(50, 50);
		cont3.setSize(50, 50);
		cont1.setPosition(145, 155);
		cont2.setPosition(345, 155);
		cont3.setPosition(545, 155);

		// Botones
		ButtonMas.setSize(80, 80);
		ButtonMas.setPosition(470, 330);
		ButtonMenos.setSize(80, 80);
		ButtonMenos.setPosition(600, 330);

		// Comida
		comida.setSize(250, 250);
		comida.setPosition(50, 200);

		// Agregamos los actores a la stage

		// Personajes
		stage.addActor(Personaje1);
		stage.addActor(Personaje2);
		stage.addActor(Personaje3);

		// Nombres
		stage.addActor(name1);
		stage.addActor(name2);
		stage.addActor(name3);

		// Contadores
		stage.addActor(cont1);
		stage.addActor(cont2);
		stage.addActor(cont3);

		// Botones
		stage.addActor(ButtonMas);
		stage.addActor(ButtonMenos);
		// Comida
		stage.addActor(comida);
	}

	public class ListenerLunchTimeCliente extends Listener {

		public void received(Connection connection, Object object) {

			if (object instanceof Message) {
				Message msg = (Message) object;
				Gdx.app.log(Gordinos.LOG, msg.text);
				String[] mensaje = msg.text.split("&");
				Gdx.app.log(Gordinos.LOG, mensaje[1] + " y " + cliente.nombre1);
				if (mensaje[1].equals(cliente.id1)) {
					Gdx.app.log(Gordinos.LOG, "OBVIO PO LOCO");
					if (mensaje[0].equals("++")) {
						cont1++;
					} else {
						cont1--;
					}
					Label contAux = new Label(cont1 + "", getSkin());
					contAux.setSize(50, 50);
					contAux.setPosition(145, 155);
					stage.getActors().removeIndex(6);
					stage.getActors().insert(6, contAux);
				}
				if (mensaje[1].equals(cliente.id2)) {
					if (mensaje[0].equals("++")) {
						cont2++;
					} else {
						cont2--;
					}
					Label contAux = new Label(cont2 + "", getSkin());
					contAux.setSize(50, 50);
					contAux.setPosition(545, 155);
					stage.getActors().removeIndex(8);
					stage.getActors().insert(8, contAux);
				}
			}

			if (object instanceof ListaImagenes) {
				ListaImagenes listaImagen = (ListaImagenes) object;
				Gdx.app.log(Gordinos.LOG, listaImagen.imagenes);
				String[] listaAux = listaImagen.imagenes.split("&");
				for (int i = 0; i < 100; i++) {
					listaImagenes[i] = Integer.parseInt(listaAux[i]);
				}
				return;
			}
		}

	}

	public class ListenerLunchTimeServidor extends Listener {

		private Server servidor;

		public ListenerLunchTimeServidor(Server servidor) {
			this.servidor = servidor;
		}

		public void received(Connection c, Object object) {
			// We know all connections for this server are actually
			// GameConnections.
			GameConnection connection = (GameConnection) c;

			if (object instanceof Message) {
				Message msg = (Message) object;
				msg.text += connection.getID();
				// Reenviamos a todos menos al que envio
				servidor.sendToAllExceptUDP(connection.getID(), msg);

			}
		}

	}

	@Override
	public void dispose() {
		if (servidor != null)
			servidor.getServidor().close();

		stage.clear();
	}

}

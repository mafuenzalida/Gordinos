package com.me.gordinos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.gordinos.net.Cliente;
import com.me.gordinos.net.Servidor;

public class Conexion2Screen extends AbstractScreen {
	
	private Cliente cliente;
	private Servidor servidor;

	public Conexion2Screen(Gordinos game, Cliente cliente, Servidor servidor) {
		super(game);
		// TODO Auto-generated constructor stub
		this.cliente = cliente;
		this.servidor = servidor;
	}
	
	@Override
	public void show() {
		TextButton text1 = new TextButton("Esperando que se conecten mas jugadores\n...\nNumero de jugadores ", getSkin());
		text1.setPosition(200,300);
		stage.addActor(text1);
		
		TextButton volverGameButton = new TextButton("Volver", getSkin());
		volverGameButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				//Limpiamos todo y volvemos
				if (servidor != null)
					servidor.getServidor().close();
				cliente.getCliente().close();
				servidor = null ;
				cliente = null;
				game.setScreen(new ConexionScreen(game));
			}
		});
		
		volverGameButton.setPosition(215, 100);
		volverGameButton.setSize(300, 60);
		
		stage.addActor(volverGameButton);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		if (empezar) {
			game.setScreen(new LunchTimeScreen(game, cliente, servidor));
		}
		super.render(delta);
	}

}

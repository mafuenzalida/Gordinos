package com.me.gordinos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.gordinos.net.Cliente;

public class PuntuacionesScreen extends AbstractScreen {

	private int contfinal;
	private int cont;
	private int cont1;
	private int cont2;
	private Cliente cliente;

	public PuntuacionesScreen(Gordinos game, int contfinal, int cont,
			int cont1, int cont2, Cliente cliente) {
		super(game);
		// TODO Auto-generated constructor stub
		this.cont = cont;
		this.cont1 = cont1;
		this.cont2 = cont2;
		this.cliente = cliente;
		this.contfinal = contfinal;
	}

	public void show() {

		TextButton text1 = new TextButton("La respuesta correcta es de "
				+ contfinal, getSkin());
		TextButton text2 = new TextButton("Tu respuesta: " + cont, getSkin());
		TextButton text3 = new TextButton("Respuesta de " + cliente.nombre1
				+ ": " + cont1, getSkin());
		TextButton text4 = new TextButton("Respuesta de " + cliente.nombre2
				+ ": " + cont2, getSkin());

		text1.setPosition(360, 400);
		text2.setPosition(360, 300);
		text3.setPosition(360, 250);
		text4.setPosition(360, 200);

		int ganadores = 0;
		int numganador = 0;
		String stringGanadores = "";

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				if (Math.abs(contfinal) - Math.abs(numganador) > Math.abs(contfinal) - Math.abs(cont)) {
					ganadores++;
					numganador = cont;
					stringGanadores += nombre;
				}
			} else if (i == 1) {
				if (Math.abs(contfinal) - Math.abs(numganador) > Math.abs(contfinal) - Math.abs(cont1)) {
					numganador = cont1;
					stringGanadores = cliente.nombre1;
				} else if (Math.abs(contfinal) - Math.abs(numganador) == Math.abs(contfinal) - Math.abs(cont1)) {
					ganadores++;
					stringGanadores += " y " + cliente.nombre1;
				}
			} else {
				if (Math.abs(contfinal) - Math.abs(numganador) > Math.abs(contfinal) - Math.abs(cont2)) {
					if (ganadores == 2) {
						ganadores--;
					}
					stringGanadores = cliente.nombre2;
				} else if (Math.abs(contfinal) - Math.abs(numganador) == Math.abs(contfinal) - Math.abs(cont)) {
					ganadores++;
					stringGanadores += " y " + cliente.nombre2;
				}
			}
		}

		TextButton text;

		if (ganadores == 3)
			text = new TextButton("Todos los jugadores han ganado.", getSkin());
		else if (ganadores == 1)
			text = new TextButton("Ganador: " + stringGanadores, getSkin());
		else
			text = new TextButton("Ganadores: " + stringGanadores, getSkin());

		text.setPosition(360, 100);
		
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
				game.setScreen(new MenuScreen(game));
			}
		});
		
		volverGameButton.setPosition(215, 20);
		volverGameButton.setSize(300, 60);
		
		stage.addActor(volverGameButton);
		Gdx.input.setInputProcessor(stage);

		stage.addActor(text1);
		stage.addActor(text2);
		stage.addActor(text3);
		stage.addActor(text4);
		stage.addActor(text);
	}

}

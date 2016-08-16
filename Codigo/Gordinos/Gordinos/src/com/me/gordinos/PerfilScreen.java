package com.me.gordinos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PerfilScreen extends AbstractScreen {

	public PerfilScreen(Gordinos game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		//Creamos TODO lo que agregaremos a la stage
		
		
		
		//Personajes
		Texture Text1 = new Texture( "images/personajes/buda.png" );
		final Image Personaje1 = new Image(Text1);
		Texture Text2 = new Texture( "images/personajes/fat_homer.png" );
		final Image Personaje2 = new Image(Text2);
		Texture Text3 = new Texture( "images/personajes/fat_lois.png" );
		final Image Personaje3 = new Image(Text3);
		Texture Text4 = new Texture( "images/personajes/kartman.png" );
		final Image Personaje4 = new Image(Text4);
		Texture Text5 = new Texture( "images/personajes/michelin.png" );
		final Image Personaje5 = new Image(Text5);
		Texture Text6 = new Texture( "images/personajes/panda.png" );
		final Image Personaje6 = new Image(Text6);
		Texture Text7 = new Texture( "images/personajes/petter_grifin.png" );
		final Image Personaje7 = new Image(Text7);
		Texture Text8 = new Texture( "images/personajes/salinas.png" );
		final Image Personaje8 = new Image(Text8);
		Texture Text9 = new Texture( "images/personajes/santa.png" );
		final Image Personaje9 = new Image(Text9);
		Texture Text10 = new Texture( "images/personajes/ursula.png" );
		final Image Personaje10 = new Image(Text10);
		
		
	    //Nombre.
		Gdx.app.log(Gordinos.LOG, nombre);
	   	final TextField name1 = new TextField(nombre, getSkin());
	   	TextButton text = new TextButton("Nombre:", getSkin());
	    
	    //Combobox
	    String[] ListaString = {"Buda","Fat Homer","Fat Lois","Kartman","Michelin","Panda","Petter","Salinas","Santa","Ursula"};
	    final SelectBox combobox = new SelectBox(ListaString, getSkin());

	    //Botones
	    
	    //aceptar
		TextButton aceptar = new TextButton("Aceptar",getSkin());
	    
	    //Listeners 
	    
	    //Botones
	    aceptar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				// IR A LA SCREEN CON LOS DATOS YA TOMADOS
				nombre = name1.getText();
				Gdx.app.log(Gordinos.LOG, name1.getText());
				Gdx.app.log(Gordinos.LOG, nombre);
				imagen = combobox.getSelectionIndex();
				game.setScreen(new MenuScreen(game));
			}
	    	
	    	
	    });
		
	    combobox.addListener(new ClickListener(){
	    	int contaux = 0;

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				//super.clicked(event, x, y);
				
				if(contaux==0){
					int index = combobox.getSelectionIndex();
				    stage.getActors().removeIndex(0);
					switch (index) {
					case 0:
					    stage.getActors().insert(0,  Personaje1);
						break;
					case 1:
						 stage.getActors().insert(0, Personaje2);
						break;
					case 2:
						 stage.getActors().insert(0, Personaje3);
						break;
					case 3:
						 stage.getActors().insert(0, Personaje4);
						break;
					case 4:
						 stage.getActors().insert(0, Personaje5);
						break;
					case 5:
						 stage.getActors().insert(0, Personaje6);
						break;
					case 6:
						 stage.getActors().insert(0, Personaje7);
						break;
					case 7:
						 stage.getActors().insert(0, Personaje8);
						break;
					case 8:
						 stage.getActors().insert(0, Personaje9);
						break;
					case 9:
						 stage.getActors().insert(0, Personaje10);
						break;
					default:
						 stage.getActors().insert(0, Personaje1);
						break;
					}
					contaux=0;
				}
			}
	    	
	    	
	    	
	    });

		//Seteamos los tamaños, posiciones, etc de TODOS los elementos a agregar
	    
	    //Personajes
	    Personaje1.setSize(250, 280);
	    Personaje1.setPosition(420, 100);
	    Personaje2.setSize(250, 280);
	    Personaje2.setPosition(420, 100);
	    Personaje3.setSize(250, 280);
	    Personaje3.setPosition(420, 100);
	    Personaje4.setSize(250, 280);
	    Personaje4.setPosition(420, 100);
	    Personaje5.setSize(250, 280);
	    Personaje5.setPosition(420, 100);
	    Personaje6.setSize(250, 280);
	    Personaje6.setPosition(420, 100);
	    Personaje7.setSize(250, 280);
	    Personaje7.setPosition(420, 100);
	    Personaje8.setSize(250, 280);
	    Personaje8.setPosition(420, 100);
	    Personaje9.setSize(250, 280);
	    Personaje9.setPosition(420, 100);
	    Personaje10.setSize(250, 280);
	    Personaje10.setPosition(420, 100);
	
		//Combobox
		combobox.setSize(250,50);
		combobox.setPosition(420, 400);
		
		//botones
		aceptar.setPosition(150, 70);
		aceptar.setSize(150, 50);

		//Name
		name1.setPosition(200, 250);
		text.setPosition(100,253);
		
		//Agregamos los actores a la stage
		
		//Personajes
		stage.addActor(Personaje1);
	    
	    //Nombres
		stage.addActor(name1);
	    
	    //Botones
		stage.addActor(aceptar);
		
		//Combobox
		stage.addActor(combobox);
		
		stage.addActor(text);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void dispose() {
		stage.clear();
		super.dispose();
	}
	
	

}

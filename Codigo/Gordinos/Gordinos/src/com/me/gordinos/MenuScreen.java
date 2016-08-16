package com.me.gordinos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends AbstractScreen {
	
	private Table table;

	public MenuScreen(Gordinos game) {
		super(game);
	}

	@Override
    public void show ()
    {
    	super.show();

        table = super.getTable();
        table.setPosition((Gdx.graphics.getWidth() - table.getWidth())/2, (Gdx.graphics.getHeight() - table.getHeight())/2);
        table.add( "Bienvenido a Gordinos Game" ).spaceBottom( 50 );
        table.row();

        TextButton perfilGameButton = new TextButton( "Perfil", getSkin() );
        perfilGameButton.addListener( new InputListener() {
        	@Override
            public boolean touchDown(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                return true;
                
            }
        	
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                
                game.setScreen( new PerfilScreen( game ) );
                
            }
        } );
        table.add( perfilGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        TextButton conectarButton = new TextButton( "Conectar", getSkin() );
        conectarButton.addListener( new InputListener() {
        	@Override
            public boolean touchDown(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                return true;
                
            }
        	
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                game.setScreen( new ConexionScreen( game ) );
            }
        } );
        table.add( conectarButton ).uniform().fill().spaceBottom( 10 );
        table.row();

        
        
        stage.addActor( table );
    }

}

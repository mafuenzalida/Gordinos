package com.me.gordinos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SplashScreen extends AbstractScreen {
	
	private Texture splashTexture;

	public SplashScreen(Gordinos game) {
		super(game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		// load the texture with the splash image
        splashTexture = new Texture( "images/personajes/petter_grifin.png" );

        // set the linear texture filter to improve the image stretching
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
        
        // let's make sure the stage is clear
        stage.clear();

        // in the image atlas, our splash image begins at (0,0) of the
        // upper-left corner and has a dimension of 512x301
        TextureRegion splashRegion = new TextureRegion( splashTexture, 0, 0, 256, 256 );

        // here we create the splash image actor and set its size
        Image splashImage = new Image( splashRegion);
        splashImage.setWidth(256);
        splashImage.setHeight(256);
        splashImage.setPosition((Gdx.graphics.getWidth() - splashImage.getWidth())/2, (Gdx.graphics.getHeight() - splashImage.getHeight())/2);

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.getColor().a = 0f;

        splashImage.addAction( Actions.sequence( Actions.fadeIn(0.75f), Actions.delay(1.75f), Actions.fadeOut( 0.75f ),
                new Action() {
                    @Override
                    public boolean act(
                        float delta )
                    {
                        // the last action will move to the next screen
                        game.setScreen( new MenuScreen( game ) );
                        return true;
                    }
                } ) );

        // and finally we add the actors to the stage, on the correct order
        stage.addActor( splashImage );
		
	}
	
	@Override
	public void hide() {
		super.hide();
		dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		splashTexture.dispose();
	}
	
	

}

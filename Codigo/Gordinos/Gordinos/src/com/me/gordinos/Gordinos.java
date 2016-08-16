package com.me.gordinos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Gordinos extends Game {
	// constant useful for logging
    public static final String LOG = Gordinos.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEV_MODE = true;

    public Gordinos()
    {
    }

    // Game-related methods

    @Override
    public void create()
    {
        Gdx.app.log( Gordinos.LOG, "Creating game on " + Gdx.app.getType() );
        
        setScreen( new SplashScreen( this ) );
    }

    @Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );
        Gdx.app.log( Gordinos.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
            if( DEV_MODE ) {
                //setScreen( new LevelScreen( this, 0 ) );
            } else {
                setScreen( new SplashScreen( this ) );
            }
        }
    }

    @Override
    public void render()
    {
        super.render();

        // output the current FPS
        //if( DEV_MODE ) fpsLogger.log();
    }

    @Override
    public void pause()
    {
        super.pause();
        Gdx.app.log( Gordinos.LOG, "Pausing game" );
    }

    @Override
    public void resume()
    {
        super.resume();
        Gdx.app.log( Gordinos.LOG, "Resuming game" );
    }

    @Override
    public void setScreen(
        Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( Gordinos.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

    @Override
    public void dispose()
    {
        super.dispose();
        Gdx.app.log( Gordinos.LOG, "Disposing game" );
    }
}

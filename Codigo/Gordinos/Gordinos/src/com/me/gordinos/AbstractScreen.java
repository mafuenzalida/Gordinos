package com.me.gordinos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen implements Screen {
	// the fixed viewport dimensions (ratio: 1.6)
	public static final int GAME_VIEWPORT_WIDTH = 720,
			GAME_VIEWPORT_HEIGHT = 480;
	public static final int MENU_VIEWPORT_WIDTH = 720,
			MENU_VIEWPORT_HEIGHT = 480;

	protected final Gordinos game;
	protected final Stage stage;
	protected static String nombre = "";
	protected static int imagen = 0;
	protected static boolean empezar = false;

	protected final String[] personajes = { "images/personajes/buda.png",
			"images/personajes/fat_homer.png",
			"images/personajes/fat_lois.png", "images/personajes/kartman.png",
			"images/personajes/michelin.png", "images/personajes/panda.png",
			"images/personajes/petter_grifin.png",
			"images/personajes/salinas.png", "images/personajes/santa.png",
			"images/personajes/ursula.png" };

	protected final String[] comidas = { "images/fat_food/burger.png",
			"images/fat_food/donuts.png", "images/fat_food/fries.png",
			"images/fat_food/nuggets.png", "images/fat_food/pizza.png",
			"images/fat_food/pizza2.png", "images/fat_food/ribs.png",
			"images/fit_food/brocoli.png", "images/fit_food/frutilla.png",
			"images/fit_food/lechuga.png", "images/fit_food/manzana.png",
			"images/fit_food/naranja.png", "images/fit_food/platano.png" };

	private BitmapFont font;
	private SpriteBatch batch;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;

	public AbstractScreen(Gordinos game) {
		this.game = game;
		int width = (isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH);
		int height = (isGameScreen() ? GAME_VIEWPORT_HEIGHT
				: MENU_VIEWPORT_HEIGHT);
		this.stage = new Stage(width, height, true);
	}

	protected String getName() {
		return getClass().getSimpleName();
	}

	protected boolean isGameScreen() {
		return false;
	}

	protected Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}

	public Table getTable() {
		if (table == null)
			table = new Table(getSkin());
		return table;
	}

	// Screen implementation

	@Override
	public void show() {
		Gdx.app.log(Gordinos.LOG, "Showing screen: " + getName());

		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(Gordinos.LOG, "Resizing screen: " + getName() + " to: "
				+ width + " x " + height);
	}

	@Override
	public void render(float delta) {
		// (1) process the game logic

		// update the actors
		stage.act(delta);

		// (2) draw the result

		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw the actors
		stage.draw();
	}

	@Override
	public void hide() {
		Gdx.app.log(Gordinos.LOG, "Hiding screen: " + getName());

		// remember to dispose the screen when leaving the screen;
		// note that the dipose() method is not called automatically by the
		// framework, so we must figure out when it's appropriate to call it
	}

	@Override
	public void pause() {
		Gdx.app.log(Gordinos.LOG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		Gdx.app.log(Gordinos.LOG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		Gdx.app.log(Gordinos.LOG, "Disposing screen: " + getName());

		// as the collaborators are lazily loaded, they may be null

		// the following call disposes the screen's stage but on my computer it
		// crashes the game, so I've commented it out; more info can be found
		// at: http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
		// stage.dispose();

		if (font != null)
			font.dispose();
		if (batch != null)
			batch.dispose();
		if (skin != null)
			skin.dispose();
		if (atlas != null)
			atlas.dispose();
	}

}

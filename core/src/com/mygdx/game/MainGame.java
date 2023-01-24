package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.screens.GameScreen;

public class MainGame extends Game {
	private GameScreen gameScreen;

	public AssetMan assetManager;

	//SE CREA LA PANTALLA DEL JUEGO Y SE LE ESTABLECE
	@Override
	public void create() {
		this.assetManager = new AssetMan();
		//se inicializa la pantalla del juego
		this.gameScreen = new GameScreen(this);
		//Se establece la pantalla
		setScreen(this.gameScreen);
	}
}

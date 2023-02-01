package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.BG_SOUND;
import static com.mygdx.game.extra.Utils.BIRD1;
import static com.mygdx.game.extra.Utils.BIRD2;
import static com.mygdx.game.extra.Utils.BIRD3;
import static com.mygdx.game.extra.Utils.BIRD4;
import static com.mygdx.game.extra.Utils.JUMP_SOUND;
import static com.mygdx.game.extra.Utils.PIPE_DOWN;
import static com.mygdx.game.extra.Utils.PIPE_UP;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//Como su nombre indica es el manager de los assets
public class AssetMan {

    private AssetManager assetManager;
    //carga las imagenes del atlas
    private TextureAtlas textureAtlas;

    public AssetMan(){
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.load(JUMP_SOUND, Sound.class);
        assetManager.load(BG_SOUND, Music.class);
        assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }
    //IMAGEN DE FONDO
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    //ANIMACIÓN ACTOR
    public Animation<TextureRegion> getBirdAnimation(){
        //Devuelve por asi decirlo los frames que van a formar la animacion del actor
        return new Animation<TextureRegion>(0.25f,
                //obtiene la region de en donde esta la imagen que  busco
                //para 'enmarcarla' o enfocarla y obtenerla
                textureAtlas.findRegion(BIRD1),
                textureAtlas.findRegion(BIRD2),
                textureAtlas.findRegion(BIRD3),
                textureAtlas.findRegion(BIRD4));
    }

    //Textura de las tuberías
    public TextureRegion getPipeDownTR(){
        return this.textureAtlas.findRegion(PIPE_DOWN);
    }

    public TextureRegion getPipeTopTR(){
        return this.textureAtlas.findRegion(PIPE_UP);
    }

    public Sound getJumpSound(){
        return this.assetManager.get(JUMP_SOUND);
    }

    public Music getMusicBG(){
        return this.assetManager.get(BG_SOUND);
    }



}















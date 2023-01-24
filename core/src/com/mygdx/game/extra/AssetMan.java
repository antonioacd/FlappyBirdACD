package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.GOKU1;
import static com.mygdx.game.extra.Utils.GOKU2;
import static com.mygdx.game.extra.Utils.GOKU3;

import com.badlogic.gdx.assets.AssetManager;
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
        //carga el atlas
        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        //Bloquea hasta que todos los assets estan cargados
        assetManager.finishLoading();
        //Se le pasa el mapa de atlas procesado por el assetManager y se guarda en el textureAtlas
        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }
    //IMAGEN DE FONDO
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    //ANIMACIÓN ACTOR
    public Animation<TextureRegion> getBirdAnimation(){
        //Devuelve por asi decirlo los frames que van a formar la animacion del actor
        return new Animation<TextureRegion>(0.33f,
                //obtiene la region de en donde esta la imagen que  busco
                //para 'enmarcarla' o enfocarla y obtenerla
                textureAtlas.findRegion(GOKU1),
                textureAtlas.findRegion(GOKU2),
                textureAtlas.findRegion(GOKU3));

    }
}















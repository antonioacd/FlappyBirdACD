package com.mygdx.game.actors;


import static com.mygdx.game.extra.Utils.USER_GOKU;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Goku extends Actor {
    private Animation<TextureRegion> gokuAnimation;
    private Vector2 position;

    private float stateTime;
    //La clase 'mundo' gestiona todas las entidades como son las físicas, la simulación dinámica
    // y las consultas asincrónicas. El mundo también contiene instalaciones de gestión de memoria eficientes.
    private World world;
    //el cuerpo
    private Body body;
    //es para darle una forma al cuerpo
    private Fixture fixture;
    //Contructor
    public Goku(World world, Animation<TextureRegion> animation, Vector2 position){
        //se inicializa a la animacion pasada por parametro
        this.gokuAnimation = animation;
        //e igual con la posicion
        this.position = position;
        //y el mundo
        this.world = world;

        this.stateTime =0f;
        //creamos el cuerpo
        createBody();
        //y creamos la 'forma' del cuerpo
        createFixture();
    }

    //creacion del cuerpo
    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(this.position);

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodyDef);
    }
    //creacion de la forma del cuerpo
    private void createFixture(){
        //forma circular
        CircleShape circle = new CircleShape();
        //establecemos el radio del circulo que va a ser la hitbox de nuestro actor
        circle.setRadius(0.30f);
        //creamos la forma y la guardamos
        this.fixture = this.body.createFixture(circle, 8);
        //se le establece la forma al actor
        this.fixture.setUserData(USER_GOKU);
        //se coloca el circulo
        circle.dispose();
    }

    @Override
    public void act(float delta) {

    }

    //para 'dibujar'
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //se le establece la poscion
        setPosition(body.getPosition().x-0.4f, body.getPosition().y-0.25f);
        batch.draw(this.gokuAnimation.getKeyFrame(stateTime, true), getX(), getY(), 0.8f,0.5f );
        //delta es el lapso de tiempo entre el cuadro actual y el último cuadro en segundos.
        stateTime += Gdx.graphics.getDeltaTime();
    }
    //para eliminar
    public void detach(){
        //destruye la forma
        this.body.destroyFixture(this.fixture);
        //y el cuerpo
        this.world.destroyBody(this.body);
    }
}















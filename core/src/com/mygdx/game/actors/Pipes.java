package com.mygdx.game.actors;



import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extra.Utils;

public class Pipes extends Actor {

    //Todo 5. Para crear las tuberías debemos fijar un ancho y alto
    private static final float PIPE_WIDTH = 1f;
    private static final float PIPE_HEIGHT = 4f;
    private static final float COUNTER_HEIGHT = 2f;
    private static final float SPEED = -0.2f;

    //Todo 6. Creamos Texturas, Body, fixture y mundo
    private TextureRegion pipeDownTR;
    private TextureRegion pipeTopTR;

    private Body bodyDown;
    private Body bodyTop;
    private Body bodyCounter;

    private Fixture fixtureDown;
    private Fixture fixtureTop;
    private Fixture fixtureCounter;

    private World world;

    //Todo 7 Constructor con mundo textura y posicion
    public Pipes(World world, TextureRegion trpDown, TextureRegion trpTop, Vector2 position) {
        this.world = world;
        this.pipeDownTR = trpDown;
        this.pipeTopTR = trpTop;

        createBodyPipeDown(position);
        createBodyPipeTop();
        createCounter();
        createFixture();
    }


    //Todo 8. creamos metodo para body con parametro
    private void createBodyPipeDown(Vector2 position) {
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        bodyDown = world.createBody(def);
        bodyDown.setUserData(Utils.USER_PIPE_DOWN);
        bodyDown.setLinearVelocity(SPEED,0);

    }

    private void createBodyPipeTop() {
        BodyDef def = new BodyDef();
        def.position.x = bodyDown.getPosition().x;
        def.position.y = bodyDown.getPosition().y + PIPE_HEIGHT + COUNTER_HEIGHT;

        def.type = BodyDef.BodyType.KinematicBody;
        bodyTop = world.createBody(def);
        bodyTop.setUserData(Utils.USER_PIPE_UP);
        bodyTop.setLinearVelocity(SPEED,0);

    }

    //Todo 9 Creamos método para la fixture
    private void createFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PIPE_WIDTH/2, PIPE_HEIGHT/2);

        this.fixtureDown = bodyDown.createFixture(shape, 8);
        this.fixtureTop = bodyTop.createFixture(shape, 8);

        shape.dispose();
    }

    public void createCounter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = this.bodyDown.getPosition().x;
        bodyDef.position.y = (this.bodyDown.getPosition().y + this.bodyTop.getPosition().y) /2f ;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        this.bodyCounter = this.world.createBody(bodyDef);
        this.bodyCounter.setLinearVelocity(SPEED, 0);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.1f,0.90f);

        this.fixtureCounter = bodyCounter.createFixture(polygonShape, 3);
        this.fixtureCounter.setSensor(true);
        this.fixtureCounter.setUserData(Utils.USER_COUNTER);
        polygonShape.dispose();
    }

    //Todo 10. Sobrecargamos métodos act y draw
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.bodyDown.getPosition().x - (PIPE_WIDTH/2), this.bodyDown.getPosition().y - (PIPE_HEIGHT/2) );
        batch.draw(this.pipeDownTR, getX(),getY(),PIPE_WIDTH,PIPE_HEIGHT);

        setPosition(this.bodyTop.getPosition().x - (PIPE_WIDTH/2), this.bodyTop.getPosition().y - (PIPE_HEIGHT/2) );
        batch.draw(this.pipeTopTR, getX(),getY(),PIPE_WIDTH,PIPE_HEIGHT);
    }

    //Todo 11. Creamos detach para liberar recursos
    public void detach(){
        bodyDown.destroyFixture(fixtureDown);
        world.destroyBody(bodyDown);

        this.bodyTop.destroyFixture(fixtureTop);
        this.world.destroyBody(this.bodyTop);
    }
}

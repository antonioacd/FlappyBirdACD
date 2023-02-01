package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.USER_BIRD;
import static com.mygdx.game.extra.Utils.USER_FLOOR;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Bird;
import com.mygdx.game.actors.Pipes;

//PANTALLA DEL JUEGO
public class GameScreen  extends BaseScreen{
    //Escenario
    private Stage stage;
    //Actor
    private Bird bird;
    //Fondo
    private Image background;
    //Mundo
    private World world;

    Pipes pipes;

    private Music bgMusic;

    //Depuracion
    private Box2DDebugRenderer debugRenderer;
    //camara ortografica
    private OrthographicCamera ortCamera;

    //Pantalla del juego
    public GameScreen(MainGame mainGame) {
        //constructor juego principal
        super(mainGame);
        //inicializacion del mundo
        this.world = new World(new Vector2(0,-10), true);
        //siempre mantiene la relación de aspecto del tamaño de la pantalla virtual
        //(ventana virtual), al tiempo que la escala tanto como sea posible para
        //que se ajuste a la pantalla.
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        //Se le pone al stage
        this.stage = new Stage(fitViewport);
        //Ponemos la musica de fondo
        this.bgMusic = this.mainGame.assetManager.getMusicBG();
        //Se inicializa la camara ortografica
        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        //Se inicializa el renderizado
        this.debugRenderer = new Box2DDebugRenderer();
    }

    //Para añadir o actualizar el fondo
    public void addBackground(){
        //Se le pone la imagen al fondo
        this.background = new Image(mainGame.assetManager.getBackground());
        //Se posiciona el fondo en la posicion x:0 y:0
        this.background.setPosition(0,0);
        //Se le pone al fondo el tamaño del mundo
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        //El escenario o escena añade al actor en el fondo
        this.stage.addActor(this.background);
    }

    //Se añade el actor
    public void addActor() {
        //creamos una animacion (sprite)
        //y la inicializamos al conjunto de assets del actor
        Animation<TextureRegion> birdSprite = mainGame.assetManager.getBirdAnimation();
        Sound sound = mainGame.assetManager.getJumpSound();
        //Inicializo al actor creandolo con el constructor de su clase
        this.bird = new Bird(this.world, birdSprite, sound, new Vector2(1f, 4f));
        //añado el actor a la escena
        this.stage.addActor(this.bird);
    }

    private void addFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(WORLD_WIDTH / 2f, -0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        body.setUserData(USER_FLOOR);

        PolygonShape edge = new PolygonShape();
        edge.setAsBox(2.3f, 0.5f);
        body.createFixture(edge, 3);
        edge.dispose();
    }

    public void addRoof(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        EdgeShape edge = new EdgeShape();
        edge.set(0,WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        body.createFixture(edge, 1);
        edge.dispose();
    }

    //aqui va el renderizado
    @Override
    public void render(float delta) {
        ////////////////////////////////////////////////////////////////////////////////////////////////
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        //Da un paso de tiempo. Esto realiza detección de colisiones,
        //integración y solución de restricciones.
        this.world.step(delta,6,2);
        //dibuja la escena
        this.stage.draw();
        //Esto supone que la matriz de proyección ya se ha establecido.
        this.debugRenderer.render(this.world, this.ortCamera.combined);
    }


    //para mostrar
    @Override
    public void show() {
        addBackground();
        addFloor();
        addRoof();
        addActor();

        TextureRegion pipeTRDown = mainGame.assetManager.getPipeDownTR();
        TextureRegion pipeTRDTop = mainGame.assetManager.getPipeTopTR();
        this.pipes = new Pipes(this.world, pipeTRDown, pipeTRDTop,new Vector2(3.75f,2f));
        this.stage.addActor(this.pipes);

        this.bgMusic.setLooping(true);
        this.bgMusic.play();

    }

    //para ocultar
    @Override
    public void hide() {
        this.bird.detach();
        this.bird.remove();

        this.pipes.detach();
        this.pipes.remove();

        this.bgMusic.stop();
    }

    //para colocar
    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();

    }
}

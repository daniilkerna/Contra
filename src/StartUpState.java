import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This state is active prior to the Game starting. In this state, sound is
 * turned off, and the bounce counter shows '?'. The user can only interact with
 * the game by pressing the SPACE key which transitions to the Playing State.
 * Otherwise, all game objects are rendered and updated normally.
 * 
 * Transitions From (Initialization), GameOverState
 * 
 * Transitions To PlayingState
 */
class StartUpState extends BasicGameState {

	String message = "Press Space";
	boolean messageOn = true;
	int printCooldown = 750;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		ContraGame bg = (ContraGame)game;

		//g.drawImage(ResourceManager.getImage(ContraGame.STARTUP_BANNER_RSC), bg.ScreenWidth/2 - 150, bg.ScreenHeight/2 + 150);
		ResourceManager.getImage(ContraGame.Contra_Banner_RSC).draw( 100, 50);
		g.drawString(message, container.getWidth()  /2 - 50 , 400);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		ContraGame bg = (ContraGame)game;

		if (input.isKeyPressed(Input.KEY_SPACE))
			bg.enterState(ContraGame.PLAYINGSTATE);
		

		printCooldown -= delta;
		if( printCooldown < 0 ){
			printCooldown = 750;
			if(messageOn){
				message = "";
				messageOn = !messageOn;
			}
			else{
				message = "Press Space";
				messageOn = !messageOn;
			}
		}

	}

	@Override
	public int getID() {
		return ContraGame.STARTUPSTATE;
	}
	
}
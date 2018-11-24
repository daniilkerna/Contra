import java.util.Random;

import jig.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 */

class PlayingState extends BasicGameState {
	Player player1;


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		ContraGame cg = (ContraGame) game;

		player1 = new Player( container, game );
	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		ContraGame tg = (ContraGame)game;

		player1.render(g);

		//SpriteSheet ss = new SpriteSheet( ResourceManager.getImage( ContraGame.PLAYER_RUN_RIGHT_RSC ).getFlippedCopy( true, false ), 37, 45 );
		//ss.getSprite( 3, 0).draw( 100, 100 );
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		ContraGame tg = (ContraGame)game;

		player1.update(container, game, delta );
	}

	@Override
	public int getID() {
		return ContraGame.PLAYINGSTATE;
	}

	//return random int [0,maximum]
	public int getRandomInt(int maximum){
		Random random = new Random();
		int number = random.nextInt(maximum + 1);

		return number;
	}


	
}
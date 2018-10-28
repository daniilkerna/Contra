import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

import jig.Collision;
import jig.Entity;
import jig.Vector;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 */

class PlayingState extends BasicGameState {


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		ContraGame tg = (ContraGame) game;

	}
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		ContraGame tg = (ContraGame)game;


	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		ContraGame tg = (ContraGame)game;


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
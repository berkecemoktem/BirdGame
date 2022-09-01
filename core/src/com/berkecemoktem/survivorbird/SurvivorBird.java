package com.berkecemoktem.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy;
	Texture enemy2;
	Texture enemy3;
	Random random;
	BitmapFont font;
	BitmapFont font2;
	int score = 0;
	int scoredEnemy = 0;
	float birdX = 0f;
	float birdY = 0f;
	//boolean isGameStarted = false;
	//boolean isGameOver = false;
	int gameState = 0;
	float velocity = 0f;
	float gravity = 0.3f;
	float enemyVelocity = 9f;

	Circle birdCircle;


	int numberOfEnemySet = 4;
	float[] enemyX = new float[numberOfEnemySet];
	float[] enemyY1 = new float[numberOfEnemySet];
	float[] enemyY2 = new float[numberOfEnemySet];
	float[] enemyY3 = new float[numberOfEnemySet];

	float distanceBetweenSets = 0;

	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	//ShapeRenderer shapeRenderer;

	@Override
	public void create () { //like "on create".
		batch = new SpriteBatch();
		background = new Texture("background.png");
		enemy = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");
		random = new Random();
		bird = new Texture("bird.png");

		birdX = Gdx.graphics.getWidth() / 4;
		birdY = Gdx.graphics.getHeight()/2;

		birdCircle = new Circle();
		enemyCircles1 = new Circle[numberOfEnemySet];
		enemyCircles2 = new Circle[numberOfEnemySet];
		enemyCircles3 = new Circle[numberOfEnemySet];
		//shapeRenderer = new ShapeRenderer();

		distanceBetweenSets = Gdx.graphics.getWidth()/2;
		for(int i = 0 ; i < numberOfEnemySet ; i++){
			enemyY1[i] = random.nextFloat() * (Gdx.graphics.getHeight());
			enemyY2[i] = random.nextFloat() * (Gdx.graphics.getHeight());
			enemyY3 [i] = random.nextFloat() * (Gdx.graphics.getHeight());

			enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 2 + i * distanceBetweenSets;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

		font = new BitmapFont();
		font.setColor(Color.GREEN);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.GREEN);
		font2.getData().setScale(6);
	}

	@Override
	public void render () { //most of the codes will be here. (Motions, actions, interactions etc)
		batch.begin();

		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1){

			if(Gdx.input.justTouched()){ //move up
				velocity = -8;
			}
			//moving enemies to the left.(towards to our character)
			for(int i = 0 ; i < numberOfEnemySet ; i++) {
				if (enemyX[i] < 0) { //if the current enemy set's x is zero.
					enemyX[i] = enemyX[i] + numberOfEnemySet * distanceBetweenSets;

					enemyY1[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
					enemyY2[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
					enemyY3[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}
				//drawing the enemies
				if (!(Gdx.graphics.getHeight() / 2 + enemyY1[i] > Gdx.graphics.getHeight())){
					batch.draw(enemy, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyY1[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
					enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyY1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				}
				else {
					enemyY1[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
				}
				if(!(Gdx.graphics.getHeight()/2 + enemyY2[i] > Gdx.graphics.getHeight())){
					batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2+ enemyY2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
					enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyY2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth()/30);
				}
				else{
					enemyY2[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
				}
				if (!(Gdx.graphics.getHeight()/2 + enemyY3[i] > Gdx.graphics.getHeight())){
					batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight() / 2+ enemyY3[i] ,Gdx.graphics.getWidth() / 15 , Gdx.graphics.getHeight() / 10);
					enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyY3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth()/30);
				}
				else{
					enemyY3[i] = (random.nextFloat() - 0.8f) * (Gdx.graphics.getHeight() - 200);
				}
			}

			//if the bird touches the floor.
			if(birdY > 0 ){ //gravity, move down
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else{
				gameState = 2;
			}
		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}
		else if(gameState == 2){ //game is over.
			font2.draw(batch,"Game Over! Tap to play again.", 300, Gdx.graphics.getHeight()/2);
			birdY = Gdx.graphics.getHeight()/2; //re-locate the bird's y axis as the beginning of the game.
			if(Gdx.input.justTouched()){//game is over and user clicked the screen again. So, restart the game.
				gameState = 1;
				for(int i = 0 ; i < numberOfEnemySet ; i++){
					enemyY1[i] = random.nextFloat() * (Gdx.graphics.getHeight());
					enemyY2[i] = random.nextFloat() * (Gdx.graphics.getHeight());
					enemyY3 [i] = random.nextFloat() * (Gdx.graphics.getHeight());

					enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 2 + i * distanceBetweenSets;

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}
				velocity = 0;
				score = 0;
				scoredEnemy = 0;
			}
		}

		//score algorithm.
		if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2){
			score ++;
			if(scoredEnemy < numberOfEnemySet -1){
				scoredEnemy++;
			}else{
				scoredEnemy = 0;
			}
		}


		batch.draw(bird,birdX ,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200); //displaying the score
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y,birdCircle.radius);

		for (int i = 0 ; i < numberOfEnemySet ; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyY1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyY2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyY3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			//birds intersects with enemy.
			if(Intersector.overlaps(birdCircle,enemyCircles1[i]) ||Intersector.overlaps(birdCircle,enemyCircles2[i]) || (Intersector.overlaps(birdCircle,enemyCircles3[i]))){
				System.out.println("collision!");
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}

	@Override
	public void dispose () {

	}
}

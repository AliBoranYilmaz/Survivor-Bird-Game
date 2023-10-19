package com.aby.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX;
	float birdY;
	Random random;
	int score = 0;
	int scoredEnemy = 0;

	BitmapFont font;
	BitmapFont font2;

	Circle birdCircle;
	ShapeRenderer shapeRenderer;

	int gameState = 0; // game is not started
	float velocity = 0;
	float gravity = 0.3f;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffSet = new float[numberOfEnemies];
	float[] enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3 = new float[numberOfEnemies];
	float distance;
	float enemyVelocity = 7f;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	Music music;
	Music huzun;

	@Override
	public void create () // this runs when app starts
	{
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		music = Gdx.audio.newMusic(Gdx.files.internal("intikam_yemini.mp3"));
		music.setLooping(true);

		huzun = Gdx.audio.newMusic(Gdx.files.internal("huzun.mp3"));
		huzun.setLooping(true);

		birdX = Gdx.graphics.getWidth() / 4f;
		birdY = Gdx.graphics.getHeight() / 3f;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.BROWN);
		font2.getData().setScale(8);

		distance = Gdx.graphics.getWidth() / 2.5f;
		random = new Random();

		for (int i=0; i<numberOfEnemies; i++)
		{
			// random.nextFloat() returns a floating number between 0 and 1.
			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			if ((enemyOffSet[i] > enemyOffSet2[i]) && ((enemyOffSet[i] - enemyOffSet2[i])) < Gdx.graphics.getHeight()/11)
			{
				enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			}

			if ((enemyOffSet2[i] > enemyOffSet3[i]) && ((enemyOffSet2[i] - enemyOffSet3[i])) < Gdx.graphics.getHeight()/11)
			{
				enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			}

			if ((enemyOffSet[i] > enemyOffSet3[i]) && ((enemyOffSet[i] - enemyOffSet3[i])) < Gdx.graphics.getHeight()/11)
			{
				enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			}

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2f + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}
	}

	@Override
	public void render ()
	{
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1)
		{
			music.play();

			if (enemyX[scoredEnemy] < birdX)
			{
				score++;

				if (scoredEnemy < numberOfEnemies - 1)
				{
					scoredEnemy++;
				}

				else
				{
					scoredEnemy = 0;
				}
			}

			if (Gdx.input.justTouched())
			{
				velocity = -8;
			}

			for (int i=0; i<numberOfEnemies; i++)
			{
				if (enemyX[i] < 0)
				{
					enemyX[i] += numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					if ((enemyOffSet[i] > enemyOffSet2[i]) && ((enemyOffSet[i] - enemyOffSet2[i])) < Gdx.graphics.getHeight()/11)
					{
						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					}

					if ((enemyOffSet2[i] > enemyOffSet3[i]) && ((enemyOffSet2[i] - enemyOffSet3[i])) < Gdx.graphics.getHeight()/11)
					{
						enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					}

					if ((enemyOffSet[i] > enemyOffSet3[i]) && ((enemyOffSet[i] - enemyOffSet3[i])) < Gdx.graphics.getHeight()/11)
					{
						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					}
				}

				else
				{
					enemyX[i] -= enemyVelocity;
				}

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2f + enemyOffSet[i],Gdx.graphics.getWidth() / 15f,Gdx.graphics.getHeight() / 10f);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2f + enemyOffSet2[i],Gdx.graphics.getWidth() / 15f,Gdx.graphics.getHeight() / 10f);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2f + enemyOffSet3[i],Gdx.graphics.getWidth() / 15f,Gdx.graphics.getHeight() / 10f);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 30f);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 30f);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 30f);
			}

			if (birdY > 0 && birdY < (Gdx.graphics.getHeight() / 1.09))
			{
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}

			else
			{
				gameState = 2;
			}
		}

		if (gameState == 0)
		{
			if (Gdx.input.justTouched())
			{
				gameState = 1;
			}
		}

		if (gameState == 2)
		{
			huzun.play();

			font2.draw(batch, "GAME OVER! TAP TO PLAY AGAIN", 100, Gdx.graphics.getHeight() / 2f + 100);

			music.stop();

			if (Gdx.input.justTouched())
			{
				gameState = 1;
				huzun.stop();
				birdY = Gdx.graphics.getHeight() / 3f;

				for (int i=0; i<numberOfEnemies; i++)
				{ // random.nextFloat() returns a floating number between 0 and 1.
					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2f + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;
			}
		}

		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 18f, Gdx.graphics.getHeight() / 12f);

		font.draw(batch, String.valueOf(score), 100, 200);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 36f, birdY + Gdx.graphics.getHeight() / 24f, Gdx.graphics.getWidth() / 42f);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		//shapeRenderer.setColor(Color.BROWN);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i=0; i<numberOfEnemies; i++)
		{
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 42f);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 42f);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30f,  Gdx.graphics.getHeight()/2f + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 42f);

			if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i]))
			{
				gameState = 2; // game over
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose ()
	{
		music.dispose();
	}
}

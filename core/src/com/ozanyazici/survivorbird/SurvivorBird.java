package com.ozanyazici.survivorbird;

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
	//SpriteBatch, genellikle oyun geliştirme veya grafik programlamada 2D görselleri
	//verimli bir şekilde çizmek için kullanılan bir bileşendir.
	Texture background;
	//Texture'lar, SpriteBatch gibi araçlarla kullanılarak çoklu sprite veya nesneleri aynı anda çizmek için kullanılabilir.
	// Bu, oyunun daha verimli bir şekilde çalışmasına yardımcı olabilir.
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0; //Hız;
	float gravity = 5f;
	float enemyVelocity = 2;
    Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

    Circle birdCircle;

	//ShapeRenderer shapeRenderer;



	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
    float [] enemyOffset = new float[numberOfEnemies];
    float [] enemyOffset2 = new float[numberOfEnemies];
    float [] enemyOffset3 = new float[numberOfEnemies];
	float distance = 0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() / 2;
        random = new Random();


		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		//shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.GREEN);
		font2.getData().setScale(6);



		for (int i = 0; i<numberOfEnemies; i++) {

            enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();



		}

	}

	@Override
	public void render () {

		batch.begin();
		//Cihazın ekranının genişliğive yüksekliğini texture'a vererek dinamik bir yapı oluşturuyorum.
		//Textureların çizimini burda yapıyoruz.
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
				score++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}




			if (Gdx.input.justTouched()) {

				velocity = -40;

			}

			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth() / 17) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

                    enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);



				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset[i], Gdx.graphics.getWidth() / 17, Gdx.graphics.getHeight() / 8);
				batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset2[i], Gdx.graphics.getWidth() / 17, Gdx.graphics.getHeight() / 8);
				batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset3[i], Gdx.graphics.getWidth() / 17, Gdx.graphics.getHeight() / 8);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);




			}



			//Kuşun yere düşünce durmasını sağlayan kontrol.
			if (birdY > 0 || velocity < 0) {
				//Yerçekimi
				velocity = gravity + velocity;
				birdY = birdY - velocity;
			} else {
				gameState = 2;
			}

		} else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",500,Gdx.graphics.getHeight() / 2 + 150);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

				for (int i = 0; i<numberOfEnemies; i++) {

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity  = 0;
				scoredEnemy = 0;
				score = 0;
			}
		}


		batch.draw(bird,birdX, birdY , Gdx.graphics.getWidth() / 17, Gdx.graphics.getHeight() / 8);

		font.draw(batch,String.valueOf(score), 100, 200);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 34,birdY + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.GREEN);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		//shapeRenderer.end();

		for ( int i = 0; i<numberOfEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 16,Gdx.graphics.getWidth() / 34);

			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState=2;
			}
		}
		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}

package net.gondr.tetris1;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import net.gondr.domain.Block;
import net.gondr.domain.Player;

public class Game {
	private GraphicsContext gc;
	public Block[][] board;  //게임판 2차원 배열
	
	private double width;  //게임판의 너비와 높이
	private double height;
	
	private AnimationTimer mainLoop; //게임 루프
	private long before; //이전 프레임의 시간
	
	private Player player;
	private double blockDownTime = 0;
	
	public int score = 0;
	
	public Game(Canvas canvas, Label label) {
		width = canvas.getWidth();
		height = canvas.getHeight();
		
		double size = (width - 4) / 10;
		
		board = new Block[20][10];
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				board[i][j] = new Block(j * size + 2, i * size + 2, size);
			}
		}
		this.gc = canvas.getGraphicsContext2D();
		
		mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(score <= 200) {
					update( (now - before) / 1000000000d);
				}else if(score <= 400) {
					update( (now - before) / 550000000d);
				}else if(score <= 600) {
					update( (now - before) / 500000000d);
				}else {
					update( (now - before) / 280000000d);
				}
				before = now;
				render();
				label.setText("Score : " + Integer.toString(score) + "점");
			}
		};
		before = System.nanoTime();
		player = new Player(board);
		mainLoop.start();
	}
	
	//매초 실행되는 루프 매서드 
	public void update(double delta) {
		blockDownTime += delta;
		if(blockDownTime >= 0.5) {
			player.down();
			blockDownTime = 0;
		}
	}
	
	//한 줄이 가득 찼는지를 검사하는 매서드
	public void checkLineStatus() {
		for(int i = 19; i >= 0; i--) {
			boolean clear = true; // 해당 줄이 꽉 찼다고 가정하고
			for(int j = 0; j < 10; j++) {
				if(!board[i][j].getFill()) {
					clear = false;
					break;
				}
			}
			
			//만약 라인이 꽉 찼다면 수행해야 하는 부분입니다.
			if(clear) {
				score += 100;
				for(int j = 0; j < 10; j++) {
					board[i][j].setData(false, Color.WHITE);
				}
				
				for(int k = i - 1; k >= 0; k--) {
					for(int j = 0; j < 10; j++) {
						board[k + 1][j].copyData(board[k][j]);
					}
				}
				
				for(int j = 0; j < 10; j++) {
					board[0][j].setData(false, Color.WHITE);
				}
				
				//중요한 것!
				i++;
				System.out.println(score + "점 획득");
			}
		}
	}
	
	//매 프레임마다 화면을 그려주는 매서드
	public void render() {
		gc.clearRect(0, 0, width, height);
		gc.setStroke(Color.rgb(0, 0, 0));
		gc.setLineWidth(2);;
		gc.strokeRect(0, 0, width, height);
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				board[i][j].render(gc);
			}
		}
	}
	
	//키보드 이벤트를 처리해주는 매서드
	public void keyHandler(KeyEvent e) {
		player.keyHandler(e); 
	}
}
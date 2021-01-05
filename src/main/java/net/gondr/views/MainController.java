package net.gondr.views;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.gondr.tetris1.App;
import net.gondr.tetris1.Game;

public class MainController {
	@FXML
	private Canvas gameCanvas;

	@FXML
	private Label score;

	@FXML
	private Button btn;

	@FXML
	public void initialize() {
		System.out.println("메인 레이아웃 초기화 완료!");
		score.setText("Score : " + "0" + "점");
		btn.setText("시작");
	}

	public void startBtn(MouseEvent e) {
		App.app.game = new Game(gameCanvas, score);
		btn.setText("재시작");
	}

	public void score(String e) {
		score.setText(e);
	}
}

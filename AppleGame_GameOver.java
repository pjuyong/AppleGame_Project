package AppleGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppleGame_GameOver extends JFrame {
	private AppleGame_Game appleGame;
	private JPanel gameOverPanel;
	private JPanel buttonPanel;
	private JLabel message;
	private JButton check;
	private JButton exit;
	private String nickNameInGameOver;
	private int totalScoreInGameOver;
	
	public AppleGame_GameOver(String nickNameInGame, int totalScore, AppleGame_Game appleGame) {
		setTitle("게임 종료!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350,175);    
        
        nickNameInGameOver = nickNameInGame;
        totalScoreInGameOver = totalScore;
        this.appleGame = appleGame;
        
        gameOverPanel = new JPanel();
        gameOverPanel.setBackground(Color.LIGHT_GRAY);
        add(gameOverPanel);
        gameOverPanel.setLayout(new BorderLayout()); 
        
        message = new JLabel(nickNameInGameOver + "님의 점수 : " + totalScoreInGameOver);
        message.setHorizontalAlignment(JLabel.CENTER);
        gameOverPanel.add(message,BorderLayout.CENTER);
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        check = new JButton("홈으로");
        buttonPanel.add(check);
        check.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		appleGame.dispose();
        		new AppleGame_Home();
        	}
        });

        exit = new JButton("게임종료");
        buttonPanel.add(exit);
        exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });

        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}
}

package AppleGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppleGame_Game extends JFrame {
	private int totalScore = 0; // 총 점수를 저장하는 변수
	private int removeCount = 0; // 삭제된 이미지의 개수를 저장하는 변수
	private int gameTime = 100; // 제한 시간 (초)
	private String nickNameInGame;
	private Timer timer;
	private TimerTask timerTask;
	private JLabel timerLabel;
	private JLabel TimerBar;
	
    public AppleGame_Game(String newName) {
        setTitle("Apple Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        
        nickNameInGame = newName;

        GameBoard gameBoardPanel = new GameBoard();
        add(gameBoardPanel);

        DragRectangle dragRectanglePanel = new DragRectangle(gameBoardPanel);
        add(dragRectanglePanel);
        
        TimerBar timerBar = new TimerBar(gameTime);
        dragRectanglePanel.add(timerBar);
        
        Thread timerBarThread = new Thread(timerBar);
        timerBarThread.start();

        gameTimer();
        
        setVisible(true);
    }
    
    class TimerBar extends JLabel implements Runnable {
    	int x = 140, y = 15, width = 500, height = 20;
    	int second;
    	Color color = new Color(100,100,100);

    	public TimerBar(int second) {
    		this.second = second;
    		setBackground(color);
    		setOpaque(true);
    		setBounds(x, y, width, height);
    	}

    	public void run() {
    		int sleepTime = 1000 / (width / second);
    		while (width > 0) {
    			try {
    				Thread.sleep(sleepTime); 
    			} 
    			catch (Exception e) { 
    				e.printStackTrace();
    			}

    			if (getWidth() > 0) {
    				width -= 1;	
    				setBounds(x, y, width, height);
    				repaint();
    			} 
    			else {
    				break;
    			}
    		}
    	}
    }

    public void gameTimer() {
    	timer = new Timer();
    	timerTask = new TimerTask() {
    		public void run() {
    			if (gameTime > 0) {
                    gameTime--;
                    timerUpdate();
                } 
                else {
                    endGame();
                }
    		}
    	};
    	
    	timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
    
    private void timerUpdate() {
        if (gameTime >= 0) {
        	timerLabel.setText("Time : " + gameTime + "초");
        }
    }

    private void endGame() {
    	timer.cancel();
        timerTask.cancel();
        AppleGame_GameOver gameOverFrame = new AppleGame_GameOver(nickNameInGame, totalScore, this);
        gameOverFrame.setLocationRelativeTo(this);
    }

    
    

    class DragRectangle extends JPanel {
        private int startX, startY, endX, endY;
        private Rectangle rectangle;
        private GameBoard gameBoardPanel;
        private JLabel scoreLabel;

        public DragRectangle(GameBoard gameBoardPanel) {
            this.gameBoardPanel = gameBoardPanel;
            setLayout(null);
            setBackground(Color.LIGHT_GRAY);
            setPreferredSize(new Dimension(900, 500));
            
            timerLabel = new JLabel("Time : " + gameTime + "초");
            timerLabel.setBounds(60,10,99,30);
            add(timerLabel);
            
            scoreLabel = new JLabel("Score : " + totalScore);
            scoreLabel.setBounds(700,10,116,30);
            add(scoreLabel);

            MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
            addMouseListener(myMouseAdapter);
            addMouseMotionListener(myMouseAdapter);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (rectangle != null) {
                Graphics2D g2d = (Graphics2D) g; // 렌더링 속성
                Stroke rectStroke = g2d.getStroke(); // stroke 속성(도령의 외곽선 모양 결정)
                g2d.setStroke(new BasicStroke(2)); // 선 굵기 => 2

                g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

                g2d.setStroke(rectStroke); // 원래 선의 굵기로 복원(다음 그림에 영향을 미치지 않도록)
            }

            gameBoardPanel.paintComponent(g);
        }

        private class MyMouseAdapter extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                int x = Math.min(startX, endX);
                int y = Math.min(startY, endY);
                int width = Math.abs(endX - startX); // 절대값 반환
                int height = Math.abs(endY - startY); // 절대값 반환

                rectangle = new Rectangle(x, y, width, height);

                repaint();
            }

            public void mouseReleased(MouseEvent e) {
            	if (rectangle != null) {
                    int sum = gameBoardPanel.sumImage(rectangle);

                    if (sum == 10) {
                        gameBoardPanel.removeImage(rectangle);
                        gameBoardPanel.scoreUpdate();
                        scoreLabel.setText("Score : " + totalScore);
                    }
                }
            	
                rectangle = null;
                repaint();
            }    
        }
    }
    
    

    class GameBoard extends JPanel {
        private Image[] appleImages;
        private int[] saveApple = {1,2,3,4,5,6,7,8,9}; // 정적 배열
        private int[] appleX;
        private int[] appleY;
        private int[][] appleBoard;

        public GameBoard() {
        	setLayout(null);
            setPreferredSize(new Dimension(900, 500));
            setBackground(Color.LIGHT_GRAY);
            
            
            appleImages = new Image[9];
            for (int i = 0; i < 9; i++) {
                appleImages[i] = loadImage("img/apple" + saveApple[i] + ".png");  // 이미지 로드 및 저장
            }
            
            appleX = new int[16];
            appleY = new int[7];
            appleBoard = new int[7][16];            

            for (int i = 0; i < 16; i++) {
                appleX[i] = 50 + i * 50; // 이미지 시작좌표의 x좌표를 배열에 넣어둠
            }

            for (int i = 0; i < 7; i++) {
                appleY[i] = 50 + i * 50; // 이미지 시작좌표의 y좌표 배열에 넣어둠
            }
           
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 16; j++) {
                	appleBoard[i][j] = getRandomIndex(); // 1~9까지 정수 랜덤 저장(배열 초기화)  
                }
            }      
        }
        
 
        private Image loadImage(String path) { // appleImages 배열에 대한 이미지를 load하는 메소드
        	ImageIcon icon = new ImageIcon(path); // path문자열이 이미지경로를 담는 문자열이다.
            return icon.getImage();        	
        }
        
        private int getRandomIndex() {
        	Random random = new Random();
        	return random.nextInt(9)+1;
        }
        
        protected void paintComponent(Graphics g) { // 이미지를 그리는 메소드.
            super.paintComponent(g);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 16; j++) {
                	for(int k=0; k<9; k++) {
                		if(appleBoard[i][j] == saveApple[k]) {
                    		g.drawImage(appleImages[k], appleX[j], appleY[i], 50, 50, this);
                    	}
                	}
                }
            }
        }
        
        public int sumImage(Rectangle rect) { // 그려진 사각형 안의 이미지의 합을 구하는 메소드
            int sum = 0;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int k = 0; k < 9; k++) {
                        int imageX = appleX[j];
                        int imageY = appleY[i];

                        if (imageX >= rect.getX() && imageX + 50 <= rect.getX() + rect.getWidth() && 
                        	imageY >= rect.getY() && imageY + 50 <= rect.getY() + rect.getHeight()) {
                            if (appleBoard[i][j] == saveApple[k]) {
                                sum += saveApple[k];
                            }
                        }
                    }
                }
            }

            return sum;
        }
        
        public void removeImage(Rectangle rect) { // 사각형 안의 이미지 합이 10인 이미지들을 삭제하는 메소드
        	for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int k = 0; k < 9; k++) {
                        int imageX = appleX[j];
                        int imageY = appleY[i];

                        if (imageX >= rect.getX() && imageX + 50 <= rect.getX() + rect.getWidth() &&
                            imageY >= rect.getY() && imageY + 50 <= rect.getY() + rect.getHeight()) {
                            if (appleBoard[i][j] == saveApple[k]) {
                                appleBoard[i][j] = 0; // appleBoard[][]의 원소를 0으로 바꾸어 이미지가 로드되지 않도록 한다.
                                removeCount ++;
                            }
                        }
                    }
                }
            }
        }
        
        public void scoreUpdate() {
        	totalScore += removeCount;
        	removeCount = 0;
       }
    }
    
}

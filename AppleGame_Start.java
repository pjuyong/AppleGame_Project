package AppleGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppleGame_Start extends JFrame {
	private JPanel startPanel;
	private JPanel centerPanel;
	private JLabel user;
	private ImageIcon startImage;
	private ImageIcon startImage2;
	private ImageIcon rankImage;
	private ImageIcon rankImage2;
	private JButton btnRank;
	private JButton btnStart;
	
	public AppleGame_Start(String newName) {
		setTitle("Apple Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,500);
        
        startPanel = new JPanel();
        startPanel.setBackground(Color.LIGHT_GRAY);
        add(startPanel);
        startPanel.setLayout(new BorderLayout());
        
        user = new JLabel("환영합니다, " + newName + "님!");
        user.setHorizontalAlignment(JLabel.CENTER);
        startPanel.add(user, BorderLayout.NORTH);
        
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setLayout(null);
        startPanel.add(centerPanel,BorderLayout.CENTER);
        
        rankImage = new ImageIcon("img/rankButton.png");
        rankImage2 = new ImageIcon("img/rankButton2.png");
        btnRank = new JButton(rankImage);
        btnRank.setBorder(null);
        btnRank.setContentAreaFilled(false);
        btnRank.setRolloverIcon(rankImage2); // 마우스를 올리면 이미지 변경
        btnRank.setBounds(200,170,100,70);
        centerPanel.add(btnRank);
        btnRank.addActionListener(new ActionListener () {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new AppleGame_Rank();
        	}
        }); 
        
        startImage = new ImageIcon("img/gameStartButton.png");
        startImage2 = new ImageIcon("img/gameStartButton2.png");
        btnStart = new JButton(startImage);
        btnStart.setBorder(null);
        btnStart.setContentAreaFilled(false);
        btnStart.setRolloverIcon(startImage2); // 마우스를 올리면 이미지 변경
        btnStart.setBounds(500,170,200,70);
        centerPanel.add(btnStart);
        
        btnStart.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new AppleGame_Game(newName);
        	}
        });
        

        
        setVisible(true);
	}
}

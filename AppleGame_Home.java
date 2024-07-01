package AppleGame;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppleGame_Home extends JFrame {
	public String newName;
	private JTextField nickNameInput;
	private Image appleGameTitle;
	private Image nickName;
	private Image check;
	private Image out;
	
	public AppleGame_Home() {
		setTitle("Apple Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,500);
        
        JPanel homePanel = new JPanel();
        homePanel.setBackground(Color.LIGHT_GRAY);
        add(homePanel);
        homePanel.setLayout(null);
        
        ImageIcon appleGameTitle = new ImageIcon("img/appleGameTitle.png");
        JLabel gameTitle = new JLabel(appleGameTitle);
        gameTitle.setBounds(280,50,350,100);
        homePanel.add(gameTitle);
        
        ImageIcon appleGameNickName = new ImageIcon("img/nickName.png");
        JLabel nickLabel1 = new JLabel(appleGameNickName);
        nickLabel1.setBounds(250,220,82,30);
        homePanel.add(nickLabel1);
        
        nickNameInput = new JTextField(15);
        nickNameInput.setBounds(340,220,145,30);
        homePanel.add(nickNameInput);
        
        // 확인 버튼
        ImageIcon appleGameCheck = new ImageIcon("img/checkButton.png");
        ImageIcon appleGameCheck2 = new ImageIcon("img/checkButton_2.png");
        JButton btnCheck = new JButton(appleGameCheck);
        btnCheck.setBorder(null);
        btnCheck.setContentAreaFilled(false);
        btnCheck.setRolloverIcon(appleGameCheck2);
        btnCheck.setBounds(500,210,60,50);
        homePanel.add(btnCheck);
        
        AppleGame_Home thisInstance = this;
        
        btnCheck.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		newName = nickNameInput.getText();
        		if (newName != null && !newName.isEmpty()) {
                    dispose();
                    new AppleGame_Start(newName);
                } 
        		else {
                    JOptionPane.showMessageDialog(thisInstance, "닉네임을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                }
        	}
        });
        
        // 나가기 버튼
        ImageIcon appleGameExit = new ImageIcon("img/outButton.png");
        ImageIcon appleGameExit2 = new ImageIcon("img/outButton_2.png");
        JButton btnExit = new JButton(appleGameExit);
        btnExit.setBorder(null);
        btnExit.setContentAreaFilled(false);
        btnExit.setRolloverIcon(appleGameExit2);
        btnExit.setBounds(570,210,150,50); 
        homePanel.add(btnExit);
        
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        
        setVisible(true);
	}
}


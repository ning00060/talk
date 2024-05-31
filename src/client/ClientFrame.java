package client;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import lombok.Data;


public class ClientFrame extends JFrame {

	private Client client;

	private Image backgroundImage;
	private JTabbedPane tabPane;
	private JPanel mainPanel;
	private LoginPanel loginPanel;
	private LoungePanel loungePanel;
	private MessagePanel messagePanel;

	public ClientFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}

	public void initData() {
		loginPanel = new LoginPanel(client, this);
		backgroundImage = new ImageIcon("images/IMG_9023.png").getImage();
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		mainPanel = new JPanel();
		messagePanel = new MessagePanel(client);
	}

	public void setInitLayout() {
		setTitle("프로토타입");
		setSize(1000, 506);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(null);
		setContentPane(mainPanel);

		tabPane.setBounds(0, 0, getWidth(), getHeight());
		mainPanel.add(tabPane);
		loginPanel.setLayout(null);
		tabPane.addTab("로그인", null, loginPanel, null);
//		tabPane.addTab("채팅", null, messagePanel, null);

	}

	public void addEventListener() {

	}

	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	public LoungePanel getLoungePanel() {
		return loungePanel;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

	public static void main(String[] args) {
		new ClientFrame();
	}
}

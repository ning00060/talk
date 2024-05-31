package client.copy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import lombok.Data;


public class LoginPanel extends JPanel {

	private Client client;
	private ClientFrame clientFrame;
	private LoungeFrame loungeFrame;

	private Image backgroundImage;
	private JPanel backgroundPanel;

	private JPanel linePanel;
	private JPanel ipPanel;
	private JPanel portPanel;
	private JPanel namePanel;

	private JLabel ipLabel;
	private JLabel portLabel;
	private JLabel nameLabel;

	private JTextField ipText;
	private JTextField portText;
	private JTextField nameText;

	private JButton loginButton;

	String ip;
	 public String name;
	String stringPort;
	int port;

	public LoginPanel(Client client, ClientFrame clientFrame) {
		this.client = client;
		this.clientFrame = clientFrame;
		initData();
		setInitLayout();
		addEventListener();
//		clientData(ip, name, port);

	}

	public void initData() {
		backgroundImage = new ImageIcon("images/Animal-Crossing-03.jpg").getImage();
		backgroundPanel = new JPanel();
		linePanel = new JPanel();

		ipPanel = new JPanel();
		portPanel = new JPanel();
		namePanel = new JPanel();

		ipLabel = new JLabel("ip");
		portLabel = new JLabel("port");
		nameLabel = new JLabel("닉네임");

		ipText = new JTextField(10);
		portText = new JTextField(10);
		nameText = new JTextField(10);

		ipText.setText("127.0.0.1");
		portText.setText("5000");

		loginButton = new JButton("연결");
	}

	public void setInitLayout() {
		setSize(getWidth(), getHeight());
		setLayout(null);
		setVisible(true);

//		backgroundPanel.setSize(getWidth(), getHeight());
//		backgroundPanel.setLayout(null);
//		add(backgroundPanel);

		linePanel.setBounds(0, 0, 510, 400);
		linePanel.setLayout(null);
		linePanel.setBackground(Color.white);
		linePanel.setBorder((new TitledBorder(new LineBorder(Color.BLACK, 5), "Login")));
		add(linePanel);

		ipPanel.setBounds(20, 50, 110, 50);
		ipPanel.setBackground(Color.white);
		ipPanel.add(ipLabel);
		ipPanel.add(ipText);
		linePanel.add(ipPanel);

		portPanel.setBounds(140, 50, 110, 50);
		portPanel.setBackground(Color.white);
		portPanel.add(portLabel);
		portPanel.add(portText);
		linePanel.add(portPanel);

		namePanel.setBounds(260, 50, 110, 50);
		namePanel.setBackground(Color.white);
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		linePanel.add(namePanel);

		loginButton.setBackground(Color.white);
		loginButton.setBounds(410, 50, 80, 50);
		linePanel.add(loginButton);
	}

	public void addEventListener() {
		if ((!ipText.getText().equals(null)) && (!portText.getText().equals(null))
				&& (!nameText.getText().equals(null))) {
			ip = ipText.getText().toString();
			name = nameText.getText().toString();
			stringPort = portText.getText().toString();
			port = Integer.parseInt(stringPort);
		}
		loginButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				clickConnectServerBtn();
				System.out.println("클릭실행됨" + ip + "," + name + "," + port);
				
				System.out.println(ipText.getText().toString() + "누름");
				System.out.println(getName() + "name받기성공");
				// 192.168.0.133
//				setVisible(false);

			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}

	
	public void clickConnectServerBtn() {
		
	
		(client = new Client(clientFrame)).setThename(name);
		name = nameText.getText().toString();
		client.ip = "127.0.0.1";
		client.port = 5000;
		try {
			client.connectToServer();
			client.setupStreams();

			client.socketWriter.write(name.trim() + "\n");
			client.socketWriter.flush();
			clientFrame.setTitle("[ KHA Talk_" + name + "님 ]");


		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	
}

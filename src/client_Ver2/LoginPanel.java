package client_Ver2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import lombok.Data;

@Data
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
	String name;
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

	public void clientData(String ip, String name, int port) {
		if ((!ipText.getText().equals(null)) && (!portText.getText().equals(null))
				&& (!nameText.getText().equals(null))) {
			ip = ipText.getText().toString();
			name = nameText.getText().toString();
			stringPort = portText.getText().toString();
			port = Integer.parseInt(stringPort);
			this.ip = ip;
			this.name = name;
			this.port = port;
		}
	}

	public void addEventListener() {
		loginButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("클릭실행됨");
				new LoungeFrame(client, clientFrame);
				client.run();
				System.out.println(ipText.getText().toString() + "누름");
				System.out.println(getName() + "name받기성공");
				// 192.168.0.133
//				setVisible(false);

			}
		});

	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		return Objects.hash(client, clientFrame);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		LoginPanel that = (LoginPanel) obj;
		return Objects.equals(client, that.client) && Objects.equals(clientFrame, that.clientFrame);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}

}

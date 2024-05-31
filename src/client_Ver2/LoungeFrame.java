package client_Ver2;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import lombok.Data;

@Data
public class LoungeFrame extends JFrame {
	private Client client;
	private ClientFrame clientFrame;

	private LoungePanel loungePanel;
	private LoginPanel loginPanel;

	private Image backgroundImage;
	private JPanel backgroundPanel;

	private JPanel gamePanel;
	private JPanel waitPanel;
	private JPanel mainPanel;

	private JLabel gameLabel;
	private JLabel loungeLabel;
	private JLabel mainLabel;

	private JList<String> userList;
	private JList<String> gameList;

	private JTextField nameText;

	private JButton selectButton;
	private JButton endButton;

	String ip;
	String name;
	String stringPort;
	int port;

	public LoungeFrame(Client client, ClientFrame clientFrame) {
		this.client = client;
		this.clientFrame = clientFrame;
		this.ip = clientFrame.getLoginPanel().getIp();
		this.port = clientFrame.getLoginPanel().getPort();
		this.name = clientFrame.getLoginPanel().getName();
		loungePanel = clientFrame.getLoungePanel();
		initData();
		setInitLayout();
		addEventListener();
	}

	public void initData() {
		backgroundImage = new ImageIcon("images/IMG_9023.png").getImage();

		gamePanel = new JPanel();
		waitPanel = new JPanel();
		mainPanel = new JPanel();

		gameLabel = new JLabel("게임중");
		loungeLabel = new JLabel("로비");

//		nameText = new JTextField(10);

		selectButton = new JButton("게임선택");
		endButton = new JButton("로비");

		userList = new JList<>();
		gameList = new JList<>();

	}

	public void setInitLayout() {
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setBounds(100, 60, 190, 380);
		mainPanel.setLayout(null);

		setTitle(clientFrame.getLoginPanel().getName());
		setSize(1300, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		mainPanel.add(loungePanel);

		gamePanel.setBounds(50, 30, 120, 260);
		gamePanel.setBackground(Color.green);
		gamePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "wwd"));

		gamePanel.add(gameList);
		add(gamePanel);

		waitPanel.setBounds(230, 30, 120, 260);
		waitPanel.setBackground(Color.red);
		waitPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "room List"));
		waitPanel.add(userList);
		add(waitPanel);

	}

	public void addEventListener() {

	}

}

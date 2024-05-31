package client.copy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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


public class LoungeFrame extends JFrame {
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
	Client client;
	String ip;
	String name;
	String stringPort;
	int port;

	public LoungeFrame(Client client, ClientFrame clientFrame) {
		this.clientFrame = clientFrame;

		initData();
		setInitLayout();
		addEventListener();
	}

	public void initData() {
		backgroundImage = new ImageIcon("images/IMG_9023.png").getImage();
		loungePanel =new LoungePanel(client);
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

		setTitle(clientFrame.getLoginPanel().getName());
		setSize(1300, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(loungePanel);




	}

	public void addEventListener() {

	}

	public LoungePanel getLoungePanel() {
		return loungePanel;
	}

	public void setLoungePanel(LoungePanel loungePanel) {
		this.loungePanel = loungePanel;
	}
	
	
	
	/////////내부
	public class LoungePanel extends JPanel implements ActionListener {

		private Client client;
		private ClientFrame clientFrame;

		private LoginPanel loginPanel;

		private Image backgroundImage;
		private JPanel backgroundPanel;
		private MessageFrame messageFrame;

		private JPanel gamePanel;
		private JPanel waitPanel;

		private JLabel gameLabel;
		private JLabel waitLabel;

		private JList<String> userList;
		private JList<String> gameList;

		private JTextField nameText;

		private JButton selectButton1;
		private JButton selectButton2;
		private JButton selectButton3;
		private JButton outRoomBtn;
		private JButton secretMsgBtn;

		String ip;
		String name;
		String stringPort;
		int port;

		private Vector<String> userIdVector = new Vector<>();
		private Vector<String> roomNameVector = new Vector<>();

		public LoungePanel(Client client) {

			messageFrame = new MessageFrame(client);
			nameText = new JTextField();
			secretMsgBtn = new JButton("send Message");
			initData();
			setInitLayout();
			addEventListener();

		}

		public void initData() {
			backgroundImage = new ImageIcon("images/sb18.jpg").getImage();

			gamePanel = new JPanel();
			waitPanel = new JPanel();

			gameLabel = new JLabel("게임중");
			waitLabel = new JLabel("로비");

//			nameText = new JTextField(10);

			selectButton1 = new JButton("마리오");
			selectButton2 = new JButton("포켓몬");
			selectButton3 = new JButton("젤다");
			outRoomBtn = new JButton("로비");

			userList = new JList<>();
			gameList = new JList<>();

			nameText = new JTextField();
			messageFrame.setVisible(false);

		}

		public void setInitLayout() {

			setSize(1300, 600);
			setLayout(null);
			setVisible(true);

			gamePanel.setBounds(0, 0, 500, 200);
			gamePanel.setBackground(Color.white);
			gamePanel.setLayout(null);
			gamePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "game List"));

			gamePanel.add(gameList);
			add(gamePanel);

			waitPanel.setBounds(0, 210, 500, 200);
			waitPanel.setBackground(new Color(255, 0, 0, 80));
			waitPanel.setLayout(null);
			waitPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "user List"));
			waitPanel.add(userList);
			add(waitPanel);

			selectButton1.setBackground(new Color(255, 0, 0, 150));
			selectButton1.setBounds(30, 440, 120, 20);
			add(selectButton1);
			selectButton2.setBackground(new Color(255, 255, 0, 100));
			selectButton2.setBounds(160, 440, 120, 20);
			add(selectButton2);
			selectButton3.setBackground(new Color(0, 255, 255, 50));
			selectButton3.setBounds(290, 440, 120, 20);
			add(selectButton3);
			outRoomBtn.setBackground(new Color(0, 255, 255, 50));
			outRoomBtn.setBounds(290, 540, 50, 20);
			add(outRoomBtn);

			secretMsgBtn.setBounds(30, 535, 240, 20);
			secretMsgBtn.setBackground(Color.WHITE);
			secretMsgBtn.setEnabled(true);
			nameText.setBounds(30, 500, 240, 23);
			add(secretMsgBtn);
			add(nameText);
		}

		public void addEventListener() {
			selectButton1.addActionListener(this);
			selectButton2.addActionListener(this);
			selectButton3.addActionListener(this);
			outRoomBtn.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == outRoomBtn) {
				messageFrame.setVisible(true);
			}
			if (e.getSource() == secretMsgBtn) {

				String msg = nameText.getText();
				if (!msg.equals(null)) {
					client.clickSendMessageBtn(msg);
					nameText.setText("");
					userList.setSelectedValue(null, false);
				}

			}

			if (e.getSource() == selectButton1) {

				String roomName = gameList.getSelectedValue();
				client.clickEnterRoomBtn(roomName);
				gameList.setSelectedValue(null, false);

			} else if (e.getSource() == selectButton2) {

				String roomName = gameList.getSelectedValue();
				client.clickEnterRoomBtn(roomName);
				gameList.setSelectedValue(null, false);

			} else if (e.getSource() == selectButton3) {

				String roomName = gameList.getSelectedValue();
				client.clickEnterRoomBtn(roomName);
				gameList.setSelectedValue(null, false);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 500, 0, getWidth(), getHeight(), null);
		}

		public MessageFrame getMessageFrame() {
			return messageFrame;
		}

		public void setMessageFrame(MessageFrame messageFrame) {
			this.messageFrame = messageFrame;
		}

		public JList<String> getUserList() {
			System.out.println("아아아리스트다다다다다");
			return userList;
		}

		public void setUserList(JList<String> userList) {
			this.userList = userList;
		}

		public JList<String> getGameList() {
			return gameList;
		}

		public void setGameList(JList<String> gameList) {
			this.gameList = gameList;
		}

		public JButton getSelectButton1() {
			return selectButton1;
		}

		public void setSelectButton1(JButton selectButton1) {
			this.selectButton1 = selectButton1;
		}

		public JButton getSelectButton2() {
			return selectButton2;
		}

		public void setSelectButton2(JButton selectButton2) {
			this.selectButton2 = selectButton2;
		}

		public JButton getSelectButton3() {
			return selectButton3;
		}

		public void setSelectButton3(JButton selectButton3) {
			this.selectButton3 = selectButton3;
		}

		public JButton getOutRoomBtn() {
			return outRoomBtn;
		}

		public void setOutRoomBtn(JButton outRoomBtn) {
			this.outRoomBtn = outRoomBtn;
		}

	}

}

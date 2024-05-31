package client;

import java.awt.Image;
import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import lombok.Data;

@Data
public class MessageFrame extends JFrame {

	private Client client;
	private MessagePanel messagePanel;
	// 백그라운드 이미지 컴포넌트
	private Image backgroundImage;
	private JPanel backgroundPanel;

	// 패널
	private JPanel mainPanel;
	private JPanel bottomPanel;

	// 스크롤
	private ScrollPane scrollPane;

	// 텍스트 컴포넌트
	private JTextArea mainMessageBox;
	private JTextField writeMessageBox;

	// 메세지 보내기 버튼
	private JButton sendMessageBtn;

	public MessageFrame(Client client) {
		initObject();
		initSetting();
//		initListener();
	}

	private void initObject() {
		backgroundImage = new ImageIcon("images/IMG_9023.png").getImage();
		backgroundPanel = new JPanel();
		messagePanel = new MessagePanel(client);
		mainPanel = new JPanel();

	}

	private void initSetting() {
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setBounds(100, 60, 190, 380);
		mainPanel.setLayout(null);
		setTitle(messagePanel.getName());
		setSize(1300, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		mainPanel.add(messagePanel);

	}

	private void sendMessage() {
		if (!writeMessageBox.getText().equals(null)) {
			String msg = writeMessageBox.getText();
			client.clickSendMessageBtn(msg);
			writeMessageBox.setText("");
			writeMessageBox.requestFocus();
		}
	}

}

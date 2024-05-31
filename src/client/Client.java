package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import lombok.Data;

@Data
// @noargus
public class Client {

	static ClientFrame clientFrame;

	private LoginPanel loginPanel;
	private LoungePanel loungePanel;
	private LoungeFrame loungeFrame;
	private MessagePanel messagePanel;
	private MessageFrame messageFrame;

	private Socket socket;
	public PrintWriter socketWriter;
	private BufferedReader socketReader;
	private BufferedReader keyboardReader;

	private JTextArea mainMessageBox;
	private JList<String> userList;
	private JList<String> gameList;
	private JButton SelectButton1;
	private JButton SelectButton2;
	private JButton SelectButton3;
	private JButton outRoomBtn;
	private JButton sendMessageBtn;

	public String ip;
	private String name;
	public int port;

	private String myRoomName;

	private String protocol;
	private String from;
	private String message;

	private Vector<String> userIdList = new Vector<>();
	private Vector<String> gameNameList = new Vector<>();

	public Client(ClientFrame clientFrame) {
		loungeFrame = new LoungeFrame(this, clientFrame);
		loungePanel = new LoungePanel(this);
		//리스트
		userList = this.getLoungeFrame().getLoungePanel().getUserList();
		gameList = this.getLoungeFrame().getLoungePanel().getGameList();
		mainMessageBox = this.getLoungeFrame().getLoungePanel().getMessageFrame().getMessagePanel().getMainMessageBox();
		
		// 버튼
		SelectButton1 = this.getLoungeFrame().getLoungePanel().getSelectButton1();
		SelectButton2 = this.getLoungeFrame().getLoungePanel().getSelectButton2();
		SelectButton3 = this.getLoungeFrame().getLoungePanel().getSelectButton3();
		outRoomBtn = this.getLoungeFrame().getLoungePanel().getOutRoomBtn();
		sendMessageBtn = this.getLoungeFrame().getLoungePanel().getMessageFrame().getMessagePanel().getSendMessageBtn();
	}

	public void run(String ip, String name, int port) {
		this.ip = ip;
		this.name = name;
		this.port = port;
		System.out.println("run실행");
		try {
			System.out.println("connect시작");
			connectToServer();
			System.out.println("1성공");
			setupStreams();
			System.out.println("2성공");
			socketWriter.write(name + "\n");
			System.out.println("writert사용중");
			socketWriter.flush();
			clientFrame.setTitle(name);
//			clientFrame.setVisible(false);

		} catch (IOException e) {
			System.out.println(">>>>> 접속 종료 <<<<< ");
		}

	}

	public void connectToServer() throws IOException {
		try {

			System.out.println(this.ip);
			this.socket = new Socket(ip, port);
			System.out.println("[" + name + "]" + "호스트 접속됨");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "커넥트오류");
		}
	}

	public void setupStreams() throws IOException {
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new PrintWriter(socket.getOutputStream(), true);
		keyboardReader = new BufferedReader(new InputStreamReader(System.in));

		readThread();

	}

	public void writer(String str) {
		try {
			socketWriter.write(str + "\n");
			socketWriter.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "클라이언트 출력 장치 에러 !");
		}
	}

	private void readThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = socketReader.readLine();
						System.out.println("checkpro" + msg);
						checkProtocol(msg);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "클라이언트 입력 장치 에러 !");
						break;
					}
				}
			}
		}).start();
	}

	private void checkProtocol(String msg) {
		StringTokenizer tokenizer = new StringTokenizer(msg, "/");

		protocol = tokenizer.nextToken();
		from = tokenizer.nextToken();

		if (protocol.equals("Chatting")) {
			message = tokenizer.nextToken();
			chatting();

		} else if (protocol.equals("SecretMessage")) {
			message = tokenizer.nextToken();
			secretMessage();
		} else if (protocol.equals("MakeRoom")) {
			makeRoom();

		} else if (protocol.equals("MadeRoom")) {
			madeRoom();

		} else if (protocol.equals("NewRoom")) {
			newRoom();

		} else if (protocol.equals("OutRoom")) {
			outRoom();

		} else if (protocol.equals("EnterRoom")) {
			enterRoom();

		} else if (protocol.equals("NewUser")) {
			newUser();
		} else if (protocol.equals("ConnectedUser")) {
			connectedUser();
		} else if (protocol.equals("EmptyRoom")) {
			gameNameList.remove(from);
			gameList.setListData(gameNameList);
			SelectButton1.setEnabled(true);
			SelectButton2.setEnabled(true);
//			outRoomBtn.setEnabled(false);
		} else if (protocol.equals("FailMakeRoom")) {
			JOptionPane.showMessageDialog(null, "같은 이름의 방이 존재합니다 !");
		} else if (protocol.equals("UserOut")) {
			userIdList.remove(from);
			userList.setListData(userIdList);
		}
	}

	public void chatting() {
		if (name.equals(from)) {
			mainMessageBox.append("[나] \n" + message + "\n");
		} else if (from.equals("입장")) {
			mainMessageBox.append("▶" + from + "◀" + message + "\n");
		} else if (from.equals("퇴장")) {
			mainMessageBox.append("▷" + from + "◁" + message + "\n");
		} else {
			mainMessageBox.append("[" + from + "] \n" + message + "\n");
		}
	}

	public void secretMessage() {
		JOptionPane.showMessageDialog(null, from + "님의 메세지\n\"" + message + "\"", "[비밀메세지]", JOptionPane.PLAIN_MESSAGE);
	}

	public void newUser() {
		if (!from.equals(this.name)) {
			userIdList.add(from);
			userList.setListData(userIdList);
			System.out.println("newuser 작동");
		}
	}

	public void makeRoom() {
		myRoomName = from;
		SelectButton1.setEnabled(false);
		SelectButton2.setEnabled(false);
		outRoomBtn.setEnabled(true);
	}

	public void madeRoom() {
		gameNameList.add(from);
		if (!(gameNameList.size() == 0)) {
			gameList.setListData(gameNameList);
		}
	}

	public void newRoom() {
		gameNameList.add(from);
		gameList.setListData(gameNameList);
	}

	public void outRoom() {
		myRoomName = null;
		mainMessageBox.setText("");
		SelectButton1.setEnabled(true);
		SelectButton2.setEnabled(true);
		outRoomBtn.setEnabled(false);
	}

	public void enterRoom() {
		myRoomName = from;
		SelectButton1.setEnabled(false);
		SelectButton2.setEnabled(false);
		outRoomBtn.setEnabled(true);
	}

	public void connectedUser() {
		System.out.println("현재 from: " + from);
		userIdList.add(from);
		System.out.println("업데이트된 userIdList: " + userIdList);
		userList.setListData(userIdList.toArray(new String[0]));
		System.out.println("connectedUser 작동");
		System.out.println("userList 상태: " + userList + ", 항목: " + Arrays.toString(userIdList.toArray()));
	}

	public void clickSendMessageBtn(String messageText) {
		if (myRoomName != null) {
			writer("Chatting/" + myRoomName + "/" + messageText);
		} else {
			String user = (String) clientFrame.getLoungePanel().getUserList().getSelectedValue();
			writer("SecretMessage/" + user + "/" + messageText);
		}
	}

	public void clickOutRoomBtn(String roomName) {
		writer("OutRoom/" + roomName);
	}

	public void clickEnterRoomBtn(String roomName) {
		writer("EnterRoom/" + roomName);
	}

	public void setThename(String name) {
		this.name = name;
	}
	
//	public static void main(String[] args) {
//		new Client();
//	}
}

////package server;
////
////import java.io.BufferedReader;
////import java.io.IOException;
////import java.io.InputStreamReader;
////import java.io.PrintWriter;
////import java.net.ServerSocket;
////import java.net.Socket;
////import java.util.Vector;
////
////import javax.swing.JTextArea;
////
////public class Server {
////	private ServerFrame serverFrame;
////	
////	private JTextArea chatText;
////
////	public Server() {
////		serverFrame = new ServerFrame(this);
////		chatText=serverFrame.getMainBoard();
////	}
////
////	private static final int PORT = 5000;
////	private static Vector<PrintWriter> clientWriters = new Vector<>();
////
////	private static Vector<Games> gameUsers = new Vector<>();
//////	private static Vector<Games2> marioUsers = new Vector<>();
//////	private static Vector<Games3> zeldaUsers = new Vector<>();
////
////	public void starts() {
////		System.out.println("Server started....");
////
////		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
////
////			new Thread(new Runnable() {
////				Socket socket;
////				
////				@Override
////				public void run() {
////					// TODO Auto-generated method stub
////					while (true) {
////						try {
////							socket = serverSocket.accept();
////						} catch (IOException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						}
////						
////						new ClientHandler(socket).start();
////						
////					}
////					
////				}
////			});starts();
////			
////
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
////	}
////
////	// 정적 내부 클래스 설계
////	private static class ClientHandler extends Thread {
////		private Socket socket;
////		private PrintWriter out;
////		private BufferedReader in;
////
////		public ClientHandler(Socket socket) {
////			this.socket = socket;
////		}
////
////		// 스레드 start() 호출시 동작 되는 메서드 -약속
////		@Override
////		public void run() {
////			try {
////				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////				out = new PrintWriter(socket.getOutputStream(), true);
////
////				clientWriters.add(out);
////
////				String message;
////				while ((message = in.readLine()) != null) {
////					System.out.println("Received:" + message);
////				}
////				for (PrintWriter writer : clientWriters) {
////					// 스트림을 통해 데이터 전달
////					writer.println(message); // 모든 클라이언트에게 메세지 전송
////
////				}
////
////			} catch (Exception e) {
////				e.printStackTrace();
////			} finally {
////				try {
////					socket.close();
////					System.out.println("클라이언트 연결 해제");
////				} catch (Exception e2) {
////					e2.printStackTrace();
////				}
////			}
////		}
////	} // end of ClientHandler
////
////	private class Games {
////
////		private String roomName;
////		// myRoom에 들어온 사람들의 정보가 담김.
////		private Vector<PrintWriter> myRoom = new Vector<>();
////
////		public Games(String roomName, PrintWriter connectedUser) {
////			this.roomName = roomName;
////			this.myRoom.add(connectedUser);
//////			connectedUser.myRoomName = roomName;
////		}
////
////		// 모든 클라이언트에게 메시지 보내기 -브로드캐스트
////		private static void brodcastMessage(String message) {
////
////			for (PrintWriter writer : clientWriters) {
////				writer.println(message);
////			}
////		}
////	}
////
////	public static void main(String[] args) {
////		new Server();
////	}// main
////}
//
//package server;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.StringTokenizer;
//import java.util.Vector;
//
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
//
//public class Server2 {
//
//	// 접속된 유저 벡터
//	private Vector<ConnectedUser> connectedUsers = new Vector<>();
//	// 만들어진 방 벡터
//	private Vector<MyRoom> madeRooms = new Vector<>();
//
//	// 프레임 창
//	private ServerFrame serverFrame;
//
//	private JTextArea mainBoard;
//
//	// 소켓 장치
//	private ServerSocket serverSocket;
//	private Socket socket;
//
//	// 파일 저장을 위한 장치
//	private FileWriter fileWriter;
//
//	// 방 만들기 같은 방 이름 체크
//	private boolean roomCheck;
//
//	private String protocol;
//	private String from;
//	private String message;
//
//	public Server2() {
//		serverFrame = new ServerFrame(this);
//		roomCheck = true;
//		mainBoard = serverFrame.getMainBoard();
//	}
//
//	/**
//	 * 포트번호 입력하고 버튼 누르면 포트번호로 서버 시작.
//	 */
//	public void starts() {
//		try {
//			// 서버 소켓 장치
//			serverSocket = new ServerSocket(5000);
//			serverViewAppendWriter("[알림] 서버 시작\n");
//			serverFrame.getConnectBtn().setEnabled(false);
//			connectClient();
//
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "이미 사용중인 포트입니다.", "알림", JOptionPane.ERROR_MESSAGE);
//			serverFrame.getConnectBtn().setEnabled(true);
//		}
//	}
//
//	/**
//	 * 서버 대기하여 소켓 연결을 하고, 스레드 실행<br>
//	 */
//	private void connectClient() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while (true) {
//					try {
//
//						// 소켓 장치
//						socket = serverSocket.accept();
//						serverViewAppendWriter("[알림] 사용자 접속 대기\n");
//
//						// 연결을 대기 하다가 유저가 들어오면 유저 생성, 소켓으로 유저 구분 가능.
//						ConnectedUser user = new ConnectedUser(socket);
//						user.start();
//					} catch (IOException e) {
//						// 서버 중지
//						serverViewAppendWriter("[에러] 접속 에러 ! !\n");
//
//					}
//				}
//			}
//		}).start();
//	}
//
//	/**
//	 * 전체 접속된 유저에게 출력하는 것
//	 * 
//	 * @param msg
//	 */
//	private void broadCast(String msg) {
//		for (int i = 0; i < connectedUsers.size(); i++) {
//			ConnectedUser user = connectedUsers.elementAt(i);
//			user.writer(msg);
//		}
//	}
//
//	/**
//	 * 서버로 들어오는 요청은 모두 저장되는 파일 Writer.<br>
//	 * 
//	 * @param str
//	 */
//	private void serverViewAppendWriter(String str) {
//		try {
//			fileWriter = new FileWriter("kha_talk_log.txt", true);
//			mainBoard.append(str);
//			fileWriter.write(str);
//			fileWriter.flush();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 소켓이 연결이 되면 ConnectedUser클래스가 생성이 된다.
//	 * 
//	 * @author 김현아
//	 *
//	 */
//	private class ConnectedUser extends Thread {
//		// 소켓 장치
//		private Socket socket;
//
//		// 입출력 장치
//		private BufferedReader reader;
//		private BufferedWriter writer;
//
//		// 유저 정보
//		private String name;
//		private String myRoomName;
//
//		public ConnectedUser(Socket socket) {
//			this.socket = socket;
//			connectIO();
//		}
//
//		/**
//		 * 입출력 장치 연결<br>
//		 */
//		private void connectIO() {
//			try {
//				// 입출력 장치
//				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//				sendInfomation();
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "서버 입출력 장치 에러!", "알림", JOptionPane.ERROR_MESSAGE);
//				serverViewAppendWriter("[에러] 서버 입출력 장치 에러 ! !\n");
//			}
//		}
//
//		/**
//		 * 처음 유저가 로그인 되었을때 화면 부분의 명단 업데이트와<br>
//		 * 접속되어 있는 유저들에게 새로운 유저(로그인 한 새로운 유저 )를 알리기<br>
//		 */
//		private void sendInfomation() {
//			try {
//				// 유저의 아이디를 가지고 온다.
//				name = reader.readLine();
//				serverViewAppendWriter("[접속] " + name + "님\n");
//
//				// 접속된 유저들에게 유저 명단 업데이트를 위한 출력
//				newUser();
//
//				// 방금 연결된 유저측에서 유저 명단 업데이트를 위한 출력
//				connectedUser();
//
//				// 방금 연결된 유저측에서 룸 명단 업데이트를 위한 출력
//				madeRoom();
//
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
//				serverViewAppendWriter("[에러] 접속 에러 ! !\n");
//			}
//		}
//
//		@Override
//		public void run() {
//			try {
//				while (true) {
//					String str = reader.readLine();
//					checkProtocol(str);
//				}
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "유저 접속 끊김 !", "알림", JOptionPane.ERROR_MESSAGE);
//				serverViewAppendWriter("[에러] 유저 " + name + " 접속 끊김 ! !\n");
//				for (int i = 0; i < madeRooms.size(); i++) {
//					MyRoom myRoom = madeRooms.elementAt(i);
//					if (myRoom.roomName.equals(this.myRoomName)) {
//						myRoom.removeRoom(this);
//					}
//				}
//				connectedUsers.remove(this);
//				broadCast("UserOut/" + name);
//			}
//		}
//
//		/**
//		 * 프로토콜을 구별해서 해당 메소드 호출 <br>
//		 * 
//		 * @param str
//		 */
//		private void checkProtocol(String str) {
//			StringTokenizer tokenizer = new StringTokenizer(str, "/");
//			// MakeRoom/비밀방/
//			// Chatting/아아아/하이
//			protocol = tokenizer.nextToken();
//			from = tokenizer.nextToken();
//
//			if (protocol.equals("Chatting")) {
//				message = tokenizer.nextToken();
//				chatting();
//
//			} else if (protocol.equals("SecretMessage")) {
//				message = tokenizer.nextToken();
//				secretMessage();
//
//			} else if (protocol.equals("MakeRoom")) {
//				makeRoom();
//
//			} else if (protocol.equals("OutRoom")) {
//				outRoom();
//
//			} else if (protocol.equals("EnterRoom")) {
//				enterRoom();
//			}
//		}
//
//		/**
//		 * 클라이언트측으로 보내는 응답<br>
//		 * 
//		 * @param str
//		 */
//		private void writer(String str) {
//			try {
//				writer.write(str + "\n");
//				writer.flush();
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "서버 출력 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//
//		/**
//		 * 프로토콜 인터페이스 <br>
//		 */
//		public void chatting() {
//			serverViewAppendWriter("[메세지] " + from + "_" + message + "\n");
//
//			for (int i = 0; i < madeRooms.size(); i++) {
//				MyRoom myRoom = madeRooms.elementAt(i);
//
//				if (myRoom.roomName.equals(from)) {
//					myRoom.roomBroadCast("Chatting/" + name + "/" + message);
//				}
//			}
//		}
//
//		public void secretMessage() {
//			serverViewAppendWriter("[비밀 메세지] " + name + "ㅡ>" + from + "_" + message + "\n");
//
//			for (int i = 0; i < connectedUsers.size(); i++) {
//				ConnectedUser user = connectedUsers.elementAt(i);
//
//				if (user.name.equals(from)) {
//					user.writer("SecretMessage/" + name + "/" + message);
//				}
//			}
//		}
//
//		public void makeRoom() {
//			for (int i = 0; i < madeRooms.size(); i++) {
//				MyRoom room = madeRooms.elementAt(i);
//
//				if (room.roomName.equals(from)) {
//					writer("FailMakeRoom/" + from);
//					serverViewAppendWriter("[방 생성 실패]" + name + "_" + from + "\n");
//					roomCheck = false;
//				} else {
//					roomCheck = true;
//				}
//			}
//
//			if (roomCheck) {
//				myRoomName = from;
//				MyRoom myRoom = new MyRoom(from, this);
//				madeRooms.add(myRoom);
//				serverViewAppendWriter("[방 생성]" + name + "_" + from + "\n");
//
//				newRoom();
//				writer("MakeRoom/" + from);
//			}
//		}
//
//		public void newRoom() {
//			broadCast("NewRoom/" + from);
//		}
//
//		public void outRoom() {
//			for (int i = 0; i < madeRooms.size(); i++) {
//				MyRoom myRoom = madeRooms.elementAt(i);
//
//				if (myRoom.roomName.equals(from)) {
//					myRoomName = null;
//					myRoom.roomBroadCast("Chatting/퇴장/" + name + "님 퇴장");
//					serverViewAppendWriter("[방 퇴장]" + name + "_" + from + "\n");
//					myRoom.removeRoom(this);
//					writer("OutRoom/" + from);
//				}
//			}
//		}
//
//		public void enterRoom() {
//			for (int i = 0; i < madeRooms.size(); i++) {
//				MyRoom myRoom = madeRooms.elementAt(i);
//
//				if (myRoom.roomName.equals(from)) {
//					myRoomName = from;
//					myRoom.addUser(this);
//					myRoom.roomBroadCast("Chatting/입장/" + name + "님 입장");
//					serverViewAppendWriter("[입장]" + from + " 방_" + name + "\n");
//					writer("EnterRoom/" + from);
//				}
//			}
//		}
//
//		public void newUser() {
//			// 자기자신을 벡터에 추가
//			connectedUsers.add(this);
//			broadCast("NewUser/" + name);
//		}
//
//		public void connectedUser() {
//			for (int i = 0; i < connectedUsers.size(); i++) {
//				ConnectedUser user = connectedUsers.elementAt(i);
//				writer("ConnectedUser/" + user.name);
//			}
//		}
//
//		public void madeRoom() {
//			for (int i = 0; i < madeRooms.size(); i++) {
//				MyRoom myRoom = madeRooms.elementAt(i);
//				writer("MadeRoom/" + myRoom.roomName);
//			}
//		}
//	}
//
//	/**
//	 * 방만들기를 했을때 생성되는 MyRoom클래스<br>
//	 * 
//	 * @author 김현아
//	 *
//	 */
//	private class MyRoom {
//
//		private String roomName;
//		// myRoom에 들어온 사람들의 정보가 담김.
//		private Vector<ConnectedUser> myRoom = new Vector<>();
//
//		public MyRoom(String roomName, ConnectedUser connectedUser) {
//			this.roomName = roomName;
//			this.myRoom.add(connectedUser);
//			connectedUser.myRoomName = roomName;
//		}
//
//		/**
//		 * 방에 있는 사람들에게 출력
//		 */
//		private void roomBroadCast(String msg) {
//			for (int i = 0; i < myRoom.size(); i++) {
//				ConnectedUser user = myRoom.elementAt(i);
//
//				user.writer(msg);
//			}
//		}
//
//		private void addUser(ConnectedUser connectedUser) {
//			myRoom.add(connectedUser);
//		}
//
//		private void removeRoom(ConnectedUser user) {
//			myRoom.remove(user);
//			boolean empty = myRoom.isEmpty();
//			if (empty) {
//				for (int i = 0; i < madeRooms.size(); i++) {
//					MyRoom myRoom = madeRooms.elementAt(i);
//
//					if (myRoom.roomName.equals(roomName)) {
//						madeRooms.remove(this);
//						serverViewAppendWriter("[방 삭제]" + user.name + "_" + from + "\n");
//						roomBroadCast("OutRoom/" + from);
//						broadCast("EmptyRoom/" + from);
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		new Server2();
//	}
//}
//

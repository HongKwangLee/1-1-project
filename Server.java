import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTextField tf_Port;
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JButton sv_start = new JButton("서버 실행");
	private JButton sv_end = new JButton("서버 종료");
	private JLabel label = new JLabel("포트 번호");
	
	private ServerSocket svsc;
	private Socket sc;
	private int port;
	Vector user = new Vector();
	private StringTokenizer st;
	
	public void Start(){
		try {
			svsc = new ServerSocket(port); // 서버 열기
			
			if(svsc != null){
				access();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "사용중인 포트입니다.", "알림", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void access(){
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						textArea.append("사용자 접속 대기중...\n");
						scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
						sc = svsc.accept();
						
						UserInfo user = new UserInfo(sc);
						
						user.start();
						
					} catch (IOException e) {
						break;
					}
				}
			}
		});
		th.start();
		
	}
	
	class UserInfo extends Thread{
		
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;
		
		private Socket user_sc;
		private String Nickname;
		
		UserInfo(Socket soc){
			this.user_sc = soc; // 유저 소켓
			
			userset();
		}
		
		public void userset(){
			try {
				is = user_sc.getInputStream();
				os = user_sc.getOutputStream();
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				
				Nickname = dis.readUTF();
				textArea.append(Nickname+"님 접속\n");
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
				
				BroadCast("NewUser/"+Nickname);
				
				for(int i = 0;i< user.size();i++){
					UserInfo User = (UserInfo)user.elementAt(i);
					SendMsg("OldUser/"+User.Nickname);
				}
				
				user.add(this);
				
				BroadCast("setListData/1");
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "스티림 에러", "알림", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void run(){
			while(true){
				try {
					String msg = dis.readUTF();
					
					st = new StringTokenizer(msg, " ");
					
					String pt = st.nextToken();
					
					if(pt.equals("/ㄱ") || pt.equals("/귓속말") || pt.equals("/귓")){
						String nname = st.nextToken();
						String Msg = "";
						while (st.hasMoreTokens()) {
							Msg += " "+st.nextToken();
						}
						for(int i = 0;i< user.size();i++){
							UserInfo User = (UserInfo)user.elementAt(i);
							if(User.Nickname.equals(nname)){
								User.SendMsg("Whisper/"+Nickname+" :"+Msg);
							}
						}
						SendMsg("Whisper/"+nname+"님에게 :"+Msg);
					}else{
						BroadCast(Nickname+"/"+msg);
					}
				} catch (IOException e) {
					try {
						dos.close();
						dis.close();
						user_sc.close();
						user.remove(this);
						BroadCast("UserOut/"+Nickname);
						BroadCast("setListData/1");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					textArea.append(Nickname+"님 접속 종료\n");
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					break;
				}
				
			}
		}
		
		public void BroadCast(String str){
			for(int i = 0;i< user.size();i++){
				UserInfo User = (UserInfo)user.elementAt(i);
				User.SendMsg(str);
			}
		}
		
		public void SendMsg(String msg){
			try {
				dos.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	Server(){
		dp(); // 화면
		actions();
	}
	
	// 엑션들추가
	public void actions(){
		sv_start.addActionListener(this);
		sv_end.addActionListener(this);
	}
	
	// 화면
	public void dp(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sv_start.setBounds(30, 350, 150, 50);
		contentPane.add(sv_start);
		
		sv_end.setBounds(210, 350, 150, 50);
		contentPane.add(sv_end);
		sv_end.setEnabled(false);
		
		label.setFont(new Font("굴림", Font.PLAIN, 18));
		label.setBounds(50, 310, 100, 30);
		contentPane.add(label);
		
		tf_Port = new JTextField();
		tf_Port.setBounds(150, 316, 190, 23);
		contentPane.add(tf_Port);
		tf_Port.setColumns(10);
		
		scrollPane.setBounds(12, 10, 370, 290);
		contentPane.add(scrollPane);
		textArea.setEditable(false);
		
		this.setVisible(true);
	}
	
	// 메인
	public static void main(String[] args) {
		
		new Server();
		
	}

	// 버튼
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == sv_start){
			//System.out.println("서버 실행 버튼");
			port = Integer.parseInt( tf_Port.getText().trim());
			Start();
			sv_start.setEnabled(false);
			tf_Port.setEditable(false);
			sv_end.setEnabled(true);
		}
		else if(e.getSource() == sv_end){
			//System.out.println("서버 종료 버튼");
			try {
				svsc.close();
				user.removeAllElements();
				textArea.append("서버종료\n");
				sv_end.setEnabled(false);
				tf_Port.setEditable(true);
				sv_start.setEnabled(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}

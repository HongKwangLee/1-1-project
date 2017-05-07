import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTML;

public class Client extends JFrame implements ActionListener, KeyListener {
	
	private JPanel contentPane;
	private JTextField tf_ip;
	private JTextField tf_port;
	private JTextField tf_nickname;
	private JButton sv_join = new JButton("서버 접속");
	
	private JPanel contentPane2;
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JTextField textField;
	private JButton msg_enter = new JButton("전송");
	private JList list = new JList();
	private JScrollPane scrollPane2 = new JScrollPane(list);
	
	private Socket sc;
	private String Ip = "";
	private int port;
	private String nickname = "";
	
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	private StringTokenizer st;
	
	Vector user_list = new Vector();
	
	public void Start(){
		try {
			sc = new Socket(Ip, port); // 서버 접속
			
			if(sc != null){
				access();
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "연결실패", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void access(){
		try {		
			is = sc.getInputStream();
			os = sc.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "스트림 에러", "알림", JOptionPane.ERROR_MESSAGE);
		}
		
		SendMsg(nickname);
		
		user_list.add(nickname);
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						String msg = dis.readUTF();
						
						st = new StringTokenizer(msg, "/");
						
						String pt = st.nextToken();
						String Msg = st.nextToken();
						
						if(pt.equals("NewUser")){
							user_list.add(Msg);
							textArea.append(Msg+"님이 입장하셨습니다.\n");
						}else if(pt.equals("OldUser")){
							user_list.add(Msg);
						}else if(pt.equals("Whisper")){
							textArea.append(Msg+"\n");
							scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
						}else if(pt.equals("UserOut")){
							user_list.remove(Msg);
						}else if(pt.equals("setListData")){
							list.setListData(user_list);
						}else{
							textArea.append(pt+":"+Msg+"\n");
							scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
						}
						
					} catch (IOException e) {
						try {
							is.close();
							os.close();
							dis.close();
							dos.close();
							sc.close();
							JOptionPane.showMessageDialog(null, "서버 연결 에러", "알림", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}
				
			}
		});
		th.start();
	}
	
	
	// sendmsg
	public void SendMsg(String msg){
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace(); // 메세지 보넬때 오류 = 스트림 오류 알빠아님(맞나?)
		}
	}
	
	Client(){
		dp(); //접속 화면
		actions();
	}
	
	
	// 엑션들추가
	public void actions(){
		sv_join.addActionListener(this);
		msg_enter.addActionListener(this);
	}
	
	//채팅창 화면
	public void dp2(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 440);
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane2);
		contentPane2.setLayout(null);
		
		scrollPane.setBounds(12, 10, 406, 346);
		contentPane2.add(scrollPane);
		textArea.setEditable(false); // 엑션들추가 에다 넣었더니 반응 없음 해결해주실 수 있는분
		
		textField = new JTextField();
		textField.setBounds(12, 366, 406, 25);
		contentPane2.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(this);
		
		msg_enter.setBounds(430, 366, 142, 25);
		contentPane2.add(msg_enter);
		
		JLabel lblNewLabel = new JLabel("접속자");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(430, 8, 142, 25);
		contentPane2.add(lblNewLabel);
		
		scrollPane2.setBounds(430, 31, 142, 322);
		contentPane2.add(scrollPane2);
		
		this.setVisible(true);
	}
	
	//접속 화면
	public void dp(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("서버 IP : ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel.setBounds(30, 100, 80, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("서버 Port : ");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(30, 160, 100, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("닉네임 : ");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(50, 250, 80, 25);
		contentPane.add(lblNewLabel_2);
		
		tf_ip = new JTextField();
		tf_ip.setBounds(105, 102, 155, 25);
		contentPane.add(tf_ip);
		tf_ip.setColumns(10);
		
		tf_port = new JTextField();
		tf_port.setColumns(10);
		tf_port.setBounds(125, 160, 135, 25);
		contentPane.add(tf_port);
		
		tf_nickname = new JTextField();
		tf_nickname.setColumns(10);
		tf_nickname.setBounds(125, 252, 116, 25);
		contentPane.add(tf_nickname);
		
		sv_join.setBounds(90, 330, 100, 25);
		contentPane.add(sv_join);
		
		this.setVisible(true);
	}
	
	// 메인
	public static void main(String[] args) {
		
		new Client();
	}
	
	
	// 버튼 엑션
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sv_join){
			//System.out.println("서버 접속 버튼");
			if(tf_port.getText().trim().equals("")){
			}else if(tf_nickname.getText().trim().equals("")){
			}else{
				Ip = tf_ip.getText().trim();
				port = Integer.parseInt(tf_port.getText().trim());
				nickname = tf_nickname.getText().trim();			
				Start();
				dp2(); //채팅창 화면 // textArea.setEditable(false); 안되길래 였다 쳐넣음
				
			}
		}
		else if(e.getSource() == msg_enter){
			//System.out.println("메세지 전송 버튼");
			if(!textField.getText().equals("")){
				String msg = textField.getText().trim();
				SendMsg(msg);
				textField.setText("");
				textField.requestFocus();
			}
		}
	}
	
	
	// 키입력
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 10){
			//System.out.println("엔터키");
			if(!textField.getText().equals("")){
				String msg = textField.getText().trim();
				SendMsg(msg);
				textField.setText("");
				textField.requestFocus();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}

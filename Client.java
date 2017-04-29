import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTextField tf_ip;
	private JTextField tf_port;
	private JTextField tf_nickname;
	private JButton sv_join = new JButton("서버 접속");
	
	private JPanel contentPane2;
	private JTextField textField;
	private JButton msg_enter = new JButton("전송");
	
	
	Client(){
		dp(); //화면
		actions();
	}
	
	public void actions(){
		sv_join.addActionListener(this);
		msg_enter.addActionListener(this);
	}
	
	// 화면
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
	
	public void dp2(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 440);
		contentPane2 = new JPanel();
		contentPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane2);
		contentPane2.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 10, 406, 346);
		contentPane2.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(12, 366, 406, 25);
		contentPane2.add(textField);
		textField.setColumns(10);
		
		msg_enter.setBounds(430, 366, 142, 25);
		contentPane2.add(msg_enter);
		
		JLabel lblNewLabel = new JLabel("접속자");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(430, 8, 142, 25);
		contentPane2.add(lblNewLabel);
		
		JList list = new JList();
		list.setBounds(430, 31, 142, 322);
		contentPane2.add(list);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sv_join){
			System.out.println("서버 접속 버튼");
		}
		else if(e.getSource() == msg_enter){
			System.out.println("메세지 전송 버튼");
		}
		
	}

}

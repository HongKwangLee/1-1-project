import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTextField sv_ip;
	private JTextField sv_port;
	private JTextField Nickname;
	private JButton sv_join = new JButton("서버 접속");
	
	Client(){
		dp(); //화면
		actions();
	}
	
	public void actions(){
		sv_join.addActionListener(this);
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
		
		JLabel lblNewLabel_2 = new JLabel("포트 번호 : ");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(50, 250, 80, 25);
		contentPane.add(lblNewLabel_2);
		
		sv_ip = new JTextField();
		sv_ip.setBounds(105, 102, 155, 25);
		contentPane.add(sv_ip);
		sv_ip.setColumns(10);
		
		sv_port = new JTextField();
		sv_port.setColumns(10);
		sv_port.setBounds(125, 160, 135, 25);
		contentPane.add(sv_port);
		
		Nickname = new JTextField();
		Nickname.setColumns(10);
		Nickname.setBounds(125, 252, 116, 25);
		contentPane.add(Nickname);
		
		sv_join.setBounds(90, 330, 100, 25);
		contentPane.add(sv_join);
		
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
		
	}

}

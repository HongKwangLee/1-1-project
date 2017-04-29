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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTextField tf_Port;
	private JTextArea textArea = new JTextArea();
	private JButton sv_start = new JButton("서버 실행");
	private JButton sv_end = new JButton("서버 종료");
	private JLabel label = new JLabel("포트 번호");
	
	Server(){
		dp(); // 화면
		actions();
	}
	
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
		
		label.setFont(new Font("굴림", Font.PLAIN, 18));
		label.setBounds(50, 310, 100, 30);
		contentPane.add(label);
		
		tf_Port = new JTextField();
		tf_Port.setBounds(150, 316, 190, 23);
		contentPane.add(tf_Port);
		tf_Port.setColumns(10);
		
		textArea.setBounds(12, 10, 370, 290);
		contentPane.add(textArea);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new Server();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == sv_start){
			System.out.println("서버 실행 버튼");
		}
		else if(e.getSource() == sv_end){
			System.out.println("서버 종료 버튼");
		}
		
	}

}

package GUI;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bean.book;

import javax.swing.JButton;

public class StudentAppointment extends JPanel {
	//JPanel jp=new JPanel();
	Object[][] ob;
	String[] s={"���","����","����","����","������","����","ԤԼʱ��","����ʱ��","����"};
	Font font=new Font("����", Font.BOLD, 16);
	JScrollPane scrollPane ;
	private JTable table;
	private TableAction tableaction = new TableAction();
	public StudentAppointment() {
		setOpaque(false);
		setLayout(null);
		//����
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 52, 993, 434);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);	
		add(scrollPane);
		//�б�
		ob=new Object[0][9];
		table = new JTable(new MyTable(ob, s));
		table.setRowHeight(28);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		//ˢ�°�ť
		JButton reflush = new JButton("\u5237\u65B0/��ѯ");
		reflush.setFont(new Font("����", Font.BOLD, 18));
		reflush.setBounds(814, 10, 125, 32);
		reflush.setContentAreaFilled(false);
		add(reflush);
		//jp.getRootPane().setDefaultButton(reflush);
		reflush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				HandleOrder();//��ȡ����ֵ
			}
		});
	}
	//��ʼ���б�
	public void HandleOrder(){
		try {
			Socket socket=new Socket(Login.ip,8181);
			DataOutputStream toServer=new DataOutputStream(socket.getOutputStream());
			toServer.writeInt(6);//ԤԼ
			toServer.writeInt(2);
			toServer.writeUTF(Login.JTnum.getText());
			ObjectInputStream fromServerOb=new ObjectInputStream(socket.getInputStream());
			ArrayList<book> list=(ArrayList<book>)fromServerOb.readObject();
			ob=new Object[list.size()][9];
			int i=0,j=0;
			if (list.size()!=0){
				for (book book:list){
					if (j>8)
						j=0;
					ob[i][j++]=book.getNo();
					ob[i][j++]=book.getName();
					ob[i][j++]=book.getAuthor();
					ob[i][j++]=book.getTranslator();
					ob[i][j++]=book.getPublsh();
					ob[i][j++]=book.getSort();
					ob[i][j++]=book.getTime();
					ob[i][j++]=book.getOrderTime();
					ob[i][j++]="˫��ȡ��ԤԼ";
					i++;
				}
				table=new JTable(new MyTable(ob, s));
				table.setFont(font);
				table.setRowHeight(28);
				table.addMouseListener(tableaction);
			}
			else {
				JOptionPane.showMessageDialog(null, "û��ԤԼ�鼮");
				ob=new Object[0][9];
				table=new JTable(new MyTable(ob, s));
				table.setRowHeight(28);
			}
			scrollPane.setViewportView(table);
			toServer.flush();
			toServer.close();
			fromServerOb.close();
			socket.close();
		} catch (IOException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class TableAction implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int Mousercount = e.getClickCount();
			if (Mousercount == 2) {
				int r = table.getSelectedRow();
					String BookNum = table.getValueAt(r, 0).toString();
					String BookName = table.getValueAt(r, 1).toString();
					String BookAuthor = table.getValueAt(r, 2).toString();
					String BookTranslator;
					if (null == ob[r][3]) {
						BookTranslator = "";
					} else
						BookTranslator = table.getValueAt(r, 3).toString();
					String BookSort = table.getValueAt(r, 5).toString();
					String BookPublish = table.getValueAt(r, 4).toString();
					
					Point p = Student.frame.getLocation();
					String Snum=Login.JTnum.getText();
					int acount=Student.count;
					int x = (int) p.getX()+150;
					int y = (int) p.getY()+100;
					BookInformation bookinformation = new BookInformation(Snum,
							BookNum, BookName, BookAuthor, BookTranslator,
							BookSort, BookPublish,"sstock",acount,2);
					bookinformation.show(x, y);
					
				}
		}

		
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
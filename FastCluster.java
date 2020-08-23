package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import org.jfree.ui.RefineryUtilities;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.awt.Cursor;
import javax.swing.JFileChooser;
public class FastCluster extends JFrame implements Runnable{
	static JTextArea area;
	JScrollPane jsp;
	JPanel p1,p2,p3;
	JLabel l1;
	Font f1,f2;
	JButton b1,b2,b3,b4,b5,b6,b7;
	Thread thread;
	String title = "Fast Clustering Based Feature Selection Approach For Data";
	File file;
	JFileChooser chooser;
	
	double fast_nb_acc,fcbf_nb_acc;
	double fast_tree_acc,fcbf_tree_acc;
	
public FastCluster(){
	super("Fast Cluster Feature Selection");

	chooser = new JFileChooser(new File("dataset"));
	
	p1 = new JPanel();
	p1.setBackground(new Color(255,255,170));
	p1.setPreferredSize(new Dimension(600,80));
	l1 = new JLabel("<HTML><BODY><CENTER>"+title.toUpperCase()+"</center></body></html>");
	l1.setForeground(Color.black);
	l1.setFont(new Font("Times New Roman",Font.BOLD,18));
	p1.add(l1);

	p2 = new JPanel();
	p2.setPreferredSize(new Dimension(250,600));
	f2 = new Font("Times New Roman",Font.BOLD,16);

	b7 = new JButton("Upload Dataset");
	b7.setFont(f2);
	p2.add(b7);
	b7.setPreferredSize(new Dimension(200,40));
	b7.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(FastCluster.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				area.setText("");
				area.append("Dataset Loaded : "+file.getName()+"\n\n");
			}
		}
	});
	
	b1 = new JButton("FCFB C4.5");
	b1.setFont(f2);
	p2.add(b1);
	b1.setPreferredSize(new Dimension(200,40));
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				FCBFC45.fcbfc45(file);
				fcbf_tree_acc = FCBFC45.acc;
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b2 = new JButton("FCFB Naiye Bayes");
	b2.setFont(f2);
	b2.setPreferredSize(new Dimension(200,40));
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				FCBFNB.fcbfNB(file);
				fcbf_nb_acc = FCBFNB.acc;
				area.append("\n"+FCBFNB.sb.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b3 = new JButton("Fast Naiye Bayes");
	b3.setFont(f2);
	b3.setPreferredSize(new Dimension(200,40));
	p2.add(b3);

	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				FastNB.fastNB(file);
				fast_nb_acc = FastNB.acc;
				area.append("\n"+FastNB.sb.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b4 = new JButton("Fast C4.5");
	b4.setFont(f2);
	b4.setPreferredSize(new Dimension(200,40));
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				FastC45.fastc45(file);
				fast_tree_acc = FastC45.acc;
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b5 = new JButton("Comparision Chart");
	b5.setFont(f2);
	b5.setPreferredSize(new Dimension(200,40));
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Comparision Chart",fast_nb_acc,fcbf_nb_acc,fast_tree_acc,fcbf_tree_acc);
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b6 = new JButton("Back");
	b6.setFont(f2);
	b6.setPreferredSize(new Dimension(200,40));
	p2.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
		}
	});



	
	p3 = new JPanel();
	p3.setLayout(new BorderLayout());
	area = new JTextArea();
	area.setFont(f2);
	area.setEditable(false);
	area.setLineWrap(true);
	jsp = new JScrollPane(area);
	jsp.getViewport().setBackground(Color.white);
	
	p3.add(jsp,BorderLayout.CENTER);
	
	p3.setPreferredSize(new Dimension(750,600));

	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.WEST);
	getContentPane().add(p3,BorderLayout.EAST);
	thread = new Thread(this);
	thread.start();
}
public void run(){
	while(true){
		try{
			l1.setVerticalAlignment(JLabel.TOP);
			thread.sleep(500);
			l1.setHorizontalAlignment(JLabel.RIGHT);
			thread.sleep(500);
			l1.setHorizontalAlignment(JLabel.LEFT);
			thread.sleep(500);
			l1.setHorizontalAlignment(JLabel.CENTER);
			thread.sleep(500);
			l1.setVerticalAlignment(JLabel.BOTTOM);
			thread.sleep(500);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	FastCluster fc = new FastCluster();
	fc.setVisible(true);
	fc.setExtendedState(JFrame.MAXIMIZED_BOTH);
}
}
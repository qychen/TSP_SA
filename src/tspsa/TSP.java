package tspsa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TSP extends JFrame implements ActionListener,Runnable {

	public static double[] cx,cy,Edraw,EE;
	public static int n;
	public static int isin=0,isrun=0,isstart=0;
	public static int[] cursolu;
			
	public static double t0,tf,alpha,kk;
	public static int markov=15000;
	
	public void run()
	{
		//参数 
		int[] newsolu,bestsolu;
		double Ec,En,Eb,t,p,E=0,E1=0;

		int i,j,k,x,y,z;
		int sum=0,tt=(int)t0,num=0;
		
		cursolu = new int[n+1];
		newsolu = new int[n+1];
		bestsolu = new int[n+1];
		Edraw = new double[(int) (t0-tf+2)];
		Edraw[0]=0;
		EE = new double[(int) (t0-tf+2)];
		EE[0]=0;
		//System.out.println(t0+" "+tf+" "+markov+" "+alpha+" "+kk);
		
		//产生初始解	
		for(i=1;i<=n;i++)
		{
			cursolu[i]=i;
			newsolu[i]=i;
			bestsolu[i]=i;
		}
		Ec=0;
		for(i=2;i<=n;i++)
			Ec+=Math.sqrt((cx[i]-cx[i-1])*(cx[i]-cx[i-1])+(cy[i]-cy[i-1])*(cy[i]-cy[i-1]));
		Ec+=Math.sqrt((cx[cursolu[n]]-cx[cursolu[1]])*(cx[cursolu[n]]-cx[cursolu[1]])
				+(cy[cursolu[n]]-cy[cursolu[1]])*(cy[cursolu[n]]-cy[cursolu[1]]));
		En=Ec;
		Eb=Ec*100;
				
		//退火开始
		t=t0;	
		while(t>tf)
		{
			j=1;
			while(j<=markov)
			{
				//产生新解
				sum++;
				if(Math.random()<0.5)
				{
					//二交换
					x=(int)(Math.random()*(n-1))+2;
					y=(int)(Math.random()*(n-1))+2;
					while(y==x)
						y=(int)(Math.random()*(n-1))+2;
					
					for(i=1;i<=n;i++) 	newsolu[i]=cursolu[i];
					newsolu[x]=cursolu[y];
					newsolu[y]=cursolu[x];					
					
				}
				else
				{
					//三交换
					x=(int)(Math.random()*(n-1))+2;
					y=(int)(Math.random()*(n-1))+2;
					z=(int)(Math.random()*(n-1))+2;
					while(y==x)
						y=(int)(Math.random()*(n-1))+2;
					while((z==y)||(z==x))
						z=(int)(Math.random()*(n-1))+2;
					if(x>y) {k=x;x=y;y=k;}
					if(x>z) {k=x;x=z;z=k;}
					if(z<y) {k=z;z=y;y=k;}
					
					for(i=1;i<x;i++) 
						newsolu[i]=cursolu[i];
					for(i=1;i<=(z-y);i++) 
						newsolu[x+i-1]=cursolu[y+i];
					for(i=1;i<=(y-x+1);i++) 
						newsolu[z-y+x-1+i]=cursolu[x+i-1];
					for(i=1;i<=(n-z);i++) 
						newsolu[z+i]=cursolu[z+i];
				}
				
				//判断是否接受
				En=0;
				for(i=2;i<=n;i++)
					En+=Math.sqrt((cx[newsolu[i]]-cx[newsolu[i-1]])*(cx[newsolu[i]]-cx[newsolu[i-1]])
							+(cy[newsolu[i]]-cy[newsolu[i-1]])*(cy[newsolu[i]]-cy[newsolu[i-1]]));
				En+=Math.sqrt((cx[newsolu[n]]-cx[newsolu[1]])*(cx[newsolu[n]]-cx[newsolu[1]])
						+(cy[newsolu[n]]-cy[newsolu[1]])*(cy[newsolu[n]]-cy[newsolu[1]]));
				
				if(En<Ec)
				{
					for(i=1;i<=n;i++)
						cursolu[i]=newsolu[i];
					Ec=En;
					path.repaint();
				}
				else
				{
					p=Math.exp(-1*(En-Ec)/(kk*t));
					if(Math.random()<p)
					{
						for(i=1;i<=n;i++)
							cursolu[i]=newsolu[i];
						Ec=En;
						path.repaint();
					}
					
				}
				
				//更新历史最优解
				if(En<Eb)
				{
					for(i=1;i<=n;i++)
						bestsolu[i]=newsolu[i];
					Eb=En;
					//path.repaint();
				}
				
				//for(i=1;i<=n;i++)
				//	System.out.print(cursolu[i]+" ");
				//System.out.println(t+" "+Ec);
				/*
				String str=Double.toString(Ec);
				t6.setText(str);
				str=Double.toString(Eb);
				t7.setText(str);
				str=Double.toString(t);
				t8.setText(str);
				*/
				E+=Ec;
				E1+=Eb;
				num++;
				
				j++;
				while(isrun==0) {
					i=1;
					}
			}
			//降温
			t=t*alpha;
			if(((int)t)<tt)
			{
				BigDecimal m = new BigDecimal(Ec);
				Ec = m.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				String str=Double.toString(Ec);
				t6.setText(str);
				m = new BigDecimal(Eb);
				Eb = m.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				str=Double.toString(Eb);
				t7.setText(str);
				m = new BigDecimal(t);
				t = m.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				str=Double.toString(t);
				t8.setText(str);
				Edraw[0]++;
				Edraw[(int) Edraw[0]]=E/num;
				EE[0]++;
				EE[(int) EE[0]]=E1/num;
				E=0;num=0;E1=0;
				tt--;
				axis.repaint();
			}
		}
		/*
		for(i=1;i<=n;i++)
			System.out.print(bestsolu[i]+"—>");
		System.out.println(bestsolu[1]);
		System.out.println(Eb);
		System.out.println(sum);
		*/
		ExceptionDialog d1 = new ExceptionDialog(this,"Finish","求解结束！",1);
		isrun=0;isstart=0;
	}
	
	private JButton b1 = new JButton("输入");
	private JButton b2 = new JButton("开始");
	private JButton b3 = new JButton("暂停");
	private JButton b4 = new JButton("恢复");
	private JLabel l1 = new JLabel("初始温度");
	private JLabel l2 = new JLabel("终止温度");
	private JLabel l3 = new JLabel("扰动次数");
	private JLabel l4 = new JLabel("降温系数");
	private JLabel l5 = new JLabel("接受系数");
	private JLabel l6 = new JLabel("当前内能");
	private JLabel l7 = new JLabel("最佳内能");
	private JLabel l8 = new JLabel("当前温度");
	private JTextField t1 = new JTextField("97",5);
	private JTextField t2 = new JTextField("3",5);
	private JTextField t3 = new JTextField("15000",5);
	private JTextField t4 = new JTextField("0.97",5);
	private JTextField t5 = new JTextField("1",5);
	private JTextField t6 = new JTextField(5);
	private JTextField t7 = new JTextField(5);
	private JTextField t8 = new JTextField(5);
	
	private Path path;
	private Axis axis;
	private Thread sa;
	
	public TSP()
	{
		super("模拟退火算法求解旅行商问题演示");
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,4,5,5));
		p1.add(b1);p1.add(b2);p1.add(b3);p1.add(b4);
		
		//参数
		JPanel p21 = new JPanel();
		p21.setLayout(new FlowLayout());	
		p21.add(l1);p21.add(t1);p21.add(l2);p21.add(t2);p21.add(l5);p21.add(t5);
		
		JPanel p22 = new JPanel();
		p22.setLayout(new FlowLayout());	
		p22.add(l3);p22.add(t3);p22.add(l4);p22.add(t4);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2,1));	
		p2.add(p21);p2.add(p22);
		p2.setBorder(new TitledBorder("配置参数"));
		
		//数值结果
		JPanel p31 = new JPanel();
		p31.setLayout(new FlowLayout());	
		p31.add(l6);p31.add(t6);p31.add(l7);p31.add(t7);p31.add(l8);p31.add(t8);
		p31.setBorder(new TitledBorder("执行结果"));
		t6.setEditable(false);t7.setEditable(false);t8.setEditable(false);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(p1,"South");
		p3.add(p2,"Center");
		p3.add(p31,"North");

		
		//坐标图
		axis = new Axis();
		axis.setBackground(Color.white);
		axis.setBorder(new TitledBorder("内能变化曲线图"));
		
		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(2,1));
		p4.add(axis);p4.add(p3);
		
		//路线图
		path = new Path();
		path.setBackground(Color.white);
		path.setBorder(new TitledBorder("游走路线图"));
		//path.repaint();
		
		setLayout(new GridLayout(1,2));
		add(path);add(p4);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		pack();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			int i,x;
			//输入
			Scanner in;
			
			FileDialog fd1 = new FileDialog(this,"打开训练集",FileDialog.LOAD);
			fd1.setVisible(true);
			if(fd1.getFile()!=null){
				File f = new File(fd1.getDirectory()+fd1.getFile());
				if(f.exists())
				{
					try {
					in = new Scanner(new FileInputStream(f));
					n = in.nextInt();
					cx = new double[n+1];
					cy = new double[n+1];
					for(i=1;i<=n;i++)
					{
						x=in.nextInt();
						cx[i]=in.nextDouble();
						cy[i]=in.nextDouble();
					}
					cx[0]=0;cy[0]=0;
					for(i=1;i<=n;i++)
					{
						cx[0]=Math.max(cx[0], cx[i]);
						cy[0]=Math.max(cy[0], cy[i]);
					}
					isin=1;
					Path.flag=0;
					path.repaint();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
		}
		else if(e.getSource()==b2)
		{
			try {
				if(isin==1)
				{	
					t0=Double.parseDouble(t1.getText());
					tf=Double.parseDouble(t2.getText());;
					markov=Integer.valueOf(t3.getText()).intValue();
					alpha=Double.parseDouble(t4.getText());;
					kk=Double.parseDouble(t5.getText());;
					isrun=1;
					isstart=1;
					sa = new Thread(this);
					sa.start();				
				}
				else
				{
					ExceptionDialog d1 = new ExceptionDialog(this,"Exception","请先输入数据！");
				}
					
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if(e.getSource()==b3)
		{
			if(isstart==1)
			//path.repaint();
			isrun=0;
			else
			{
				ExceptionDialog d1 = new ExceptionDialog(this,"Exception","请先开始运行！");
			}
		}
		else if(e.getSource()==b4)
		{
			if(isstart==1)
			//path.repaint();
			isrun=1;
			else
			{
				ExceptionDialog d1 = new ExceptionDialog(this,"Exception","请先开始运行！");
			}
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TSP tsp = new TSP();
		tsp.setSize(800,500);
		tsp.setLocationRelativeTo(null);
		tsp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tsp.setVisible(true);	
	
	}


}

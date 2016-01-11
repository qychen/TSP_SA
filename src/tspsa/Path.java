package tspsa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Path extends JPanel {
	
	public static int flag=0;
	
	private int[] pic;
	
	public void paintComponent(Graphics g)
	{
		int i,x;
		int w=this.getWidth(),h=this.getHeight(),gap=(Math.min(w, h))/40;
		super.paintComponent(g);

		int size=(Math.min(w, h))/15;
		ImageIcon imgIcon = new ImageIcon("./image/p0.jpg");  
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		Image img0 = imgIcon.getImage();  
		imgIcon = new ImageIcon("./image/p1.jpg");
		size = size/2;
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		Image img1 = imgIcon.getImage();  
		imgIcon = new ImageIcon("./image/p2.jpg");
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		Image img2 = imgIcon.getImage();  
		imgIcon = new ImageIcon("./image/p3.jpg");
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		Image img3 = imgIcon.getImage();  
		imgIcon = new ImageIcon("./image/p4.jpg");
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
		Image img4 = imgIcon.getImage();  
		
		//g.setColor(Color.red);
		//g.drawOval((int)(TSP.cx[i]*w*0.9/TSP.cx[0]),(int)(TSP.cy[i]*h*0.9/TSP.cy[0]),11,11);

		if(flag==0)
		{
			pic = new int[TSP.n+1];
			for(i=2;i<=TSP.n;i++)
				pic[i]=(int)(Math.random()*4)+1;
			flag=1;
		}
		
		for(i=1;i<=TSP.n;i++)
			if(i==1)
			g.drawImage(img0, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
					(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
			else
			{
				x=pic[i];
				switch(x)
				{
				case 1: 
				{g.drawImage(img1, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
						(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
				break;
				}
				case 2: 
				{g.drawImage(img2, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
						(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
				break;
				}
				case 3: 
				{g.drawImage(img3, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
						(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
				break;
				}
				case 4: 
				{g.drawImage(img4, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
						(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
				break;
				}
				default: 
					g.drawImage(img4, (int)(TSP.cx[i]*w*0.9/TSP.cx[0]+gap),
							(int)(TSP.cy[i]*h*0.9/TSP.cy[0]+gap), null);
				}
				
			}
		if(TSP.isstart==1)
		{
			for(i=2;i<=TSP.n;i++)
			g.drawLine((int)(TSP.cx[TSP.cursolu[i-1]]*w*0.9/TSP.cx[0]+gap), 
					(int)(TSP.cy[TSP.cursolu[i-1]]*h*0.9/TSP.cy[0]+gap), 
					(int)(TSP.cx[TSP.cursolu[i]]*w*0.9/TSP.cx[0]+gap), 
					(int)(TSP.cy[TSP.cursolu[i]]*h*0.9/TSP.cy[0]+gap));
			g.drawLine((int)(TSP.cx[TSP.cursolu[TSP.n]]*w*0.9/TSP.cx[0]+gap), 
					(int)(TSP.cy[TSP.cursolu[TSP.n]]*h*0.9/TSP.cy[0]+gap), 
					(int)(TSP.cx[TSP.cursolu[1]]*w*0.9/TSP.cx[0]+gap), 
					(int)(TSP.cy[TSP.cursolu[1]]*h*0.9/TSP.cy[0]+gap));
		}
	}
	

}

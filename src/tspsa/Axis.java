package tspsa;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Axis extends JPanel {
	
	public void paintComponent(Graphics g)
	{
		int i=0;
		int w=this.getWidth(),h=this.getHeight();
		int o=(int)(Math.max(w, h)/20),ox,oy,num=0;
		ox=o;oy=h-o;
		double ym,gap;
		gap=(w-o-ox)/(TSP.t0-TSP.tf+1);
		
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawLine(ox, oy, w-o, oy);
		g.drawLine(ox, oy, ox, o);
		g.drawString("T0", ox, oy);
		g.drawString("Tf", (w-2*o), oy);
		g.drawString("Ecurrent", ox, (int)(o*1.5));
		
		if(TSP.isstart==1)
		{
			//xm=(int)((Math.log(TSP.tf/TSP.t0)/Math.log(TSP.alpha))*1.1);
			//g.drawOval(ox,(o+(int)(ym-TSP.E0)),1,1);
			/*
			if((int)TSP.Edraw[0]>=2)
			for(i=2;i<=(int)TSP.Edraw[0];i++)
				g.drawLine((i*gap+ox), (o+(int)(ym-TSP.Edraw[i])),
						((i-1)*gap+ox), (o+(int)(ym-TSP.Edraw[i-1])));
			else
				g.drawLine(ox,(o+(int)(ym-TSP.E0)),(gap+ox), (o+(int)(ym-TSP.Edraw[1])));
			*/
			ym=TSP.Edraw[1]*1.2;
				for(i=1;i<=(int)TSP.Edraw[0];i++)
				{
					g.setColor(Color.orange);
					g.fillOval((int)(i*gap+ox), (o+(int)((ym-TSP.Edraw[i])*(oy-o)/ym)),4,4);
					g.setColor(Color.blue);
					g.fillOval((int)(i*gap+ox), (o+(int)((ym-TSP.EE[i])*(oy-o)/ym)),4,4);
				}
				//g.setColor(Color.orange);
				//g.fillOval((int)(i*gap+ox), (o+(int)((ym-TSP.Edraw[i])*(oy-o)/ym)),5,5);
				//g.setColor(Color.blue);
				//g.fillOval((int)(i*gap+ox), (o+(int)((ym-TSP.EE[i])*(oy-o)/ym)),5,5);
				
		}

		
	}
}









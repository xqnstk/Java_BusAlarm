package BusAlarmScreen;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import Main.BusAlarm;
import java.awt.Color;

public class BusPanel extends JPanel {

	BusAlarm busalarm;

	ImageIcon icmainScreen = new ImageIcon(this.getClass().getResource("/MainScreen2.jpg"));
	ImageIcon icbusIcon = new ImageIcon(this.getClass().getResource("/BusIcon.png"));

	ImageIcon icbusRoad_green = new ImageIcon(this.getClass().getResource("/busRoad_green.png"));
	ImageIcon icbusRoad_yellow = new ImageIcon(this.getClass().getResource("/busRoad_yelllow.png"));
	ImageIcon icbusRoad_red = new ImageIcon(this.getClass().getResource("/busRoad_red.png"));
	ImageIcon icbusStop = new ImageIcon(this.getClass().getResource("/busStopButton.png"));
	ImageIcon icbusStopSelect = new ImageIcon(this.getClass().getResource("/busStopButtonSelect.png"));
	
	JLabel label;
	JLabel lbbusroad_green;
	JLabel lbbusroad_yellow;
	JButton bbusStop[][] = new JButton[13][10];
	JLabel lbbusStop[][]=new JLabel[13][10];
	JLabel jlabel;
	
	int i=0, j=0;
	int count=0;
	//BusAPI busapi=new BusAPI();
	
	public BusPanel(BusAlarm busalarm) {
		super(true);
		setLayout(null);
		setBackground(new Color(255,243,225));
		label = new JLabel();
		label.setBackground(Color.YELLOW);
		label.setBounds(0, 0, 1280, 720);
		
		lbbusroad_green=new JLabel(icbusRoad_green);
		lbbusroad_green.setBounds(95,200,120,12);

		JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL,400, 100, 0, 800);
		vbar.setBounds(1245, 0, 26, 686);
		vbar.addAdjustmentListener(new MyAdjustmentListener());

		add(vbar);
		add(label);		
		

		for(i=0; i<13;i++)
		{
			for(j=0; j<10;j++)
			{
				lbbusStop[i][j]=new JLabel();
			//	lbbusStop[i][j].setText(busapi.GetBusStopInfo(count)); count++;
				lbbusStop[i][j].setBounds(115*j+77,110*i+200,110,80);
				add(lbbusStop[i][j]);
				
				bbusStop[i][j]=new JButton(icbusStop);
				bbusStop[i][j].setBounds(115*j+93, 110*i+198, 16, 16);
				BusAlarm.setButton(bbusStop[i][j]);
				add(bbusStop[i][j]);
			}
		}
		add(lbbusroad_green);		
	}

	class MyAdjustmentListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
		for(int i=0; i<13;i++)
		{
			for(int j=0; j<10;j++)
			{
				lbbusStop[i][j].setLocation(115*j+77,e.getValue()-(-110*i+196));
				add(lbbusStop[i][j]);
				bbusStop[i][j].setLocation(115*j+93, e.getValue()-(-110*i+198));
				BusAlarm.setButton(bbusStop[i][j]);
				add(bbusStop[i][j]);
			}
		}
				
			lbbusroad_green.setLocation(95,e.getValue()-196);
			add(lbbusroad_green);
				
			
			label.setText(" New Value is " + e.getValue() + " ");
			repaint();
		}
	}


}

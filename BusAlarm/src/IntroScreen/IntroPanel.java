package IntroScreen;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.BusAlarm;
import Main.BusAlarm;

public class IntroPanel extends JPanel {

	BusAlarm busalarm;
	ImageIcon imgIntroScreen = new ImageIcon(this.getClass().getResource("/IntroScreen1.jpg"));
	ImageIcon icIntroSelect = new ImageIcon(this.getClass().getResource("/IntroSelect.png"));
	ImageIcon icIntroNonSelect = new ImageIcon(this.getClass().getResource("/IntroNonSelect.png"));
	ImageIcon icStartButton = new ImageIcon(this.getClass().getResource("/StartButton.png"));
	ImageIcon icStartButtonSelect = new ImageIcon(this.getClass().getResource("/StartButtonSelect.png"));
	
	JLabel lbIntroScreen=new JLabel(imgIntroScreen);
	JButton bIntroButton1 = new JButton(icIntroSelect);
	JButton bIntroButton2 = new JButton(icIntroNonSelect);
	JButton bIntroButton3 = new JButton(icIntroNonSelect);
	JButton bStartButton = new JButton(icStartButton);
	
	public IntroPanel(BusAlarm busalarm) {
		
		this.busalarm= busalarm;
		setLayout(null);
		
		bIntroButton1.setSize(30,30); //ũ�� ����
		bIntroButton2.setSize(30,30);
		bIntroButton3.setSize(30,30);
		
		bIntroButton1.setLocation(BusAlarm.SCREEN_W/2-85, 450); //��ġ ����
		bIntroButton2.setLocation(BusAlarm.SCREEN_W/2-15, 450);
		bIntroButton3.setLocation(BusAlarm.SCREEN_W/2+55, 450);
		
		introButton(bIntroButton1,bIntroButton2,bIntroButton3); // ��ư�� �Լ�
		introButton(bIntroButton2,bIntroButton1,bIntroButton3);
		introButton(bIntroButton3,bIntroButton1,bIntroButton2);
		
		bStartButton.setIcon(icStartButton); //�����ϱ� ��ư
		bStartButton.setSize(500, 80);
		bStartButton.setLocation(BusAlarm.SCREEN_W/2-250,520);
		BusAlarm.setButton(bStartButton);
		bStartButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){ //Ŀ���� ��ư �ȿ� ���� ��
				bStartButton.setIcon(icStartButtonSelect); //���� ���ϰ�
			}
			public void mouseExited(MouseEvent e) { //Ŀ���� �ٱ��� ���� ��
				bStartButton.setIcon(icStartButton); //���� ���ϰ�
			}
			public void mousePressed(MouseEvent e) { //��ư�� ������ ��
				busalarm.change("busalarmpanel"); //ȭ�� ��ȯ

			}
		});
		add(bStartButton);
		
		lbIntroScreen.setSize(1280,720); 
		lbIntroScreen.setLocation(0, 0);
		add(lbIntroScreen);
	}

	public void introButton(JButton select,JButton nselect1, JButton nselect2)
	{
		BusAlarm.setButton(select); //���� ���� ��ư
		select.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				select.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				select.setIcon(icIntroSelect);
				nselect1.setIcon(icIntroNonSelect);
				nselect2.setIcon(icIntroNonSelect);
				if(select==bIntroButton1){ //ù��° ��ư 
					imgIntroScreen = new ImageIcon(this.getClass().getResource("/IntroScreen1.jpg"));
					lbIntroScreen.setIcon(imgIntroScreen);
				}
				else if(select==bIntroButton2){ //�ι�° ��ư
					imgIntroScreen = new ImageIcon(this.getClass().getResource("/IntroScreen2.jpg"));
					lbIntroScreen.setIcon(imgIntroScreen);
				}
				else if(select ==bIntroButton3){ //����° ��ư
				}
			}
		});
		add(select);
	}
	
	

}

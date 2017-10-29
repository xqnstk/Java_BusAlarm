package BusAlarmScreen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import Main.BusAlarm;

public class BusPanel extends JPanel implements Runnable, ActionListener {

	BusAlarm busalarm;

	Image imgbusicon = new ImageIcon(this.getClass().getResource("/BusIcon.png")).getImage();
	ImageIcon icmainScreen = new ImageIcon(this.getClass().getResource("/MainScreen2.jpg"));
	ImageIcon icbusIcon = new ImageIcon(this.getClass().getResource("/BusIcon.png"));
	ImageIcon icbusRoad_green = new ImageIcon(this.getClass().getResource("/busRoad_green.png"));
	ImageIcon icbusRoad_yellow = new ImageIcon(this.getClass().getResource("/busRoad_yelllow.png"));
	ImageIcon icbusRoad_red = new ImageIcon(this.getClass().getResource("/busRoad_red.png"));
	ImageIcon icbusStop = new ImageIcon(this.getClass().getResource("/busStopButton.png"));
	ImageIcon icbusStopSelect = new ImageIcon(this.getClass().getResource("/busStopButtonSelect.png"));
	ImageIcon icmainScreenBar = new ImageIcon(this.getClass().getResource("/MenuBar.png"));
	ImageIcon icfold = new ImageIcon(this.getClass().getResource("/foldbutton2.png"));
	ImageIcon icbusRoad;
	ImageIcon icbusSeat= new ImageIcon(this.getClass().getResource("/BusSeat.png"));
	ImageIcon icseated= new ImageIcon(this.getClass().getResource("/seated.png"));

	JLabel lbbusInfo;
	JLabel label;
	JButton bbusRoad[][] = new JButton[13][10];
	JButton bbusStop[][] = new JButton[13][10];
	JLabel lbbusStop[][] = new JLabel[13][10];
	JLabel lbmainScreenBar = new JLabel(icmainScreenBar);
	JButton bfoldButton = new JButton(icfold);

	Calendar calendar1 = Calendar.getInstance();
	int year = calendar1.get(Calendar.YEAR);
	int month = calendar1.get(Calendar.MONTH);
	int day = calendar1.get(Calendar.DAY_OF_MONTH);
	int hour = calendar1.get(Calendar.HOUR_OF_DAY);
	int min = calendar1.get(Calendar.MINUTE);
	int sec = calendar1.get(Calendar.SECOND);
	Timer timer;
	JLabel lbPresent;
	boolean menubarVisible = true;
	static int time;

	int i = 0, j = 0, count=0;
	BusAPI busapi = new BusAPI();
	int gap, linecnt1=0, bus_speed=1, many; //버스 개수, 홀/짝수줄 인식, 버스 속도, 한 줄 버스 개수
	int busgap[] = new int[3]; //버스 간 간격
	int x, y, busStop_x, busStop_y; // 좌표
	int list1_x[] = { 34, 211, 34, 211, 34, 211, 34, 211, 34, 211, 34, 80, 172, 214, 34, 211, 34, 80, 172, 214, 34, 211, 34, 80, 126, 172, 211 };
	int list1_y[] = { 103, 103, 148, 148, 194, 194, 240, 240, 286, 286, 353, 353, 353, 353, 397, 397, 397, 397, 440, 440, 484, 484, 484, 484, 484 };

	Thread th;
	ArrayList Bus_List = new ArrayList();
	ArrayList BusStop_List = new ArrayList();
	Bus bus; // 버스 접근 키
	BusStop[][] busStop=new BusStop[13][10];
	BusAlarmScreen.DBBus dbbus = new BusAlarmScreen.DBBus();
	
	ActionListener busListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof BusStop) { //정류장 클릭
				final Frame frbusStop = new Frame("busStop");
				JPanel p= new JPanel();
				JLabel l=new JLabel("랄랄랄");
				p.setBackground(Color.WHITE);
				p.add(l);
				frbusStop.add(p);
				frbusStop.setVisible(true);
				frbusStop.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						frbusStop.setVisible(false);
						frbusStop.dispose();
					}
				});
				frbusStop.setSize(400, 250);
				frbusStop.setLocation(200, 200);
				frbusStop.setResizable(false);
				frbusStop.setLocationRelativeTo(null);
			}
			if (e.getSource() instanceof Bus) { //버스 클릭
				final Frame frbusStop = new Frame("Bus");
				String low_floor;
				JPanel p =new JPanel();
				p.setLayout(null);
				p.setBackground(Color.WHITE);

				JLabel lbseated1 = new JLabel(icseated);
				lbseated1.setBounds(list1_x[bus.xy], list1_y[bus.xy], 47, 40);
				p.add(lbseated1);
				
				JLabel lbbusSeat = new JLabel(icbusSeat);
				lbbusSeat.setBounds(15,10,263,523);
				p.add(lbbusSeat);
				p.setLayout(getLayout());
				
				JLabel l=new JLabel("현재 승객수 : "+Integer.toString(bus.busPassenger));
				JLabel info=new JLabel("버스 번호 : "+bus.bnum + ", 저상 여부 : " +bus.bfloor + ", 좌석 총 개수 : " + bus.bseat+"개");

				l.setBounds(19, 530, 10, 30);
				info.setBounds(19, 540, 260, 530);
				p.add(l);
				p.add(info);



				if(DBBus.low_floor_bus == 1) {
					low_floor = "저상버스";
				}
				else {
					low_floor="지상버스";
				}
				
				frbusStop.add(p);
				frbusStop.setVisible(true);
				frbusStop.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						frbusStop.setVisible(false);
						frbusStop.dispose();
					}
				});
				frbusStop.setSize(300, 630);
				frbusStop.setLocation(200, 200);
				frbusStop.setResizable(false);
				frbusStop.setLocationRelativeTo(null);
			}
		}
	};

	public BusPanel(BusAlarm busalarm) {

		super(true);
		setLayout(null);
		setBackground(new Color(255, 243, 225));
		label = new JLabel();
		label.setBackground(Color.YELLOW);
		label.setBounds(0, 0, 1280, 720);

		JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 1000, 80, 0, 1500);
		vbar.setBounds(1245, 0, 26, 686);
		vbar.addAdjustmentListener(new MyAdjustmentListener());
		add(vbar);
		add(label);

		lbPresent = new JLabel(year + "-" + month + "-" + day + "   " + hour + ":" + min + ":" + sec,
				SwingConstants.RIGHT);
		lbPresent.setVerticalAlignment(SwingConstants.TOP);
		lbPresent.setBounds(1010, 5, 220, 40);
		lbPresent.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		add(lbPresent);

		timer = new Timer(1000, this);
		timer.setInitialDelay(0);
		timer.start();

		for (i = 0; i < 13; i++) {
			for (j = 0; j < 10; j++) {
				lbbusStop[i][j] = new JLabel();
				lbbusStop[i][j].setText("정류장");// busapi.GetBusStopInfo(count)
				count++;
				lbbusStop[i][j].setBounds(115 * j + 77, 140 * i + 226, 110, 60);
				add(lbbusStop[i][j]);

				busStop[i][j] = new BusStop(busStop_x, busStop_y);
				busStop[i][j].setIcon(icbusStop);
				busStop[i][j].setBounds(115 * j + 93, 140 * i + 220, 16, 16);
				BusAlarm.setButton(busStop[i][j]);
				add(busStop[i][j]);

				busStop[i][j].addActionListener(busListener);
			}
			
			for (j = 0; j < 9; j++) {
				int randomRoad = (int) (Math.random() * 3);

				if (randomRoad == 0) {
					icbusRoad = icbusRoad_green;
				} else if (randomRoad == 1) {
					icbusRoad = icbusRoad_red;
				} else {
					icbusRoad = icbusRoad_yellow;
				}
				
				bbusRoad[i][j] = new JButton(icbusRoad);
				bbusRoad[i][j].setBounds(115 * j + 96, 140 * i + 220, 120, 12);
				BusAlarm.setButton(bbusRoad[i][j]);
				add(bbusRoad[i][j]);
			}
		}
		addMenu();

		init();
		start();
	}

	class MyAdjustmentListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {

			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 10; j++) {
					lbbusStop[i][j].setLocation(115 * j + 77, e.getValue() - (-140 * i + 774));
					add(lbbusStop[i][j]);

					busStop[i][j].setPos(115 * j + 93, e.getValue() - (-140 * i + 780));
					busStop[i][j].setLocation(115 * j + 93, e.getValue() - (-140 * i + 780));
					BusAlarm.setButton(busStop[i][j]);
					add(busStop[i][j]);
				}
				for (int j = 0; j < 9; j++) {
					bbusRoad[i][j].setLocation(115 * j + 96, e.getValue() - (-140 * i + 780));
					BusAlarm.setButton(bbusRoad[i][j]);
					add(bbusRoad[i][j]);
				}
			}
			repaint();
		}
	}

	public void actionPerformed(ActionEvent e) {
		++sec;
		Calendar calendar2 = Calendar.getInstance();
		year = calendar2.get(Calendar.YEAR);
		month = calendar2.get(Calendar.MONTH);
		day = calendar2.get(Calendar.DAY_OF_MONTH);
		hour = calendar2.get(Calendar.HOUR_OF_DAY);
		min = calendar2.get(Calendar.MINUTE);
		sec = calendar2.get(Calendar.SECOND);
		lbPresent.setText(year + "-" + month + "-" + day + "   " + hour + ":" + min + ":" + sec);
	}

	public void init() { // 편의를 위해 init 에서 기본적인 세팅을 합니다.
		x = 100;
		y = 100;
		busStop_x = 85;
		busStop_y = 85;
	}

	public void start() {
		th = new Thread(this);
		th.start();
	}

	public void BusGap() { //시간대별로 버스 총 개수 정하기
		if((7<=hour && hour<=9) || (12<=hour && hour<=14) || (18<=hour && hour<=20)) { //버스 많을 시간(아침, 점심, 저녁)
			gap=40;
		}
		else if((5<=hour&& hour<=6) || (22<=hour && hour<=24)) { //없음 
			gap=20;
		}
		else { //평균
			gap=30;
		}
	}
	public void changexy() { //x, y좌표 주기
		x=10;
		y+=140;
		many = (int)((Math.random()*2)+2);

		for(i=0; i<many; i++) { //한 줄에 버스 2, 3개정도
			if(many == 3) {
				busgap[0]=(int)((Math.random()*300)+100); 
				busgap[1]=(int)((Math.random()*300)+400); 
				busgap[2]=(int)((Math.random()*300)+700); 
			}
			else if(many == 2) {
				busgap[0]=(int)((Math.random()*300)+100); 
				busgap[1]=(int)((Math.random()*500)+500); 
			}
			x=busgap[i];
			BusProcess();
			if(linecnt1%2==0) { //뒤로 가기
				bus.bus_speed=-bus_speed;
			}
		}
		linecnt1++;
		
	}
	
	public void run() {
		try {
			BusGap(); //버스 몇 대 세팅해놀지 시간별로 정해줌
			
			for(int i=0; i<gap; i++) { //버스 찍어내기		
				changexy(); //좌표값 주기
			}
			
			x=93; y=100; //원래 버스 생성 좌표 주기
		
			while (true) {
				BusProcess();
				repaint();	

				for (int i = 0; i < Bus_List.size(); ++i) {	
					bus = (Bus) (Bus_List.get(i));
					bus.move();	
					
					bus.setBounds(bus.pos.x, bus.pos.y + 15 + lbbusStop[1][1].getY() - 330, 48, 65);
					add(bus);				
				}
				Thread.sleep(70);
				time++;
			}			
		} catch (Exception e) {
		}
	}

	public void BusProcess() { // 버스 처리 메소드
		if (time % 300 == 0) {
			DBBus.insertDB();
			bus = new Bus(x, y); // 좌표 체크하여 넘기기
			bus.bnum=dbbus.bus_num;
			bus.bfloor=dbbus.low_floor_bus;
			bus.bseat=dbbus.seat_num;
			Bus_List.add(bus); // 버스 추가
			bus.setIcon(icbusIcon);
			bus.setBounds(bus.pos.x, bus.pos.y + 15 + lbbusStop[1][1].getY() - 330, 48, 65);
			BusAlarm.setButton(bus);
			bus.addActionListener(busListener);
			add(bus);
			bus.where();

		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < Bus_List.size(); ++i) {
			bus = (Bus) (Bus_List.get(i));
			g.drawImage(imgbusicon, bus.pos.x, bus.pos.y + 15 + lbbusStop[1][1].getY() - 330, this);
			bus.move();
		}
	}

	public void addMenu() {
		// 메뉴바
		lbbusInfo = new JLabel();
		lbbusInfo.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 18));
		lbbusInfo.setText("버스정보");// busapi.GetBusInfo()
		lbbusInfo.setSize(500, 100);
		lbbusInfo.setLocation(0, 0);
		add(lbbusInfo);

		lbmainScreenBar.setSize(1280, 120);
		lbmainScreenBar.setLocation(0, 0);
		add(lbmainScreenBar);

		bfoldButton.setIcon(icfold);
		bfoldButton.setSize(90, 26);
		bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 120);
		BusAlarm.setButton(bfoldButton);
		
		bfoldButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if (menubarVisible == false) {
					lbmainScreenBar.setVisible(true);
					lbbusInfo.setVisible(true);
					menubarVisible = true;
					bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 120);
				} else {
					lbmainScreenBar.setVisible(false);
					lbbusInfo.setVisible(false);
					menubarVisible = false;
					bfoldButton.setLocation(BusAlarm.SCREEN_W / 2 - 45, 0);
				}
			}
		});
		add(bfoldButton);
	}
}

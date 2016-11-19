package midFidelty;


import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Component;

import java.util.Date;
import java.util.Calendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.awt.SystemColor;
import java.awt.Font;


public class Home {

	private JFrame frmPerAnal;
	private Connection cn;
	private String[] apps;
	private String[] wins;
	private JTextArea acitiveWindow;
	public static void main(String[] args) throws InterruptedException {
		
					
					Home window = new Home();
					window.frmPerAnal.setVisible(true);
					
					activeWindow();
				
	}
	
	
	public Home(){
		initializeFrame();
		openWindows();
		connectDB();
		initializeMenu();
		initializePanes();
		
	}
	
	
	public static void activeWindow() throws InterruptedException{
		char[] buffer = new char[100];
		while(true){
	        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
	        User32.INSTANCE.GetWindowText(hwnd, buffer, 100);
	        System.out.println("Active window title: " + Native.toString(buffer));
	        Thread.sleep(5000);
	        
		}
	}
		
		
	private void openWindows(){

		String[] a = new String[100];
		String[] w = new String[100];
		
		try {
			String process;
			
			int i=0;
			Process p = Runtime.getRuntime().exec("tasklist /v /fo csv /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.println(input.read());// total number of lines
			while ((process = input.readLine()) != null) {
				process = process.replaceAll("\"", "");
				String[]str = process.split(",");
				if(str.length == 11){
					if(!str[10].equals("N/A")){
						System.out.println(str[0]+"__"+str[10]);
						a[i] = str[0];
						w[i] = str[10];
					}	
				}
				else if(str.length == 10){
					if(!str[9].equals("N/A")){
						System.out.println(str[0]+"__"+str[9]);
						a[i] = str[0];
						w[i] = str[9];
					}	
				}
				else if(str.length==9){
					if(!str[8].equals("N/A")){
						System.out.println(str[0]+"__"+str[8]);
						a[i] = str[0];
						w[i] = str[8];
					}
				}
					i++;				
			}
			input.close();
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		int count=0;
		for(int j=0;j<a.length;j++){
			if(a[j]!=null){
				count++;
			}
		}
		String[]ap = new String[count];
		String[]win = new String[count];
		count=0;
		for(int j=0;j<a.length;j++){
			if(a[j]!=null){
				ap[count]=a[j];
				win[count]=w[j];
				count++;
			}
		}
		apps = ap;
		wins = win;
		
	
	}
	private void initializeFrame() {
		frmPerAnal = new JFrame();
		frmPerAnal.getContentPane().setBackground(SystemColor.menu);
		frmPerAnal.setTitle("Personal Analytics");
		frmPerAnal.setResizable(false);
		frmPerAnal.setIconImage(Toolkit.getDefaultToolkit().getImage("person.ico"));
		frmPerAnal.setBounds(100, 100, 750, 500);
		frmPerAnal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void connectDB(){
		cn=null;
		Connector c = new Connector();
		cn = c.connectDataBase();
		System.out.println(cn);
	}
	
	private void initializeMenu(){	
		JMenuBar menuBar = new JMenuBar();
		frmPerAnal.setJMenuBar(menuBar);
		
		JMenu mnAction = new JMenu("Action");
		menuBar.add(mnAction);
		
		JMenu mnGenrateGraph = new JMenu("Genrate Graph");
		mnAction.add(mnGenrateGraph);
		
		JMenuItem mntmBarChart = new JMenuItem("Bar Chart");
		mnGenrateGraph.add(mntmBarChart);
		
		JMenuItem mntmPieChart = new JMenuItem("Pie Chart");
		mnGenrateGraph.add(mntmPieChart);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("List");
		mnGenrateGraph.add(mntmNewMenuItem);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mnAction.add(mntmImport);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnAction.add(mntmExport);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				frmPerAnal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				System.exit(0);
			}
		});
		mnAction.add(mntmExit);
		
		
		JMenu mnSetting = new JMenu("Setting");
		menuBar.add(mnSetting);
		
		JMenuItem mntmDisplay = new JMenuItem("Display");
		mnSetting.add(mntmDisplay);
		
		JMenuItem mntmLogs = new JMenuItem("Logs");
		mnSetting.add(mntmLogs);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu("About");
		mnHelp.add(mnAbout);
		
		JMenuItem mntmApplication = new JMenuItem("Application");
		mnAbout.add(mntmApplication);
		
		JMenuItem mntmGraph = new JMenuItem("Graph");
		mnAbout.add(mntmGraph);
		
		JMenuItem mntmDevelopers = new JMenuItem("Developers");
		mnAbout.add(mntmDevelopers);
		
		JMenuItem mntmTips = new JMenuItem("Tips");
		mnHelp.add(mntmTips);
		frmPerAnal.getContentPane().setLayout(null);
	}
	
	
	private void initializePanes(){
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 21, 724, 419);
		frmPerAnal.getContentPane().add(tabbedPane);
		
		initializeHomePane(tabbedPane);
		initializeOpenWindowPane(tabbedPane);
		initializeReportPane(tabbedPane);
	}
	
	
	public void initializeHomePane(JTabbedPane tabbedPane){
		JPanel homePanel = new JPanel();
		homePanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		tabbedPane.addTab("Home", null, homePanel, null);
		homePanel.setLayout(null);
		
		
		JLabel homeLogo = new JLabel("");
		homePanel.add(homeLogo);
		Image img = new ImageIcon(this.getClass().getResource("/personal.png")).getImage();
		homeLogo.setIcon(new ImageIcon(img));
		homeLogo.setBounds(305, 146, 128, 128);
		homePanel.add(homeLogo);
		
		
	}
	
	public void initializeOpenWindowPane(JTabbedPane tabbedPane){
		JPanel openWindowPanel = new JPanel();
		openWindowPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		
		
		tabbedPane.addTab("Open Windows", null, openWindowPanel, null);
		openWindowPanel.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 43, 699, 335);
		openWindowPanel.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		acitiveWindow = new JTextArea();
		acitiveWindow.setEditable(false);
		acitiveWindow.setBackground(new Color(255, 255, 204));
		acitiveWindow.setForeground(Color.BLACK);
		Font myFont = new Font("Calibre", Font.BOLD, 15);
		acitiveWindow.setFont(myFont);
		acitiveWindow.setForeground(Color.BLUE);
		scrollPane.setViewportView(acitiveWindow);
		
		JPanel CurrentApps = new JPanel();
		CurrentApps.setBorder(UIManager.getBorder("CheckBox.border"));
		CurrentApps.setBackground(new Color(255, 255, 204));
		splitPane.setLeftComponent(CurrentApps);
		GridBagLayout gbl_CurrentApps = new GridBagLayout();
		gbl_CurrentApps.columnWidths = new int[]{89, 0};
		gbl_CurrentApps.rowHeights = new int[]{14, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_CurrentApps.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_CurrentApps.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		CurrentApps.setLayout(gbl_CurrentApps);
		
		// Current Apps start	
		for(int i=0;i<apps.length-1;i++){
			JLabel appLabel = new JLabel(apps[i]);
			appLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			appLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String temp="";
					String label = appLabel.getText();
					for(int i=0;i<apps.length;i++){
						if(label==apps[i]){
							temp = wins[i]+"\n";
						}
					}
					acitiveWindow.setText(temp);
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
					appLabel.setForeground(Color.GRAY);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					appLabel.setForeground(Color.BLACK);
				}
			});
			
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 0;
			gbc_label.gridy = i;
			CurrentApps.add(appLabel, gbc_label);
		}
		
		//Current Apps end
			
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_1.setBounds(679, 5, 30, 30);
		openWindowPanel.add(button_1);
		Image img2 = new ImageIcon(this.getClass().getResource("/refresh.png")).getImage();
		button_1.setIcon(new ImageIcon(img2));
		
		JLabel lblCurrentApp = new JLabel("Current Apps");
		lblCurrentApp.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCurrentApp.setBounds(10, 18, 90, 14);
		openWindowPanel.add(lblCurrentApp);
		
		JLabel lblActiveWindow = new JLabel("Active Window");
		lblActiveWindow.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblActiveWindow.setBounds(328, 18, 105, 14);
		openWindowPanel.add(lblActiveWindow);
		
		
	}

	
	public void initializeReportPane(JTabbedPane tabbedPane){
		JPanel reportPanel = new JPanel();
		reportPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		
		reportPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabbedPane.addTab("Reports", null, reportPanel, null);
		tabbedPane.setEnabledAt(2, true);
		reportPanel.setLayout(null);
		
		
		JTabbedPane tabbedPane2 = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane2.setBounds(10, 45, 699, 333);
		reportPanel.add(tabbedPane2);
		
		JLayeredPane PieLayeredPane = new JLayeredPane();
		PieLayeredPane.setBackground(new Color(255, 255, 204));
		tabbedPane2.addTab("Pie Chart", null, PieLayeredPane, null);
		tabbedPane2.setBackgroundAt(0, new Color(255, 255, 255));
		GridBagLayout gbl_PieLayeredPane = new GridBagLayout();
		gbl_PieLayeredPane.columnWidths = new int[]{0};
		gbl_PieLayeredPane.rowHeights = new int[]{0};
		gbl_PieLayeredPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_PieLayeredPane.rowWeights = new double[]{Double.MIN_VALUE};
		PieLayeredPane.setLayout(gbl_PieLayeredPane);
		
		JLayeredPane barLayeredPane = new JLayeredPane();
		barLayeredPane.setBackground(new Color(255, 255, 204));
		tabbedPane2.addTab("Bar Chart", null, barLayeredPane, null);
		tabbedPane2.setBackgroundAt(1, new Color(255, 255, 255));
		GridBagLayout gbl_barLayeredPane = new GridBagLayout();
		gbl_barLayeredPane.columnWidths = new int[]{0};
		gbl_barLayeredPane.rowHeights = new int[]{0};
		gbl_barLayeredPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_barLayeredPane.rowWeights = new double[]{Double.MIN_VALUE};
		barLayeredPane.setLayout(gbl_barLayeredPane);
	
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(new Color(255, 255, 204));
		tabbedPane2.addTab("Other", null, layeredPane, null);
		tabbedPane2.setBackgroundAt(2, new Color(255, 255, 255));
		layeredPane.setLayout(null);
		
		JLabel lblDate = new JLabel("Day:");
		lblDate.setBounds(475, 17, 25, 14);
		reportPanel.add(lblDate);
		
		JSpinner daySpinner = new JSpinner();
		daySpinner.setModel(new SpinnerDateModel(new Date(1479092400000L), new Date(1479092400000L), null, Calendar.DAY_OF_WEEK_IN_MONTH));
		daySpinner.setBounds(510, 14, 141, 20);
		reportPanel.add(daySpinner);
		
		JButton button = new JButton("");
		button.setBounds(679, 5, 30, 30);
		reportPanel.add(button);
		
		Image img2 = new ImageIcon(this.getClass().getResource("/refresh.png")).getImage();
		button.setIcon(new ImageIcon(img2));
	
	}
}
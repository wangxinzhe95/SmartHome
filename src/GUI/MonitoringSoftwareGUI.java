package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.SmartElectricityMeter;
import Controller.SmartGasMeter;
import Controller.SmartMonitoringSoftware;

/**Title: MonitoringSoftwareGUI 
* Description: This is MonitoringSoftwareGUI class.
* This class is used to generate user information interface. And the user can view
* daily or history consumption and costs, check tariff and send meter readings to
* energy provider.
* @author  Group 18
* @version 1.0
*/
public class MonitoringSoftwareGUI {
	JFrame frame;
	JTextField field1, field2, field3, field4;
	JButton setElectronicBudget, setGasBudget;
	JButton viewHistory, checkTariff, sendButton, backToLogin;
	String time = null;
	SmartMonitoringSoftware software;
	Timer timer;

	public MonitoringSoftwareGUI(SmartMonitoringSoftware software) {
		this.software = software;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 800, 300);

		JPanel panel1 = new JPanel(new GridLayout(6, 2, 200, 0));
		JLabel label1 = new JLabel("electricity consumption and cost: ");
		JLabel label2 = new JLabel("gas consumption and cost: ");
		field1 = new JTextField();
		field1.setEditable(false);
		field2 = new JTextField();
		field2.setEditable(false);

		setElectronicBudget = new JButton("set electronic budget");
		setGasBudget = new JButton("set gas budget");

		setElectronicBudget.addActionListener(new ElectronicActionListener());
		setGasBudget.addActionListener(new GasActionListener());

		viewHistory = new JButton("view history");
		checkTariff = new JButton("check tariff");
		sendButton = new JButton("send meter readings");
		backToLogin = new JButton("Exit Login");

		viewHistory.addActionListener(new ViewHistoryActionListener());
		checkTariff.addActionListener(new CheckTariffActionListener());
		sendButton.addActionListener(new SendToActionListener());
		backToLogin.addActionListener(new BackToLoginActionListener());

		JLabel label3 = new JLabel("electricity budget: ");
		JLabel label4 = new JLabel("gas budget: ");
		field3 = new JTextField();
		field3.setText(software.getElectronicBudgetValue() + software.getElectronicBudgetType());
		field3.setEditable(false);
		field4 = new JTextField();
		field4.setText(software.getGasBudgetValue() + software.getGasBudgetType());
		field4.setEditable(false);

		ElectronicPanel panel3 = new ElectronicPanel();
		GasPanel panel4 = new GasPanel();

		panel1.add(label1);
		panel1.add(label2);
		panel1.add(field1);
		panel1.add(field2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.add(field3);
		panel1.add(field4);
		panel1.add(panel3);
		panel1.add(panel4);
		panel1.add(setElectronicBudget);
		panel1.add(setGasBudget);

		JPanel panel2 = new JPanel(new GridLayout(1, 4, 20, 0));

		panel2.add(viewHistory);
		panel2.add(checkTariff);
		panel2.add(sendButton);
		panel2.add(backToLogin);

		frame.getContentPane().add(BorderLayout.NORTH, panel1);
		frame.getContentPane().add(BorderLayout.SOUTH, panel2);
		frame.setVisible(true);

		//This is the timed task
		timer = new Timer();
		timer.schedule(new RemindTaskElectronic(), 0, 1000);
		timer.schedule(new RemindTaskGas(), 0, 1000);
		timer.schedule(new ElectronicBudget(), 0, 1000);
		timer.schedule(new GasBudget(), 0, 1000);

	}

	/**
	 * This method is used to obtain the current time in yyyy-MM-dd format.
     * @return the current time in String.
	 */
	public String getTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = (Date) calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		time = format.format(date);
		return time;
	}

	/**Title: RemindTaskElectronic 
	* Description: This is an inner class called RemindTaskElectronic which extends TimerTask class.
	* This class is used to get daily electronic consumption and costs periodically.
	* @author  Group 18
	* @version 1.0
	*/ 
	private class RemindTaskElectronic extends TimerTask {
		public void run() {
			DecimalFormat df = new DecimalFormat("#.00");
			SmartElectricityMeter electronicMeter = software.getElectronicMeter();
			electronicMeter.increaseUsage(Math.random());
			field1.setText("usage: " + electronicMeter.getDailyUsage(getTime()) + "kWh" + " | " + "cost: "
					+ df.format(electronicMeter.getDailyCost(getTime())) + "pounds");
		}
	}

	/**Title: RemindTaskGas
	* Description: This is an inner class called RemindTaskGas which extends TimerTask class.
	* This class is used to get daily gas consumption and costs periodically.
	* @author  Group 18
	* @version 1.0
	*/ 
	private class RemindTaskGas extends TimerTask {
		public void run() {
			DecimalFormat df = new DecimalFormat("#.00");
			SmartGasMeter gasMeter = software.getGasMeter();
			gasMeter.increaseUsage(Math.random());
			field2.setText("usage: " + gasMeter.getDailyUsage(getTime()) + "kWh" + " | " + "cost: "
					+ df.format(gasMeter.getDailyCost(getTime())) + "pounds");
		}
	}

	/**Title: ElectronicBudget 
	* Description: This is an inner class called ElectronicBudget which extends TimerTask class.
	* This class is used to re-paint the whole panel periodically to show the alert information.
	* @author  Group 18
	* @version 1.0
	*/ 
	private class ElectronicBudget extends TimerTask {
		public void run() {
			frame.repaint();
		}
	}

	/**Title: GasBudget 
	* Description: This is an inner class called GasBudget which extends TimerTask class.
	* This class is used to re-paint the whole panel periodically to show the alert information.
	* @author  Group 18
	* @version 1.0
	*/ 
	private class GasBudget extends TimerTask {
		public void run() {
			frame.repaint();
		}
	}

	/**Title: ElectronicActionListener 
	* Description: This is an inner class called ElectronicActionListener.
	* This class is used to deal with the event after the "set electronic budget" button has been clicked. 
	* It will show an interface to choose the type of electronic budget which user want to set, and user 
	* can input the electronic budget value.
	* @author  Group 18
	* @version 1.0
	*/ 
	class ElectronicActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int type = 0;// 0代表电量消耗，1代表电费
			String showMessage;
			String unit;
			String[] options = { "electronic consumption", "electronic cost" };
			String budget = (String) JOptionPane.showInputDialog(null, "Select the budget type", "Budget Type",
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (budget != null) {
				if (budget.equals(options[0])) {
					type = 0;
					showMessage = "Please input the electronic consumption budget(kWh)";
					unit = "kWh";
				} else {
					type = 1;
					showMessage = "Please input the electronic cost budget(pounds)";
					unit = "pounds";
				}
				String electronicBudget = JOptionPane.showInputDialog(null, showMessage);
				if (software.setElectronicBudget(electronicBudget, type)) {
					field3.setText(software.getElectronicBudgetValue() + unit);
					frame.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Please input the legal value!");
				}
			}
		}
	}

	/**Title: GasActionListener 
	* Description: This is an inner class called GasActionListener.
	* This class is used to deal with the event after the "set gas budget" button has been clicked. 
	* It will show an interface to choose the type of gas budget which user want to set, and user 
	* can input the gas budget value.
	* @author  Group 18
	* @version 1.0
	*/ 
	class GasActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int type = 0;// 0代表气量消耗，1代表气费
			String showMessage;
			String unit;
			String[] options = { "gas consumption", "gas cost" };
			String budget = (String) JOptionPane.showInputDialog(null, "Select the budget type", "Budget Type",
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (budget != null) {
				if (budget.equals(options[0])) {
					type = 0;
					showMessage = "Please input the gas consumption budget(kWh)";
					unit = "kWh";
				} else {
					type = 1;
					showMessage = "Please input the gas consumption budget(pounds)";
					unit = "pounds";
				}
				String gasBudget = JOptionPane.showInputDialog(null, showMessage);
				if (software.setGasBudget(gasBudget, type)) {
					field4.setText(software.getGasBudgetValue() + unit);
					frame.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Please input the legal value!");
				}
			}
		}
	}

	/**Title: ViewHistoryActionListener 
	* Description: This is an inner class called ViewHistoryActionListener.
	* This class is used to deal with the event after the "view history" button has been clicked. 
	* It will show an interface to choose the type of history which user want to view.
	* @author  Group 18
	* @version 1.0
	*/ 
	class ViewHistoryActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			new UserCheckHistoryGUI(software, frame);
		}
	}

	/**Title: CheckTariffActionListener 
	* Description: This is an inner class called CheckTariffActionListener.
	* This class is used to deal with the event after the "check tariff" button has been clicked. 
	* It will show an interface to show the current tariff.
	* @author  Group 18
	* @version 1.0
	*/ 
	class CheckTariffActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, SmartMonitoringSoftware.checkTariff());
		}
	}

	/**Title: SendToActionListener 
	* Description: This is an inner class called SendToActionListener.
	* This class is used to deal with the event after the "send meter readings" button has been clicked. 
	* It will show an interface to show the send status (success or fail). After the meter readings been sent,
	* the provider can view the latest consumption and costs history of this user.
	* @author  Group 18
	* @version 1.0
	*/ 
	class SendToActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (software.generateMonthlyBill(getTime().substring(0, 7))) {//截取月份
				JOptionPane.showMessageDialog(null, "send monthly bills successfully");
			} else {
				JOptionPane.showMessageDialog(null, "send monthly bills failed");
			}
		}
	}
	
	/**Title: BackToLoginActionListener
	* Description: This is an inner class called BackToLoginActionListener.
	* This class is used to deal with the event after the "Exit Login" button has been clicked. 
	* It will return to the user login interface, and user can login by different id.
	* @author  Group 18
	* @version 1.0
	*/ 
	class BackToLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			timer.cancel();//关闭定时
			frame.dispose();//关闭界面
			new UserLogin();
		}
	}
	
	/**Title: ElectronicPanel 
	* Description: This is an inner class called ElectronicPanel.
	* This class is used to change the circle color to red when the daily electronic consumption or
	* costs is larger than the budget.
	* @author  Group 18
	* @version 1.0
	*/ 
	class ElectronicPanel extends JPanel {
		private static final long serialVersionUID = 8186886441092964871L;

		public void paintComponent(Graphics g) {
			if (software.compareDailyElectronicUsageWithBudget()) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.green);
			}
			g.fillOval(this.getWidth() / 2, this.getHeight() / 4, 20, 20);
		}
	}

	/**Title: GasPanel 
	* Description: This is an inner class called GasPanel.
	* This class is used to change the circle color to red when the daily gas consumption or
	* costs is larger than the budget.
	* @author  Group 18
	* @version 1.0
	*/ 
	class GasPanel extends JPanel {
		private static final long serialVersionUID = 2384802767707835837L;

		public void paintComponent(Graphics g) {
			if (software.compareDailyGasUsageWithBudget()) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.green);
			}
			g.fillOval(this.getWidth() / 2, this.getHeight() / 4, 20, 20);

		}
	}

}

package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.EnergyProvider;
import Controller.SmartElectricityMeter;
import Controller.SmartGasMeter;
import Controller.SmartMonitoringSoftware;

/**Title: UserLogin 
* Description: This is UserLogin class.
* This class is used to generate user login interface.
* @author  Group 18
* @version 1.0
*/ 
public class UserLogin {
	JFrame frame;
	JTextField textField1, textField2;
	JButton buttonOK, buttonCancel;

	public static void main(String[] args) {
		new UserLogin();
	}

	public UserLogin() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 300, 400, 310);
		JPanel panel1 = new JPanel(new GridLayout(3, 2, 10, 100));

		JLabel label1 = new JLabel("User ID: ");
		JLabel label2 = new JLabel("Password: ");

		textField1 = new JTextField();

		textField2 = new JTextField();

		buttonOK = new JButton("OK");
		buttonCancel = new JButton("Quit");
		buttonOK.addActionListener(new OKListener());
		buttonCancel.addActionListener(new QuitListener());

		panel1.add(label1);
		panel1.add(textField1);
		panel1.add(label2);
		panel1.add(textField2);
		panel1.add(buttonOK);
		panel1.add(buttonCancel);
		frame.getContentPane().add(BorderLayout.NORTH, panel1);
		frame.setVisible(true);
	}
	

	/**Title: OKListener 
	* Description: This is an inner class called OKListener.
	* This class is used to deal with the event after the "OK" button has been clicked. It will
	* set the current tariff.
	* @author  Group 18
	* @version 1.0
	*/ 
	class OKListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (textField1.getText() != null) {
				if (EnergyProvider.checkUserExisted(textField1.getText())) {//check if the user is existed
					if (EnergyProvider.checkUserPassword(textField1.getText(), textField2.getText())) {
						SmartMonitoringSoftware software = new SmartMonitoringSoftware(new SmartElectricityMeter(textField1.getText()),
								new SmartGasMeter(textField1.getText()));
						software.setId(textField1.getText());
						new MonitoringSoftwareGUI(software);
						frame.dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "The password is not correct!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "This user ID is not existed!");
				}
			} else {
				System.exit(0);
			}
		}
	}

	/**Title: CancelListener 
	* Description: This is an inner class called CancelListener.
	* This class is used to deal with the event after the "Cancel" button has been clicked. It will
	* close this GUI, and return to the ProviderGUI.
	* @author  Group 18
	* @version 1.0
	*/ 
	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}

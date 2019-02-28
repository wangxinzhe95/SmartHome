package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.EnergyProvider;

/**Title: SetTariffGUI 
* Description: This is SetTariffGUI class.
* This class is used to generate set tariff interface. And the provider can set the
* current tariff in this interface.
* @author  Group 18
* @version 1.0
*/
public class SetTariffGUI {
	JFrame frame;
	JTextField textField1, textField2;
	JButton buttonOK, buttonCancel;

	public SetTariffGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setBounds(500, 300, 400, 310);
		JPanel panel1 = new JPanel(new GridLayout(3, 2, 10, 100));

		JLabel label1 = new JLabel("set electronic unit(p/kWh): ");
		JLabel label2 = new JLabel("set gas unit(p/kWh): ");

		textField1 = new JTextField();

		textField2 = new JTextField();

		buttonOK = new JButton("OK");
		buttonCancel = new JButton("Cancel");
		buttonOK.addActionListener(new OKListener());
		buttonCancel.addActionListener(new CancelListener());

		frame.addWindowListener(new WindowAdapter() {// 点红叉关闭时，会返回到主界面
			public void windowClosing(WindowEvent we) {
				frame.dispose();
				new ProviderGUI();
			}
		});

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
			if (EnergyProvider.setTariff(textField1.getText(), textField2.getText())) {
				JOptionPane.showMessageDialog(null, "Please input the legal value！");
			} else {
				new ProviderGUI();
				frame.dispose();
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
	class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ProviderGUI();
			frame.dispose();
		}
	}
}

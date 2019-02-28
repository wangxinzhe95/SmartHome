package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.SmartMonitoringSoftware;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**Title: UserCheckHistoryGUI 
* Description: This is UserCheckHistoryGUI class.
* This class is used to generate user check history interface. And the user can choose
* the type of history which user want to view.
* @author  Group 18
* @version 1.0
*/
public class UserCheckHistoryGUI extends JFrame {
	private static final long serialVersionUID = 6727708074734450670L;
	private JPanel contentPane;
	private JTextField textField;
	JRadioButton rdbtnNewRadioButton, rdbtnNewRadioButton_1, rdbtnNewRadioButton_2, rdbtnNewRadioButton_3,
			rdbtnNewRadioButton_4;
	private SmartMonitoringSoftware software;
	private JFrame frame;

	public UserCheckHistoryGUI(SmartMonitoringSoftware software, JFrame frame) {
		this.frame = frame;
		this.frame.setVisible(false);//check的时候，前面的计算当日消耗的定时任务必须继续跑，所以只是关闭了界面
		this.software = software;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//点击红叉时，不关闭程序，而是返回至主界面
		setBounds(500, 300, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		addWindowListener(new MyWindowAdapter());//设置点击红叉后的操作

		JLabel lblNewLabel = new JLabel("Select the energy type:");
		lblNewLabel.setBounds(18, 27, 142, 19);
		contentPane.add(lblNewLabel);

		rdbtnNewRadioButton = new JRadioButton("electronic");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(160, 24, 94, 23);
		contentPane.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("gas");
		rdbtnNewRadioButton_1.setBounds(160, 58, 141, 23);
		contentPane.add(rdbtnNewRadioButton_1);

		ButtonGroup bg1 = new ButtonGroup();//使两个单选框只能有一个被选中
		bg1.add(rdbtnNewRadioButton);
		bg1.add(rdbtnNewRadioButton_1);

		JLabel lblNewLabel_1 = new JLabel("Select the type of bills:");
		lblNewLabel_1.setBounds(278, 27, 168, 18);
		contentPane.add(lblNewLabel_1);

		rdbtnNewRadioButton_2 = new JRadioButton("daily bills");
		rdbtnNewRadioButton_2.setSelected(true);
		rdbtnNewRadioButton_2.setBounds(429, 24, 141, 23);
		contentPane.add(rdbtnNewRadioButton_2);

		rdbtnNewRadioButton_3 = new JRadioButton("weekly bills");
		rdbtnNewRadioButton_3.setBounds(429, 58, 141, 23);
		contentPane.add(rdbtnNewRadioButton_3);

		rdbtnNewRadioButton_4 = new JRadioButton("monthly bills");
		rdbtnNewRadioButton_4.setBounds(429, 93, 141, 23);
		contentPane.add(rdbtnNewRadioButton_4);

		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rdbtnNewRadioButton_2);
		bg2.add(rdbtnNewRadioButton_3);
		bg2.add(rdbtnNewRadioButton_4);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new OKActionListener());
		btnNewButton.setBounds(79, 218, 117, 29);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new CancelActionListener());
		btnNewButton_1.setBounds(269, 218, 117, 29);
		contentPane.add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(249, 139, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblyY = new JLabel("Set the start day(yyyy-MM-dd):");
		lblyY.setBounds(33, 142, 218, 22);
		contentPane.add(lblyY);
		setVisible(true);
	}

	/**Title: OKActionListener 
	* Description: This is an inner class called OKActionListener.
	* This class is used to deal with the event after the "OK" button has been clicked. 
	* It will show an interface to show the consumption and costs history.
	* @author  Group 18
	* @version 1.0
	*/
	class OKActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String consumeType, checkType, dateStart;
			if (rdbtnNewRadioButton.isSelected()) {
				consumeType = rdbtnNewRadioButton.getText();
			} else {
				consumeType = rdbtnNewRadioButton_1.getText();
			}
			if (rdbtnNewRadioButton_2.isSelected()) {
				checkType = rdbtnNewRadioButton_2.getText();
			} else if (rdbtnNewRadioButton_3.isSelected()) {
				checkType = rdbtnNewRadioButton_3.getText();
			} else {
				checkType = rdbtnNewRadioButton_4.getText();
			}
			dateStart = textField.getText();

			JOptionPane.showMessageDialog(null, software.checkHistory(consumeType, checkType, dateStart));
		}
	}

	/**Title: CancelActionListener 
	* Description: This is an inner class called CancelActionListener.
	* This class is used to deal with the event after the "Cancel" button has been clicked. 
	* It will close current interface, and return to the main frame.
	* @author  Group 18
	* @version 1.0
	*/ 
	class CancelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(true);
			dispose();
		}
	}

	/**Title: MyWindowAdapter 
	* Description: This is an inner class called MyWindowAdapter.
	* This class is used to deal with the event after the "X" button has been clicked. 
	* It will close current interface, and return to the main frame.
	* @author  Group 18
	* @version 1.0
	*/ 
	class MyWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			frame.setVisible(true);
			dispose();
		}
	}

}

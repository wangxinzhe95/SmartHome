package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Controller.EnergyProvider;

/**
 * Title: ProviderGUI Description: This is ProviderGUI class. This class is used
 * to generate energy provider interface. And the provider can manage users and
 * set tariff in this interface.
 * 
 * @author Group 18
 * @version 1.0
 */
public class ProviderGUI {
	JFrame frame;
	JTextArea textArea1, textArea2;
	JTable table1, table2;
	JButton buttonAdd, buttonRemove, buttonView;
	JButton buttonSetTariff;
	String[] columnNames = {"id", "name", "password"};

	public static void main(String[] args) {
		new ProviderGUI();
	}

	public ProviderGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 800, 300);

		JPanel panel1 = new JPanel(new GridLayout(1, 2, 200, 0));
		table1 = new JTable(EnergyProvider.viewAllUser(), columnNames);
		table1.setPreferredScrollableViewportSize(new Dimension(100,100));
		JScrollPane scroller1 = new JScrollPane(table1);// 使JTextArea具有上下滚动的效果
//		textArea1.setLineWrap(true);
		scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		table1.setEnabled(false);//不可编辑


		textArea2 = new JTextArea(10, 20);
		JScrollPane scroller2 = new JScrollPane(textArea2);
//		textArea2.setLineWrap(true);
		scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		textArea2.setText(EnergyProvider.checkTariff());
		textArea2.setEditable(false);

		panel1.add(scroller1);
		panel1.add(scroller2);

		JPanel panel2 = new JPanel(new GridLayout(1, 4, 30, 0));

		buttonAdd = new JButton("Add");
		buttonView = new JButton("View");
		buttonRemove = new JButton("Remove");
		buttonSetTariff = new JButton("Set");
		buttonAdd.addActionListener(new AddListener());
		buttonView.addActionListener(new ViewListener());
		buttonRemove.addActionListener(new RemoveListener());
		buttonSetTariff.addActionListener(new SetTariffListener());

		panel2.add(buttonAdd);
		panel2.add(buttonView);
		panel2.add(buttonRemove);
		panel2.add(buttonSetTariff);
		frame.getContentPane().add(BorderLayout.NORTH, panel1);
		frame.getContentPane().add(BorderLayout.SOUTH, panel2);
		frame.setVisible(true);
	}

	/**
	 * Title: AddListener Description: This is an inner class called
	 * AddListener. This class is used to deal with the event after the "Add"
	 * button has been clicked. It will show an interface to add user.
	 * 
	 * @author Group 18
	 * @version 1.0
	 */
	class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			new RegisterUserGUI();
			frame.dispose();
		}
	}

	/**
	 * Title: ViewListener Description: This is an inner class called
	 * ViewListener. This class is used to deal with the event after the "View"
	 * button has been clicked. It will show an interface to view the consume
	 * history of each user.
	 * 
	 * @author Group 18
	 * @version 1.0
	 */
	class ViewListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String viewId = JOptionPane.showInputDialog(null, "Please input the user you want to view");
			if (viewId != null) {
				if (EnergyProvider.checkUserExisted(viewId)) {
					JOptionPane.showMessageDialog(null, EnergyProvider.viewUserHistory(viewId));
				} else {
					JOptionPane.showMessageDialog(null, "The user ID is not existed!");
				}
			}
		}
	}

	/**
	 * Title: RemoveListener Description: This is an inner class called
	 * RemoveListener. This class is used to deal with the event after the
	 * "Remove" button has been clicked. It will show an interface to remove the
	 * existed user.
	 * 
	 * @author Group 18
	 * @version 1.0
	 */
	class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String removeId = JOptionPane.showInputDialog(null, "Please input the user ID you want to delete");
			if (removeId != null) {
				if (EnergyProvider.checkUserExisted(removeId)) {
					if (EnergyProvider.removeUser(removeId)) {
						JOptionPane.showMessageDialog(null, "You are not input the user ID");
					}
					frame.dispose();
					new ProviderGUI();
				} else {
					JOptionPane.showMessageDialog(null, "The user ID is not existed!");
				}
			}
		}
	}

	/**
	 * Title: SetTariffListener Description: This is an inner class called
	 * SetTariffListener. This class is used to deal with the event after the
	 * "Set" button has been clicked. It will show an interface to set the
	 * tariff.
	 * 
	 * @author Group 18
	 * @version 1.0
	 */
	class SetTariffListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			new SetTariffGUI();
			frame.dispose();
		}
	}
}

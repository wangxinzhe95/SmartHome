package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**Title: EnergyProvider
* Description: This is EnergyProvider class. There are many methods in this class
* This class is used to implement the provider function.
* @author  Group 18
* @version 1.0
*/
public class EnergyProvider {
	/**
	 * This method is used to generate a random id for the new user.
     * @return the id of new user in Integer.
	 */
	public static int generateId() {
		return (int) (System.currentTimeMillis() / 1000);// 根据系统时间，产生唯一id
	}

	/**
	 * This method is used to generate a random id for the new user.
	 * @param         houseHoldId            the user's id
     * @return a boolean value to indicate if the user is existed.
	 */
	public static boolean checkUserExisted(String houseHoldId) {
		boolean existing = false;
		if (houseHoldId.trim().length() != 0) {
			try {
				String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的user.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (lineArray[0].equals(houseHoldId)) {//如果都到了文件中的某一行的id和houseHoldId相同，则说明存在
						existing = true;
						break;
					}
					line = br.readLine();//一次读入一行数据
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return existing;
		} else {
			return existing;
		}
	}

	public static boolean checkUserPassword(String houseHoldId, String password) {
		boolean result = false;
		if (password.trim().length() != 0) {
			try {
				String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的user.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (lineArray[0].equals(houseHoldId)) {//如果都到了文件中的某一行的id和houseHoldId相同，则说明存在
						if (lineArray[8].equals(password)) {
							result = true;
						}
					}
					line = br.readLine();//一次读入一行数据
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} else {
			return result;
		}
	}
	/**
	 * This method is used to count the number of users.
     * @return an integer value to indicate the number of users.
	 */

	public static int countUsers() {
		int count = -1;
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				count++;
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	/**
	 * This method is used to add a new user to the file.
	 * @param         houseHoldName            the user's name
	 * @param         password                  the first user's password
	 * @param         verify_password            the second user's password
     * @return a string value to indicate if the user has been added successfully.
	 */
	public static String addUser(String houseHoldName, String password, String verify_password, String email, String address) {
		if (houseHoldName.trim().length() != 0) {
			if (email.trim().length() != 0) {
				if (address.trim().length() != 0) {
					if ((password.trim().length() != 0) && (verify_password.trim().length() != 0)) {
						if (password.equals(verify_password)) {
							try {
								File writename = new File("user.txt"); // 相对路径，如果没有则要建立一个新的user.txt文件
								BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));// 追加写入
								int houseHoldId = EnergyProvider.generateId();
								out.write(houseHoldId + " " + houseHoldId + " " + houseHoldId + " " + houseHoldName + " 100 " + "kWh"
										+ " 100 " + "kWh" + " " + password + " " + email + " " + address + "\r\n");// 默认budget为100
								out.flush(); // 把缓存区内容压入文件
								out.close(); // 最后记得关闭文件
								return "success";
							} catch (Exception e) {
								e.printStackTrace();
								return "register failed, please try again!";
							}
						}
						else {
							return "Two input password must be consistent!";
						}
					}
					else {
						return "Please input both of two passwords!";
					}
				}
				else {
					return "Please input your address!";
				}
			}
			else {
				return "Please input your email!";
			}
		} else {
			return "Please input your name!";
		}
	}

	/**
	 * This method is used to obtain all users' information.
     * @return a string array which contains the information of all users.
	 */
	
	public static String[][] viewAllUser() {
		String[][] result = new String[EnergyProvider.countUsers()][3];
		int i = -1;
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			line = br.readLine();//从第二行开始读
			while (line != null) {
				i++;
				String[] lineArray = line.split(" ");
				result[i][0] = lineArray[0];
				result[i][1] = lineArray[3];
				result[i][2] = lineArray[8];
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * This method is used to view the consumption and costs history of each user.
	 * @param         viewId            the user's id
     * @return a string which contains the specific user's monthly bill.
	 */
	public static String viewUserHistory(String viewId) {
		String result = "";
		if (viewId.trim().length() != 0) {
			try {
				String pathname = "bills.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的bills.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				String[] line1 = line.split(" ");//line1代表bill.txt的第一行
				result += line1[1] + " " + line1[2] + " " + line1[3] + " " + line1[4] + " " + line1[5] + "\r\n";
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (viewId.equals(lineArray[0])) {//若id相同，则输出此行的所有数据
						result += lineArray[1] + " " + lineArray[2] + " " + lineArray[3] + " " + lineArray[4] + " "
								+ lineArray[5] + "\r\n";
					}
					line = br.readLine();// 一次读入一行数据
				}
				br.close();
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		} else {
			return result;
		}
	}

	/**
	 * This method is used to remove the existed user.
	 * @param         removeId            the user's id
     * @return a boolean value to indicate if the user has been removed successfully.
	 */
	public static boolean removeUser(String removeId) {
		if (removeId.trim().length() != 0) {
			try {
				String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的user.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				String lineContent = "";
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (!(removeId.equals(lineArray[0]))) {//除了id和removeId相同的，都重新写到文件中
						lineContent = lineContent + line + "\r\n";
					}
					line = br.readLine();// 一次读入一行数据
				}
				File writename = new File("user.txt"); // 相对路径，如果没有则要建立一个新的user.txt文件
				BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
				out.write(lineContent);
				br.close();
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * This method is used to check the current tariff.
     * @return a string which contains the current tariff.
	 */
	public static String checkTariff() {
		String result = "";
		try {
			String pathname = "tariff.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的tariff.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			line = br.readLine();// 第二行
			String[] lineArray = line.split(" ");
			result += "Electricity Unit rate: " + lineArray[0] + "p per kWh" + "\r\n";
			result += "Gas Unit rate: " + lineArray[1] + "p per kWh" + "\r\n";
			br.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * This method is used to set the tariff.
	 * @param         electronic            the electronic price which you want to set
	 * @param           gas                   the gas price which you want to set
     * @return a boolean value to indicate if the tariff has been set successfully.
	 */
	public static boolean setTariff(String electronic, String gas) {
		if ((electronic.trim().length() != 0) && (gas.trim().length() != 0)) {
			try {
				double electronicValue = Double.parseDouble(electronic);
				double gasValue = Double.parseDouble(gas);

				if (electronicValue < 0 || gasValue < 0) {//输入小于0的值报错
					return true;
				}

				String pathname = "tariff.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的tariff.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				String[] lineArray = line.split(" ");
				String lineContent = "";
				lineContent = lineContent + lineArray[0] + " " + lineArray[1] + "\r\n";
				lineContent = lineContent + electronic + " " + gas + "\r\n";

				File writename = new File("tariff.txt"); // 相对路径，如果没有则要建立一个新的tariff.txt文件
				BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));// 覆盖写入
				out.write(lineContent);
				br.close();
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		} else {
			return true;
		}
	}

}
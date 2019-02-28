package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**Title: SmartMonitoringSoftware 
* Description: This is SmartMonitoringSoftware class. There are many methods in this class
* This class is used to implement the SmartMonitoringSoftware function, such as check tariff
* and so on.
* It has four attribute:id, electronicMeter, gasMeter, time.
* @author  Group 18
* @version 1.0
*/ 
public class SmartMonitoringSoftware {

	private String houseHoldId;//the user id

	private SmartElectricityMeter electronicMeter;//the electronic meter which belongs to this user

	private SmartGasMeter gasMeter;//the gas meter which belongs to this user

	private String time = null;//the current time

	public SmartMonitoringSoftware(SmartElectricityMeter electronicMeter, SmartGasMeter gasMeter) {
		this.electronicMeter = electronicMeter;
		this.gasMeter = gasMeter;
	}

	public void setId(String houseHoldId) {
		this.houseHoldId = houseHoldId;
	}

	public String getId() {
		return this.houseHoldId;
	}

	public SmartElectricityMeter getElectronicMeter() {
		return this.electronicMeter;
	}

	public SmartGasMeter getGasMeter() {
		return this.gasMeter;
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

	/**
	 * This method is used to view the current tariff.
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
			result = "Electricity Unit rate " + lineArray[0] + "p per kWh" + "\r\n" + "Gas Unit rate " + lineArray[1]
					+ "p per kWh" + "\r\n";
			br.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * This method is used to set the electronic budget.
	 * @param         electronicBudget            the value of electronic budget 
	 * @param              type                  the budget type (0: consumption or 1: cost)
     * @return a boolean value to indicate if the electronic budget has been set successfully.
	 */
	public boolean setElectronicBudget(String electronicBudget, int type) {
		String unit;
		if (type == 0) {
			unit = "kWh";
		} else {
			unit = "pounds";
		}
		if (electronicBudget != null) {
			try {
				double electronicValue = Double.parseDouble(electronicBudget);

				if (electronicValue < 0) {//数值小于0直接返回false
					return false;
				}

				String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的user.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				String lineContent = "";
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (!(this.getId().equals(lineArray[0]))) {//若此行的id不等于本用户的id,则原封不动的加到lineContent
						lineContent = lineContent + line + "\r\n";
					} else {//若此行的id等于本用户的id,则修改electronic budget为输入值
						lineContent = lineContent + lineArray[0] + " " + lineArray[1] + " " + lineArray[2] + " "
								+ lineArray[3] + " " + electronicBudget + " " + unit + " " + lineArray[6] + " "
								+ lineArray[7] + " " + lineArray[8] + " " + lineArray[9] + " " + lineArray[10] + "\r\n";
					}
					line = br.readLine();// 一次读入一行数据
				}
				File writename = new File("user.txt"); // 相对路径，如果没有则要建立一个新的user.txt文件
				BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
				out.write(lineContent);
				br.close();
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * This method is used to set the gas budget.
	 * @param         gasBudget            the value of gas budget 
	 * @param              type                  the budget type (0: consumption or 1: cost)
     * @return a boolean value to indicate if the gas budget has been set successfully.
	 */
	public boolean setGasBudget(String gasBudget, int type) {
		String unit;
		if (type == 0) {
			unit = "kWh";
		} else {
			unit = "pounds";
		}
		if (gasBudget != null) {
			try {
				double gasValue = Double.parseDouble(gasBudget);

				if (gasValue < 0) {
					return false;
				}

				String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
				File filename = new File(pathname); // 要读取以上路径的user.txt文件
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
				BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
				String line = "";
				line = br.readLine();
				String lineContent = "";
				while (line != null) {
					String[] lineArray = line.split(" ");
					if (!(this.getId().equals(lineArray[0]))) {//若此行的id不等于本用户的id,则原封不动的加到lineContent
						lineContent = lineContent + line + "\r\n";
					} else {//若此行的id等于本用户的id,则修改gas budget为输入值
						lineContent = lineContent + lineArray[0] + " " + lineArray[1] + " " + lineArray[2] + " "
								+ lineArray[3] + " " + lineArray[4] + " " + lineArray[5] + " " + gasBudget + " " + unit
								+ " " + lineArray[8] + " " + lineArray[9] + " " + lineArray[10] + "\r\n";
					}
					line = br.readLine();// 一次读入一行数据
				}
				File writename = new File("user.txt"); // 相对路径，如果没有则要建立一个新的user.txt文件
				BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
				out.write(lineContent);
				br.close();
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * This method is used to obtain the value of the electronic budget of this user.
     * @return a double value which is the value of the electronic budget of this user.
	 */
	public double getElectronicBudgetValue() {
		double electronicBudgetValue = 0.0;
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//找到和本用户id相同的行
					electronicBudgetValue = Double.parseDouble(lineArray[4]);//取第五列，即electronic budget的值
				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return electronicBudgetValue;
	}

	/**
	 * This method is used to obtain the type (consumption or cost) of the electronic budget of this user.
     * @return a string which is the type of the electronic budget of this user.
	 */
	public String getElectronicBudgetType() {
		String electronicBudgetType = "";
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//找到和本用户id相同的行
					electronicBudgetType = lineArray[5];//取第六列，即electronic budget的类型
				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return electronicBudgetType;
	}

	/**
	 * This method is used to check if the daily electronic usage has been greater than the budget of this user.
     * @return a boolean value to indicate if the daily electronic usage has been greater than the budget of this user.
	 */
	public boolean compareDailyElectronicUsageWithBudget() {
		if (this.getElectronicBudgetType().equals("kWh")) {//若budget类型是consumption
			if (this.getElectronicMeter().getDailyUsage(this.getTime()) > this.getElectronicBudgetValue()) {//获取当日consumption与budget的值进行比较
				return true;
			} else {
				return false;
			}
		} else {//若budget类型是cost
			if (this.getElectronicMeter().getDailyCost(this.getTime()) > this.getElectronicBudgetValue()) {//获取当日cost与budget的值进行比较
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * This method is used to obtain the value of the gas budget of this user.
     * @return a double value which is the value of the gas budget of this user.
	 */
	public double getGasBudgetValue() {
		double gasBudgetValue = 0.0;
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//找到和本用户id相同的行
					gasBudgetValue = Double.parseDouble(lineArray[6]);//取第七列，即gas budget的值
				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gasBudgetValue;
	}

	/**
	 * This method is used to obtain the type (consumption or cost) of the gas budget of this user.
     * @return a string which is the type of the gas budget of this user.
	 */
	public String getGasBudgetType() {
		String gasBudgetType = "";
		try {
			String pathname = "user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的user.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//找到和本用户id相同的行
					gasBudgetType = lineArray[7];//取第八列，即gas budget的类型
				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gasBudgetType;
	}

	/**
	 * This method is used to check if the daily gas usage has been greater than the budget of this user.
     * @return a boolean value to indicate if the daily gas usage has been greater than the budget of this user.
	 */
	public boolean compareDailyGasUsageWithBudget() {
		if (this.getGasBudgetType().equals("kWh")) {//若budget类型是consumption
			if (this.getGasMeter().getDailyUsage(this.getTime()) > this.getGasBudgetValue()) {//获取当日consumption与budget的值进行比较
				return true;
			} else {
				return false;
			}
		} else {//若budget类型是cost
			if (this.getGasMeter().getDailyCost(this.getTime()) > this.getGasBudgetValue()) {//获取当日cost与budget的值进行比较
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * This method is used to view the consumption and cost of this user in a specific time interval.
	 * @param              consumeType            the electronic or gas history you want to view
	 * @param              checkType              the daily, weekly or monthly history you want to view
	 * @param              dateStart              the start date of this the daily, weekly or monthly history you want to view
     * @return a string which contains the consumption and cost of this user in a specific time interval.
	 */
	public String checkHistory(String consumeType, String checkType, String dateStart) {
		String result = "";
		int day = 1;
		String viewType = "";
		if (checkType.equals("daily bills")) {//日账单只取1天
			day = 1;
			viewType = "daily";
		} else if (checkType.equals("weekly bills")) {//周账单取7天
			day = 7;
			viewType = "weekly";
		} else if (checkType.equals("monthly bills")) {//月账单取30天
			day = 30;
			viewType = "monthly";
		}
		if (dateStart != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.parse(dateStart);
				if (consumeType.equals("electronic")) {//电费，直接调用已经写好的函数可以获得相应的值
					result = "From " + dateStart + ", your" + viewType + "electronic usage is "
							+ this.getElectronicMeter().getSegmentUsageAndCost(dateStart, day)[0] + "kWh\r\n" + viewType
							+ "electronic cost is " + this.getElectronicMeter().getSegmentUsageAndCost(dateStart, day)[1] + "pounds";
				} else {//气费
					result = "From" + dateStart + ", your" + viewType + "gas usage is "
							+ this.getGasMeter().getSegmentUsageAndCost(dateStart, day)[0] + "kWh\r\n" + viewType
							+ "gas cost is " + this.getGasMeter().getSegmentUsageAndCost(dateStart, day)[1] + "pounds";
				}

			} catch (Exception e) {
				result = "The date you input is not legal!";
			}

		}

		return result;

	}

	/**
	 * This method is used to generate the monthly bill of this user.
	 * @param         month            the current month
     * @return a boolean value to indicate if the monthly bill has been generated successfully.
	 */
	public boolean generateMonthlyBill(String month) {
		double[] electronicList = this.getElectronicMeter().getMonthlyBill(month);//获取该月电量消耗与消费
		double[] gasList = this.getGasMeter().getMonthlyBill(month);//获取该月气量消耗与消费
		boolean existing = false;
		try {
			String pathname = "bills.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的bills.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			String lineContent = "";
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (!(lineArray[0].equals(this.getId()))) {//如果此行的id不是本用户id，则原封不动的加到lineContent中
					lineContent = lineContent + line + "\r\n";
				} else if (!(lineArray[1].equals(month))) {//如果此行的id是本用户id，但是日期不是当前日期，也原封不动的加到lineContent中
					lineContent = lineContent + line + "\r\n";
				} else {//已经存在此用户当前日期的数据，就修改它
					existing = true;//表明已经存在此用户当前日期的数据
					lineContent = lineContent + lineArray[0] + " " + lineArray[1] + " " + electronicList[0] + " "
							+ electronicList[1] + " " + gasList[0] + " " + gasList[1] + "\r\n";
				}
				line = br.readLine();// 一次读入一行数据
			}
			if (existing == false) {//若不存在此用户当前日期的数据，则新建一行
				lineContent = lineContent + this.getId() + " " + month + " " + electronicList[0] + " "
						+ electronicList[1] + " " + gasList[0] + " " + gasList[1] + "\r\n";
			}
			br.close();

			File writename = new File("bills.txt"); // 相对路径，如果没有则要建立一个新的user.txt文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));// 覆盖
			out.write(lineContent);
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
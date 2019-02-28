package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**Title: SmartMeter 
* Description: This is SmartMeter class. There are many methods in this class
* This class is used to implement the smart meter function, such as getting daily
* electronic usage and so on.
* It has three attribute:id, time, filename.
* @author  Group 18
* @version 1.0
*/ 
public class SmartMeter {
	private String id;//the meter's id
	private String time = null;//the current time

	private String filename;//the file name that the method read from, it decide the meter is electronic meter or gas meter

	public SmartMeter(String id) {
		this.id = id;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}
	
	/**
	 * This method is used to obtain the daily usage of this specific user .
	 * @param         time            the current date
     * @return a double value which is the daily usage.
	 */
	public double getDailyUsage(String time) {
		String usageString = "0.0";
		try {
			String pathname = this.getFilename(); // 读取文件名，决定是电费还是气费
			File filename = new File(pathname); // 要读取以上路径的文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//找到了某一行的id和本用户的id相同
					if (lineArray[1].equals(time)) {//如果日期也相同，则找到所寻目标
						usageString = lineArray[2];//获取日期为time那天的消耗数值
					}
				}
				line = br.readLine();// 一次读入一行数据
			}
			//循环结束还没找到，说明日期为time那天没消耗, 所以usageString还是0.0
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double dailyUsage = Double.parseDouble(usageString);//转化为double返回
		return dailyUsage;
	}

	/**
	 * This method is used to obtain a specific time interval usage of this specific user .
	 * @param         startTime                   the start date
	 * @param           day                 the length of time interval
     * @return a double array which contains the specific time interval consumption and cost.
	 */
	public double[] getSegmentUsageAndCost(String startTime, int day) {
		double usage = 0.0;
		double cost = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String pathname = this.getFilename(); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				Date date = sdf.parse(startTime);
				if (lineArray[0].equals(this.getId())) {//先找到跟用户id相同的行
					for (int i = 0; i < day; i++) {//循环day次，如果该行的时间在输入的时间段内，则将消耗和消费记录添加到usage和cost
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.add(Calendar.DAY_OF_MONTH, i);// 设置的时间+i天
						Date nowTime = c.getTime();//获取+i天后的日期，date格式
						if (lineArray[1].equals(sdf.format(nowTime))) {//跟文件中的第二列(时间)比较，找到每一天的消耗和消费
							usage += Double.parseDouble(lineArray[2]);
							cost += Double.parseDouble(lineArray[3]);
						}
					}

				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double[] returnList = { usage, cost };
		return returnList;
	}

	/**
	 * This method is used to increase the consumption of this user.
	 * @param         consume                   the value that the user's consumption increased
	 */
	public void increaseUsage(double consume) {// 一打开SmartMonitoringSoftware就开始消耗
		boolean existing = false;
		DecimalFormat df = new DecimalFormat("#.00");//小数点后两位
		try {
			String pathname = this.getFilename(); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			String lineContent = "";
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (!(lineArray[0].equals(this.getId()))) {//如果此行的id不是本用户id，则原封不动的加到lineContent中
					lineContent = lineContent + line + "\r\n";
				} else if (!(lineArray[1].equals(this.getTime()))) {//如果此行的id是本用户id，但是日期不是当前日期，也原封不动的加到lineContent中
					lineContent = lineContent + line + "\r\n";
				} else {//已经存在此用户当前日期的数据，就修改它
					existing = true;//表明已经存在此用户当前日期的数据
					double currentUsage = Double.parseDouble(lineArray[2]);
					currentUsage += consume;//增加消耗后，写入到lineContent中
					String currentUsageString = df.format(currentUsage);
					lineContent = lineContent + lineArray[0] + " " + lineArray[1] + " " + currentUsageString + " "
							+ df.format(currentUsage * this.getTariff()) + "\r\n";
				}
				line = br.readLine();// 一次读入一行数据
			}
			if (existing == false) {//若不存在此用户当前日期的数据，则新建一行
				lineContent = lineContent + this.getId() + " " + this.getTime() + " " + consume + " " + df.format(consume * this.getTariff()) + "\r\n";
			}
			br.close();

			File writename = new File(this.getFilename()); // 相对路径，如果没有则要建立一个新的文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));// 覆盖写入
			out.write(lineContent);//用lineContent复写该文件，即更新数据
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to obtain the current electronic or gas price.
     * @return a double value which represents the price of electronic or gas.
	 */
	public double getTariff() {
		double tariff = 0.0;
		try {
			String pathname = "tariff.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的tariff.txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			line = br.readLine();// 第二行

			String[] lineArray = line.split(" ");
			if (this.filename.equals("electronicUsage.txt"))//若文件名是电，则获取电价
				tariff = Double.parseDouble(lineArray[0]);
			else if (this.filename.equals("gasUsage.txt"))//若文件名是气，则获取气价
				tariff = Double.parseDouble(lineArray[1]);
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tariff;
	}

	/**
	 * This method is used to obtain the daily cost of this specific user .
	 * @param         time            the current date
     * @return a double value which is the daily cost.
	 */
	public double getDailyCost(String time) {
		double electronicUsage = this.getDailyUsage(time);
		return electronicUsage * this.getTariff() * 1.0;//用量乘以价格
	}

	/**
	 * This method is used to obtain the specific month usage and cost of this specific user .
	 * @param         month            the specific month
     * @return a double array which contains the specific month usage and cost.
	 */
	public double[] getMonthlyBill(String month) {
		double usage = 0.0;
		double cost = 0.0;
		try {
			String pathname = this.getFilename(); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(" ");
				if (lineArray[0].equals(this.getId())) {//先找到和本用户id相同的行
					if (lineArray[1].substring(0, 7).equals(month)) {//截取此行的日期的前八位，即月份，与输入的month进行比较，若相等，则增加消耗和消费
						usage += Double.parseDouble(lineArray[2]);
						cost += Double.parseDouble(lineArray[3]);
					}
				}
				line = br.readLine();// 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double[] returnList = { usage, cost };
		return returnList;
	}

}

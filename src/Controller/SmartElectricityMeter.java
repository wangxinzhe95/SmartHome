package Controller;

/**Title: SmartElectricityMeter 
* Description: This is SmartElectricityMeter class. 
* It extends the SmartMeter class.
* @author  Group 18
* @version 1.0
*/ 
public class SmartElectricityMeter extends SmartMeter {

	public SmartElectricityMeter(String id) {
		super(id);
		this.setFilename("electronicUsage.txt");//设置为electronicUsage.txt表明这是电表
	}

}
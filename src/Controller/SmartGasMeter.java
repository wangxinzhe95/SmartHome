package Controller;

/**Title: SmartGasMeter 
* Description: This is SmartGasMeter class. 
* It extends the SmartMeter class.
* @author  Group 18
* @version 1.0
*/ 
public class SmartGasMeter extends SmartMeter {

	public SmartGasMeter(String id) {
		super(id);
		this.setFilename("gasUsage.txt");//设置为gasUsgae.txt表明这是气表
	}

}

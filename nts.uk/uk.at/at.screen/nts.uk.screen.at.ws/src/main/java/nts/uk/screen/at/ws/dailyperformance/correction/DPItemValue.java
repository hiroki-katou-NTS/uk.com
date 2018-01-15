package nts.uk.screen.at.ws.dailyperformance.correction;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
public class DPItemValue {
	
	private String rowId;
	
	private int itemId;
	
	private String value;
	
	private String valueType;
	
	private String layoutCode;
	
	private String employeeId;
	
	private GeneralDate date;
}

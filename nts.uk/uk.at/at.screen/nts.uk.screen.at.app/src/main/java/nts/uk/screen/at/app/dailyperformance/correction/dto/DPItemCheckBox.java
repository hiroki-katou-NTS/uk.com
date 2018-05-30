package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPItemCheckBox {
private String rowId;
	
	private String itemId;
	
	private boolean value;
	
	private String valueType;
	
	private String employeeId;
	
	private GeneralDate date;
	
	private boolean flagRemoveAll;
}

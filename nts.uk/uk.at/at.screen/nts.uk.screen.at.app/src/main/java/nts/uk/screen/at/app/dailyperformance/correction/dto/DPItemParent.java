package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.Data;
@Data
public class DPItemParent {
	int mode;
	String employeeId;
	List<DPItemValue> itemValues;
	List<DPItemCheckBox> dataCheckSign;
	List<DPItemCheckBox> dataCheckApproval;
	DateRange dateRange;
}

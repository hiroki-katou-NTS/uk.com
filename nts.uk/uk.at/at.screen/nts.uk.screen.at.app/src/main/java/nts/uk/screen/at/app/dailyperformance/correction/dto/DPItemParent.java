package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.Data;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
@Data
public class DPItemParent {
	int mode;
	String employeeId;
	List<DPItemValue> itemValues;
	List<DPItemCheckBox> dataCheckSign;
	List<DPItemCheckBox> dataCheckApproval;
	DateRange dateRange;
	SPRStampSourceInfo spr;
	DPMonthValue monthValue;
}

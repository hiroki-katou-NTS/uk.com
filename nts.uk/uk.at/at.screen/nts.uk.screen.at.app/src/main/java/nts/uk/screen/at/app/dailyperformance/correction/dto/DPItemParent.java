package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.Data;
@Data
public class DPItemParent {
	List<DPItemValue> itemValues;
	List<DPItemCheckBox> dataCheckSign;
	List<DPItemCheckBox> dataCheckApproval;
}

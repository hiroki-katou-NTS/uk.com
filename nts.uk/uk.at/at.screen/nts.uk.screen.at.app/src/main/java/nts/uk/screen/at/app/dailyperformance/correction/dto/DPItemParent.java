package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPItemParent {
	private int mode;
	
	private String employeeId;
	
	private List<DPItemValue> itemValues;
	
	private List<DPItemCheckBox> dataCheckSign;
	
	private List<DPItemCheckBox> dataCheckApproval;
	
	private DateRange dateRange;
	
	private SPRStampSourceInfo spr;
	
	private DPMonthValue monthValue;
	
	private List<DailyRecordDto> dailyOlds;
	
	private List<DailyRecordDto> dailyEdits;
	
	private boolean flagCalculation;
	
}

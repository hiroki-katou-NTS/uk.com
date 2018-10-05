package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationOfDailyPerformanceDto {
	
	private String companyId;

	private SettingUnitType settingUnit;
	
	private String comment;
	
	private boolean showError;
}

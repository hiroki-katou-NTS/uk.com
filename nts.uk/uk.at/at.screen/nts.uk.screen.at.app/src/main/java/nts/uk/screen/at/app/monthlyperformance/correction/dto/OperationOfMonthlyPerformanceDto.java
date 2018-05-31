package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.enums.SettingUnit;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationOfMonthlyPerformanceDto {
	
	private String companyId;

	private SettingUnit settingUnit;
	
	private String comment;
}

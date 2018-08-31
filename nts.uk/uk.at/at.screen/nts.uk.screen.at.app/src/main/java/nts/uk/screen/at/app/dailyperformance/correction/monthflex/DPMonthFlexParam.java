package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPMonthFlexParam {

	private String companyId;
	private String employeeId;
	private GeneralDate date;
	private String employmentCode;
	
	OperationOfDailyPerformanceDto dailyPerformanceDto;
	Set<String> formatCode;
	
}

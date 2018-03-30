package nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class DailyAggregationProcessExport {

	private List<String> employeeID;
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
}

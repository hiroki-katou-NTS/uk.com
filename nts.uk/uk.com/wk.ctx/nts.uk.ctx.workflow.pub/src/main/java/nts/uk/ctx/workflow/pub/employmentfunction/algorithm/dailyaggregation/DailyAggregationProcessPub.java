package nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DailyAggregationProcessPub {

	DailyAggregationProcessExport findByID(List<String> employeeID, GeneralDate startDate, GeneralDate endDate);
}

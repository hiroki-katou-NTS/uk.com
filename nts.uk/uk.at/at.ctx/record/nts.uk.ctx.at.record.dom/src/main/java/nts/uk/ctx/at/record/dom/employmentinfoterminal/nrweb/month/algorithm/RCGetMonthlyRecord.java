package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.YearMonthPeriod;

public interface RCGetMonthlyRecord {
	Map<String, List<MonthlyRecordValues>> getRecordValues(
			List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);
}

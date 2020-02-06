package nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth;

import java.util.List;
import java.util.Map;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;


public interface ActualMultipleMonthAdapter {
	Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);

}

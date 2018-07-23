package nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;


public interface ActualMultipleMonthAdapter {
	List<MonthlyRecordValueImport> getActualMultipleMonth(String employeeId, YearMonthPeriod period, List<Integer> itemIds);

}

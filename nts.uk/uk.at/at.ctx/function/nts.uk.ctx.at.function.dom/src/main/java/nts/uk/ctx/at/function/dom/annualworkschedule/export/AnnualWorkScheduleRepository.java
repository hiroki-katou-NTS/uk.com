package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.time.YearMonth;
import java.util.List;

import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;

public interface AnnualWorkScheduleRepository {
	ExportData getData(String cid, String setItemsOutputCd, YearMonth startYearMonth,
			YearMonth endYearMonth,
			List<Employee> employees);
}

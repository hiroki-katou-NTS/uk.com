package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;

public interface AnnualWorkScheduleRepository {
	/**
	 * 年間勤務表の出力処理
	 */
	ExportData outputProcess(String cid, String setItemsOutputCd, Year fiscalYear, YearMonth startYearMonth,
			YearMonth endYearMonth, List<Employee> employees, PrintFormat printFormat, int breakPage,
			ExcludeEmp excludeEmp, Integer monthLimit, Optional<YearMonth> baseMonth);
	
}

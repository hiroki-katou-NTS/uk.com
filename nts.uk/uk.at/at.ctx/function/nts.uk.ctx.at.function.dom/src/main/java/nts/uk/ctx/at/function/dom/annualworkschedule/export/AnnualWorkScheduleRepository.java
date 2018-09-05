package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.time.YearMonth;
import java.util.List;

import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.shared.dom.common.Year;

public interface AnnualWorkScheduleRepository {
	/**
	 * 年間勤務表の出力処理
	 */
	ExportData outputProcess(String cid, String setItemsOutputCd, Year fiscalYear, YearMonth startYearMonth,
			YearMonth endYearMonth, List<Employee> employees, PrintFormat printFormat, int breakPage,
			ExcludeEmp excludeEmp, Integer monthLimit);
}

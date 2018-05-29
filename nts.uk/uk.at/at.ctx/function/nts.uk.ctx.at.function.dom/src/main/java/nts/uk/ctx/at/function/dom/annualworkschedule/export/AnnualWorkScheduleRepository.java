package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;

public interface AnnualWorkScheduleRepository {
	ExportData getData(String cid, String setItemsOutputCd, String startYearMonth,
			String endYearMonth,
			List<Employee> employees);
}

package nts.uk.ctx.at.function.app.find.holidaysremaining.report;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class HolidaysRemainingReportQuery {
	private HolidaysRemainingOutputConditionQuery holidayRemainingOutputCondition;
	private List<EmployeeQuery> lstEmpIds;
}

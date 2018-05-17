package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeData {
	private List<String> employeeInfo;
	private List<AnnualWorkScheduleData> annualWorkSchedule;
}
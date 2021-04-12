package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Getter
@Setter
public class HolidaysRemainingEmployee {

	private String employeeId;
	private String employeeCode;
	private String employeeName;
	private String workplaceId;
	private String workplaceCode;
	private String workplaceName;
	private String employmentName;
	// Update export KDR001-Ver 15 D2_6
	private String employmentCode;
	private String jobTitle;
	// Update export KDR001-Ver 15-D2-7
	private String jobTitleCode;
	private Optional<YearMonth> currentMonth;
	private HolidayRemainingInfor holidayRemainingInfor;

	public HolidaysRemainingEmployee(String employeeId, String employeeCode, String employeeName, String workplaceId,
			String workplaceCode, String workplaceName, String employmentName, String employmentCode,String jobTitle,String jobTitleCode,
			Optional<YearMonth> currentMonth, HolidayRemainingInfor holidayRemainingInfor) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
		this.employmentName = employmentName;
		this.jobTitle = jobTitle;
		this.jobTitleCode = jobTitleCode;
		this.currentMonth = currentMonth;
		this.holidayRemainingInfor = holidayRemainingInfor;
		this.employmentCode = employmentCode;
	}
}

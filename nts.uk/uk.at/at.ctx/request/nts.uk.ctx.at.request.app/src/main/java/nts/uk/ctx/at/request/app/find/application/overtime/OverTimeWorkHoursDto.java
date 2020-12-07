package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OverTimeWorkHoursOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OverTimeWorkHoursDto {
	
	public static final String PATTERN_YEAR_MONTH = "yyyy/MM";
	
	// 当月のありなし
	public Boolean isCurrentMonth;
	
	// 当月の時間外時間
	public AgreementTimeOfManagePeriodDto currentTimeMonth;
	
	// 当月の年月
	public String currentMonth;
	
	// 翌月のありなし
	public Boolean isNextMonth;
	
	// 翌月の時間外時間
	public AgreementTimeOfManagePeriodDto nextTimeMonth;
	
	// 翌月の年月
	public String nextMonth;
	
	public static OverTimeWorkHoursDto fromDomain(OverTimeWorkHoursOutput param) {
		int currentYear = param.getCurrentMonth().year();
		int currentMonth = param.getCurrentMonth().month();
		
		int nextYear = param.getNextMonth().year();
		int nextMonth = param.getNextMonth().month();
		
		String current = GeneralDate.ymd(currentYear, currentMonth, 1).toString(PATTERN_YEAR_MONTH);
		String next = GeneralDate.ymd(nextYear, nextMonth, 1).toString(PATTERN_YEAR_MONTH);
		return new OverTimeWorkHoursDto(
				param.getIsCurrentMonth(),
				AgreementTimeOfManagePeriodDto.fromDomain(param.getCurrentTimeMonth()),
				current,
				param.getIsNextMonth(),
				AgreementTimeOfManagePeriodDto.fromDomain(param.getNextTimeMonth()),
				next);
	}
}

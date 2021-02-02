package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OverTimeWorkHoursOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OverTimeWorkHoursCommand {
	
	public static final String PATTERN_YEAR_MONTH = "yyyy/MM";
	
	// 当月のありなし
	public Boolean isCurrentMonth;
	
	// 当月の時間外時間
	public AgreementTimeOfManagePeriodCommand currentTimeMonth;
	
	// 当月の年月
	public String currentMonth;
	
	// 翌月のありなし
	public Boolean isNextMonth;
	
	// 翌月の時間外時間
	public AgreementTimeOfManagePeriodCommand nextTimeMonth;
	
	// 翌月の年月
	public String nextMonth;
	
	public OverTimeWorkHoursOutput toDomain() {
		
		return OverTimeWorkHoursOutput.builder()
				.isCurrentMonth(isCurrentMonth)
				.currentTimeMonth(currentTimeMonth.toDomain())
				.currentMonth(new YearMonth(Integer.valueOf(currentMonth.split("/")[0])*100 + Integer.valueOf(currentMonth.split("/")[1])))
				.isNextMonth(isNextMonth)
				.nextTimeMonth(nextTimeMonth.toDomain())
				.nextMonth(new YearMonth(Integer.valueOf(nextMonth.split("/")[0])*100 + Integer.valueOf(nextMonth.split("/")[1])))
				.build();
	}
	
	
	 
}

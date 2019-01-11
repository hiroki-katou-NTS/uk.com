package nts.uk.ctx.at.schedule.app.export.shift.pattern.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class WorkMonthlySettingReportData {
	private String pattenCode;
	private String patternName;
	private GeneralDate date;
	private String workSetName;
	
	public static WorkMonthlySettingReportData createFromJavaType(String pattenCode,
			String patternName, GeneralDate date, String workSetName) {
		return new WorkMonthlySettingReportData(
				pattenCode, 
				patternName,
				date,
				workSetName);
	}
	
	public String getYearMonth() {
		return date.yearMonth().toString();
	}
	
	public int getDay() {
		return date.day();
	}

}

package nts.uk.ctx.at.schedule.app.export.shift.pattern.work;

import java.util.Optional;

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
	private Optional<GeneralDate> date;
	private Optional<String> workSetName;
	private String eventName;
	
	public static WorkMonthlySettingReportData createFromJavaType(String pattenCode,
			String patternName, Optional<GeneralDate> date, Optional<String> workSetName,String eventName) {
		return new WorkMonthlySettingReportData(
				pattenCode, 
				patternName,
				date,
				workSetName,
				eventName);
	}
	
	public String getYearMonth() {
		if (date.isPresent()) {
			return date.get().yearMonth().toString();
		}
		else {
			return "";
		}
	}
	
	public int getDay() {
		if (date.isPresent()) {
			return date.get().day();
		}
		else {
			return 0;
		}
	}
}

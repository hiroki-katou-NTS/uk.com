package nts.uk.ctx.at.schedule.app.export.shift.pattern.work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class PersionalWorkMonthlySettingReportData {
	private String scd;
	private String name;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private String patternCode;
	private String patternName;
	
	public static PersionalWorkMonthlySettingReportData createFromJavaType(String scd,
			String name, GeneralDate startDate, GeneralDate endDate, String patternCode, String patternName) {
		return new PersionalWorkMonthlySettingReportData(
				scd, name, startDate, endDate, patternCode, patternName);
	}
}

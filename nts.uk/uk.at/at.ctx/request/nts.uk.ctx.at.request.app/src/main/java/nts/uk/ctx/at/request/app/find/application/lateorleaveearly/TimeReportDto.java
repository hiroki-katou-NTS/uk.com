package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退時刻報告
public class TimeReportDto {
//	勤務NO
	private int workNo;
//	区分
	private int lateOrEarlyClassification;
//	時刻
	// wait handle
	private int timeWithDayAttr;
	
	public static TimeReportDto convertDto(TimeReport value) {
		return new TimeReportDto(
				value.getWorkNo(), 
				value.getLateOrEarlyClassification().value, 
				1);
	}
}

package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退時刻報告
public class TimeReport {
//	勤務NO
	private int workNo;
//	区分
	private LateOrEarlyAtr lateOrEarlyClassification;
//	時刻
	private TimeWithDayAttr timeWithDayAttr;
}

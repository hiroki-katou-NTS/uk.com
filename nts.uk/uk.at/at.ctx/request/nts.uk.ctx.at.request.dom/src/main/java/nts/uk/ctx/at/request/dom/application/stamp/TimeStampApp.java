package nts.uk.ctx.at.request.dom.application.stamp;
/**
 * Refactor4
 * @author hoangnd
 *
 */


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakout.GoOutReasonAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@NoArgsConstructor
@Data
//打刻申請時刻
public class TimeStampApp {
//	反映先
	DestinationTimeApp destinationTimeApp;
//	時刻
	private TimeWithDayAttr timeOfDay;
//	勤務場所
	private Optional<WorkLocationCD> workLocationCd; 
//	外出理由
	private Optional<GoOutReasonAtr> appStampGoOutAtr;
	
}

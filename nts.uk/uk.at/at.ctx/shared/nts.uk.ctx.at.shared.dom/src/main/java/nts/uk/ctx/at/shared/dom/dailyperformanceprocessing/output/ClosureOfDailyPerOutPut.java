package nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;

@Getter
@Setter
@NoArgsConstructor
public class ClosureOfDailyPerOutPut {
	
	private WorkInfoOfDailyAttendance workInfoOfDailyPerformance;
	
	private List<ErrMessageInfo> errMesInfos;

}

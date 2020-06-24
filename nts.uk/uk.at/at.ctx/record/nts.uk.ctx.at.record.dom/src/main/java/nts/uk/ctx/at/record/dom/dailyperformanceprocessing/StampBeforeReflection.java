package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

/**
 * 打刻再反映前の日別実績
 * @author nampt
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class StampBeforeReflection {
	
	/* 日別実績のPCログオン情報 */
	private PCLogOnInfoOfDaily pcLogOnInfoOfDaily;
	
	/* 日別実績の外出時間帯 */
	private OutingTimeOfDailyPerformance outingTimeOfDailyPerformance;
	
	/* 日別実績の出退勤 */
	private TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance;
	
	/* 日別実績の入退門 */
	private AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily;
	
	/* 日別実績の臨時出退勤 */
	private TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance;
}

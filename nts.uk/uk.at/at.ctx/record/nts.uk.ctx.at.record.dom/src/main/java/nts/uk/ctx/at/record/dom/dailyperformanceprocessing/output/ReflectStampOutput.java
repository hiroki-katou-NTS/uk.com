package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

/**
 * 
 * @author dungdt 休日時打刻反映時間帯
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ReflectStampOutput {
	OutingTimeOfDailyPerformance outingTimeOfDailyPerformance;
	TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance;
	List<Stamp> lstStamp;
	TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance;
	AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily;
	PCLogOnInfoOfDaily pcLogOnInfoOfDaily;
	ShortTimeOfDailyPerformance shortTimeOfDailyPerformance;
	BreakTimeOfDailyPerformance breakTimeOfDailyPerformance;
	List<EmployeeDailyPerError> employeeDailyPerErrorList;
}

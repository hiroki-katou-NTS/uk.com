package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectShortWorkingOutPut;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

//短時間勤務時間帯を反映する
public interface ReflectShortWorkingTimeDomainService {
	public ReflectShortWorkingOutPut reflect(String empCalAndSumExecLogID, String companyId,GeneralDate date, String employeeId,  WorkInfoOfDailyAttendance WorkInfo, TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance);
}

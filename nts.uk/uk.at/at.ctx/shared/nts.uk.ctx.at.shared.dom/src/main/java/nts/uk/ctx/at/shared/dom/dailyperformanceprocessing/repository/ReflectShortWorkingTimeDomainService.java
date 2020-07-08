package nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.ReflectShortWorkingOutPut;

//短時間勤務時間帯を反映する
public interface ReflectShortWorkingTimeDomainService {
	public ReflectShortWorkingOutPut reflect(String empCalAndSumExecLogID, String companyId,GeneralDate date, String employeeId,  WorkInfoOfDailyAttendance WorkInfo, TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance);
}

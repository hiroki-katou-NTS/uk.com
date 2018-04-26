package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

//短時間勤務時間帯を反映する
public interface ReflectShortWorkingTimeDomainService {
	public ShortTimeOfDailyPerformance reflect(String companyId,GeneralDate date, String employeeId,  WorkInfoOfDailyPerformance WorkInfo, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance);
}

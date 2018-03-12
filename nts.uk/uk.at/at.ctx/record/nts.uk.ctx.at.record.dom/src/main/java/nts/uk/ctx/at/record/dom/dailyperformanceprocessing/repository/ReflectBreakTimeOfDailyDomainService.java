package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectTimezoneOutput;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;

public interface ReflectBreakTimeOfDailyDomainService {
		//就業時間帯の休憩時間帯を日別実績に反映する
		public void reflectBreakTime(String companyId,List<TimeLeavingWork> timeLeavingWorks, WorkInfoOfDailyPerformance WorkInfo);
}

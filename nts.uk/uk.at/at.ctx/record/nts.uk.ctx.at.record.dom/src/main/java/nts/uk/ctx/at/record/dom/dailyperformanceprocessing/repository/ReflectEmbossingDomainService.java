package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
//打刻を反映する
public interface ReflectEmbossingDomainService {

	public ReflectStampOutput reflectStamp(WorkInfoOfDailyPerformance WorkInfo,
			TimeLeavingOfDailyPerformance timeDailyPer, List<Stamp> lstStamp, StampReflectRangeOutput s,
			GeneralDate date, String employeeId, String companyId);
}

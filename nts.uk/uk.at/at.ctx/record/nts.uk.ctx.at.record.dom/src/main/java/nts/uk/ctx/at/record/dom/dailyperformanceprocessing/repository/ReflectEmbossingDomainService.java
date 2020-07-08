package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;
//打刻を反映する
public interface ReflectEmbossingDomainService {

	/**
	 * 打刻を反映する
	 * «DomainService» 打刻データを日別勤怠に仮反映する
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.作成用クラス.日別作成WORK.打刻データを日別勤怠に仮反映する
	 * @param WorkInfo
	 * @param timeDailyPer
	 * @param lstStamp
	 * @param s
	 * @param date
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	public ReflectStampOutput reflectStamp(WorkInfoOfDailyPerformance WorkInfo,
			TimeLeavingOfDailyPerformance timeDailyPer, List<Stamp> lstStamp, StampReflectRangeOutput s,
			GeneralDate date, String employeeId, String companyId);
}

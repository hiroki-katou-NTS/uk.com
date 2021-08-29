package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * DS : 打刻データがいつの日別実績に反映するか
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻データがいつの日別実績に反映するか
 * @author tutk
 *
 */
public class ReflectDataStampDailyService {
	/**
	 * [1] 判断する
	 * @param require
	 * @param employeeId
	 * @param stamp
	 * @return 反映対象日
	 */
	public static Optional<GeneralDate> getJudgment(Require require,  String cid, String employeeId,Stamp stamp) {
		GeneralDate date = stamp.getStampDateTime().toDate();
		DatePeriod period = new DatePeriod(date.addDays(-2), date.addDays(1));
		return period.stream().filter(c-> reflectTemporarily(require, cid, employeeId, c, stamp)).findFirst();
	}
	/**
	 * 	[prv-1] 日別実績を仮反映する
	 * @param require
	 * @param employeeId
	 * @param date
	 * @param stamp
	 * @return
	 */
	private static boolean reflectTemporarily(Require require, String cid, String employeeId, GeneralDate date, Stamp stamp) {
		Optional<IntegrationOfDaily> dom = StampDataReflectProcessService.updateStampToDaily(require, cid, employeeId, date, stamp);
		return dom.map(x -> stamp.isReflectedCategory()).orElse(false);
	}

	public static interface Require extends StampDataReflectProcessService.Require2 {
		
	}
	
}

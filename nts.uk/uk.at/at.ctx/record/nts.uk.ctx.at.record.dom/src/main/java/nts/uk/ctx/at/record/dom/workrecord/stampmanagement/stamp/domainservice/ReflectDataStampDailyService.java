package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;

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
	public static Optional<GeneralDate> getJudgment(Require require,String employeeId,Stamp stamp) {
		GeneralDate date = stamp.getStampDateTime().toDate();
		DatePeriod period = new DatePeriod(date.addDays(-2), date.addDays(1));
		return period.stream().filter(c-> reflectTemporarily(require, employeeId, c, stamp)).findFirst();
	}
	/**
	 * 	[prv-1] 日別実績を仮反映する
	 * @param require
	 * @param employeeId
	 * @param date
	 * @param stamp
	 * @return
	 */
	private static boolean reflectTemporarily(Require require,String employeeId,GeneralDate date,Stamp stamp) {
		//	$日別実績 = require.勤務情報を反映する(社員ID, 年月日, しない, NULL)
		
		//	$打刻反映範囲 = require.打刻反映時間帯を取得する($日別実績.日別実績の勤務情報)

		//	$反映後の打刻 = require.打刻を反映する($日別実績, $打刻反映範囲, 打刻)
		
		return false;
	}
	
	
	public static interface Require {

		//	[R-1] 勤務情報を反映する
		
		//	[R-2] 打刻反映時間帯を取得する
		
		//	[R-3] 打刻を反映する
		
		//TODO: các require đang k giống trong code, chờ bug http://192.168.50.4:3000/issues/109911
	}
}

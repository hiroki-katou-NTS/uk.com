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
<<<<<<< HEAD
	private static boolean reflectTemporarily(Require require, String employeeId, GeneralDate date, Stamp stamp) {
		
		//	$日別実績 = require.日別実績を作成する(社員ID, 年月日, しない, empty, empty, empty)
		OutputCreateDailyOneDay dailyOneDay = require.createDailyResult(
				employeeId,
				date,
				ExecutionTypeDaily.CREATE,
				EmbossingExecutionFlag.ALL,
				new EmployeeGeneralInfoImport(),
				new PeriodInMasterList(),
				createNull(employeeId, date));
		
		if (!dailyOneDay.getListErrorMessageInfo().isEmpty()){
			return false;
		}
		
		//	$打刻反映範囲 = require.打刻反映時間帯を取得する($日別実績.日別実績の勤務情報)
		OutputTimeReflectForWorkinfo forWorkinfo = require.get(
				employeeId,
				date,
				dailyOneDay.getIntegrationOfDaily().getWorkInformation());
		
		// 	$変更区分 = 日別勤怠の何が変更されたか#日別勤怠の何が変更されたか(true, true, true, true)	
		ChangeDailyAttendance changeDailyAtt = new  ChangeDailyAttendance(true,
				true, 
				true,
				true,
				ScheduleRecordClassifi.RECORD,
				true);

		//	$反映後の打刻 = require.打刻を反映する($日別実績, $打刻反映範囲, 打刻)
		List<ErrorMessageInfo> errorMessageInfos = require.reflectStamp(stamp,
				forWorkinfo.getStampReflectRangeOutput(),
				dailyOneDay.getIntegrationOfDaily(),
				changeDailyAtt);
		
		return stamp.getImprintReflectionStatus().isReflectedCategory();
=======
	private static boolean reflectTemporarily(Require require, String cid, String employeeId, GeneralDate date, Stamp stamp) {
		Optional<IntegrationOfDaily> dom = StampDataReflectProcessService.updateStampToDaily(require, cid, employeeId, date, stamp);
		return dom.map(x -> stamp.isReflectedCategory()).orElse(false);
>>>>>>> pj/at/release_ver4
	}

	public static interface Require extends StampDataReflectProcessService.Require2 {
		
	}
	
}

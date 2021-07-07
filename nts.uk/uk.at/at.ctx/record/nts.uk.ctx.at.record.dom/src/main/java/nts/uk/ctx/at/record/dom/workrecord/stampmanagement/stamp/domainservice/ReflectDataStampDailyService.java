package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

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
	public static Optional<GeneralDate> getJudgment(Require require,  String employeeId,Stamp stamp) {
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
		
		return stamp.isReflectedCategory();
	}

	public static interface Require {

		// [R-1] 日別実績を作成する
		OutputCreateDailyOneDay createDailyResult(String employeeId, GeneralDate ymd,
				ExecutionTypeDaily executionType, EmbossingExecutionFlag flag,
				EmployeeGeneralInfoImport employeeGeneralInfoImport, PeriodInMasterList periodInMasterList,IntegrationOfDaily integrationOfDaily);

		// [R-2] 打刻反映時間帯を取得する
		OutputTimeReflectForWorkinfo get(String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation);	

		// [R-3] 打刻を反映する
		List<ErrorMessageInfo> reflectStamp(Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt);

		// TODO: các require đang k giống trong code, chờ bug
		// http://192.168.50.4:3000/issues/109911
	}
	
	private static IntegrationOfDaily createNull(String sid, GeneralDate dateData) {
			return new IntegrationOfDaily(
					sid,
					dateData,
					null, 
					null, 
					null,
					Optional.empty(), 
					new ArrayList<>(), 
					Optional.empty(), 
					new BreakTimeOfDailyAttd(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					new ArrayList<>(),
					Optional.empty(),
					new ArrayList<>(),
					Optional.empty());
		}
}

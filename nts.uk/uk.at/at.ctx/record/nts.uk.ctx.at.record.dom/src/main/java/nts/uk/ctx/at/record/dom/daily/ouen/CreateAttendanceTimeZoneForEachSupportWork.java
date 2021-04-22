package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ExecutionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * DS: 	応援作業別勤怠時間帯を作成する	
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別時間帯別実績.応援作業別勤怠時間帯を作成する
 * @author ThanhPV
 */

public class CreateAttendanceTimeZoneForEachSupportWork {
	
//■Public
	/**
	 * 	[prv-1] 編集状態を作成する
	 * @input require
	 * @input empId 社員ID	
	 * @input ymd 年月日
	 * @input ouenWorkTimeSheetOfDailyAttendance 作業時間帯
	 * @output 計算結果 	Optional<日別勤怠(Work)>
	 */
	public static void create(Require require, String empId, GeneralDate ymd, List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance) {
	
	}
	
//■Private
	/**
	 * @name [prv-1] 総労働時間を超えているかチェックする
	 * @input require
	 * @input employeeDailyPerError	エラー一覧	List<社員の日別実績エラー一覧>
	 */
	private static void checkWorkingHours(Require require, GeneralDate date, List<EmployeeDailyPerError> employeeDailyPerError) {
	}
	
	/**
	 * @name	[prv-2] 退勤時刻をセットする
	 * @input empId 社員ID
	 * @input ymd 年月日
	 * @input workDetailsParam  作業詳細	
	 */
	private static OuenWorkTimeSheetOfDailyAttendance CreateNewSupportWorkTimeZone(Require require, String empId, GeneralDate ymd, WorkDetailsParam workDetailsParam) {
		//	$職場ID = require.所属職場を取得する(社員ID,年月日)
		String workplateID = require.getAffWkpHistItemByEmpDate(empId, ymd);
		return OuenWorkTimeSheetOfDailyAttendance.create(workDetailsParam.getSupportFrameNo().v(), 
				WorkContent.create("", workDetailsParam.getWorkGroup(), WorkplaceOfWorkEachOuen.create(workplateID, workDetailsParam.getWorkLocationCD().orElse(null))), 
				TimeSheetOfAttendanceEachOuenSheet.create(null, workDetailsParam.getTimeZone().getStart(), workDetailsParam.getTimeZone().getEnd()));
	}
	
//■Require
	public static interface Require {
		//[R-1] 応援作業別勤怠時間帯を取得する
		//日別実績の応援作業別勤怠時間帯Repository.取得する(社員ID,年月日,作業詳細.応援勤務枠No)	
		Optional<OuenWorkTimeOfDaily> find(String empId, GeneralDate ymd, int workNo);
		//[R-2] 所属職場を取得する
		//所属職場履歴Adapter.取得する(社員ID,年月日)	
		String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);
	}

}

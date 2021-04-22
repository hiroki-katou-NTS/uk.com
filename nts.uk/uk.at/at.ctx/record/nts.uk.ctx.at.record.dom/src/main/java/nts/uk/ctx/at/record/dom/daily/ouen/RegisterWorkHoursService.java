package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

/**
 * DS: 		作業工数を登録する	
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別時間帯別実績.作業工数を登録する	
 * @author ThanhPV
 */

public class RegisterWorkHoursService {
	
//■Public
	/**
	 * 		[1] 登録する
	 * @input require 
	 * @input empId 社員ID	
	 * @input ymd 年月日
	 * @input editStateOfDailyAttd 編集状態	
	 * @input workDetailsParams 作業詳細一覧
	 * @output AtomTask
	 */
	public static AtomTask register(Require require, String empId, GeneralDate ymd, EditStateSetting editStateSetting, List<WorkDetailsParam> workDetailsParams) {
		List<AtomTask> atomTasks = new ArrayList<>();
		
		//if 作業詳細一覧.isPresent
		if(!workDetailsParams.isEmpty()) {
			//$作業時間帯 = 応援作業別勤怠時間帯を作成する#作成する(require,社員ID,年月日,作業詳細一覧)
			OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDailyAttendance = CreateAttendanceTimeZoneForEachSupportWork.create(require, empId, ymd, workDetailsParams.get(0));
			//$計算結果 = 応援作業別勤怠時間を計算する#計算する(require,社員ID,年月日,$作業時間帯)
			Optional<IntegrationOfDaily> integrationOfDaily = CalculateAttendanceTimeBySupportWorkService.calculate(require, empId, ymd, Arrays.asList(ouenWorkTimeSheetOfDailyAttendance));
		//List<AtomTask> $登録対象	
		
		//$登録対象.add(応援作業別勤怠時間帯を登録する#登録する(require,社員ID,年月日,$作業時間帯,編集状態)
		atomTasks.add(RegisterOuenWorkTimeSheetOfDailyService.register(require, empId, ymd, Arrays.asList(ouenWorkTimeSheetOfDailyAttendance), editStateSetting));
		//	$登録対象.add(応援作業別勤怠時間を登録する#登録する(require,社員ID,年月日,$計算結果.応援時間)
		atomTasks.add(RegisterOuenWorkTimeOfDailyService.register(require, empId, ymd, integrationOfDaily.get());
		}
		return AtomTask.bundle(atomTasks);
	}
	
//■Private
//■Require
	public static interface Require extends CreateAttendanceTimeZoneForEachSupportWork.Require, CalculateAttendanceTimeBySupportWorkService.Require, RegisterOuenWorkTimeSheetOfDailyService.Require, RegisterOuenWorkTimeOfDailyService.Require {}
}

package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.ArrayList;
import java.util.Optional;

import nts.uk.ctx.at.schedule.dom.workschedule.EditStateOfDailyAttd;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkSchedule;

/**
 * シフトの編集状態を判断する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author Hieult
 *
 */
public class DeterEditStatusShiftService {
  

	/**
	 * [1] 判断する
	 * @param workSchedule
	 * @return 	シフトの編集状態	
	 */
	public ShiftEditState toDecide(WorkSchedule workSchedule){
		ShiftEditState result = new ShiftEditState();
		return result;
	}
	/**
	 * [prv-1] 日別編集状態を判断する		
	 * @param workTypeStatus
	 * @param workingHourStatus
	 * @return Optional<日別勤怠の編集状態>
	 */
	private Optional<EditStateOfDailyAttd> judgeDailyEditStatus(EditStateOfDailyAttd workTypeStatus,EditStateOfDailyAttd
			workingHourStatus){
		return null;
		
	}
	
}

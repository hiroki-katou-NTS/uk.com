package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

/**
 * シフトの編集状態を判断する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * 
 * @author Hieult
 * 
 *
 */
public class DeterEditStatusShiftService {

	/**
	 * [1] 判断する
	 * 
	 * @param workSchedule
	 * @return シフトの編集状態
	 */
	public static ShiftEditState toDecide(WorkSchedule workSchedule) {
		/*
		 * if 勤務予定.編集状態.isEmpty return ($社員ID, $年月日, Optional.empty())
		 */
		if (workSchedule.getLstEditState().isEmpty()) {
			return new ShiftEditState(workSchedule.getEmployeeID(), workSchedule.getYmd(), Optional.empty());
		}
		// $勤務種類状態 = 勤務予定.編集状態：find $.勤怠項目ID == 28
		// $就業時間帯状態 = 勤務予定.編集状態：find $.勤怠項目ID == 29
		Optional<EditStateOfDailyAttd> workTypeStatus = workSchedule.getLstEditState().stream()
				.filter(t -> t.getAttendanceItemId() == 28).findFirst();
		Optional<EditStateOfDailyAttd> workingHourStatus = workSchedule.getLstEditState().stream()
				.filter(t -> t.getAttendanceItemId() == 29).findFirst();

		// $シフトの編集状態 = [prv-1] 日別編集状態を判断する($勤務種類状態, $就業時間帯状態)
		Optional<EditStateSetting> editStateDaily = judgeDailyEditStatus(workTypeStatus, workingHourStatus);
		// return シフトの編集状態#シフトの編集状態(勤務予定.社員ID, 勤務予定.年月日, $シフトの編集状態)
		return new ShiftEditState(workSchedule.getEmployeeID(), workSchedule.getYmd(), editStateDaily);
	}

	/**
	 * [prv-1] 日別編集状態を判断する
	 * 
	 * @param workTypeStatus 勤務種類状態
	 * @param workingHourStatus 就業時間帯状態
	 * @return Optional<日別勤怠の編集状態>
	 */
	private static Optional<EditStateSetting> judgeDailyEditStatus(Optional<EditStateOfDailyAttd> workTypeStatus,
			Optional<EditStateOfDailyAttd> workingHourStatus) {
		
		if(!workTypeStatus.isPresent()) {
			if(!workingHourStatus.isPresent() || workingHourStatus.get().getEditStateSetting() == EditStateSetting.IMPRINT) {
				return Optional.empty();	
			}
			return Optional.of(workingHourStatus.get().getEditStateSetting());
		}
		
		if(!workingHourStatus.isPresent()) {
			if(workTypeStatus.get().getEditStateSetting() == EditStateSetting.IMPRINT) {
				return Optional.empty();
			}
			return Optional.of(workTypeStatus.get().getEditStateSetting());
		}
		val workTypeStt = workTypeStatus.get().getEditStateSetting();
		val workHourStt = workingHourStatus.get().getEditStateSetting();
		
		if(workTypeStt == EditStateSetting.IMPRINT && workHourStt == EditStateSetting.IMPRINT ) {
			return Optional.empty();
		}
		
		if(workTypeStt == EditStateSetting.REFLECT_APPLICATION || workHourStt == EditStateSetting.REFLECT_APPLICATION ) {
			return Optional.of(EditStateSetting.REFLECT_APPLICATION);
		}
		
		if(workTypeStt == EditStateSetting.HAND_CORRECTION_OTHER || workHourStt == EditStateSetting.HAND_CORRECTION_OTHER ) {
			return Optional.of(EditStateSetting.HAND_CORRECTION_OTHER);
		}
		
		return Optional.of(EditStateSetting.HAND_CORRECTION_MYSELF);
		
	}

}

package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author ThanhNX
 *
 *         実績の勤務情報を予定の勤務情報へコピー
 */
public class CopyActualToScheduleWorkInfo {

	// 実績の勤務情報を予定の勤務情報へコピー
	public static void copy(WorkInfoOfDailyAttendance workInformation, List<EditStateOfDailyAttd> editState) {
		WorkInformation record = workInformation.getRecordInfo();
		
		// 勤務情報の値をコピー
		if (workInformation.getScheduleInfo() == null) {	
			workInformation.setScheduleInfo(new WorkInformation(record.getWorkTypeCode().v(), record.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null)));
		} else {
			workInformation.getScheduleInfo().setWorkTypeCode(record.getWorkTypeCode().v());
			workInformation.getScheduleInfo().setWorkTimeCode(record.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null));
		}

		// 実績の勤務情報の編集状態を予定の勤務情報へ引き継ぐ
		List<EditStateOfDailyAttd> editStateWorkInfo = editState.stream()
				.filter(x -> x.getAttendanceItemId() == 28 || x.getAttendanceItemId() == 29)
				.collect(Collectors.toList());

		editState.removeIf(x -> x.getAttendanceItemId() == 28 || x.getAttendanceItemId() == 29);
		editState.addAll(editStateWorkInfo.stream().map(x -> {
			return new EditStateOfDailyAttd(x.getAttendanceItemId() - 27,
					x.getEditStateSetting());
		}).collect(Collectors.toList()));
		// 日別実績の勤務情報と編集状態を返す
		return;
	}
}

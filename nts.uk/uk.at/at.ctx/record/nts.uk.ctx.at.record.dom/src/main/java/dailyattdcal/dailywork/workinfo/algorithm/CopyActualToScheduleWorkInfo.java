package dailyattdcal.dailywork.workinfo.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * @author ThanhNX
 *
 *         実績の勤務情報を予定の勤務情報へコピー
 */
public class CopyActualToScheduleWorkInfo {

	// 実績の勤務情報を予定の勤務情報へコピー
	public static void copy(WorkInfoOfDailyPerformance workInformation, List<EditStateOfDailyPerformance> editState) {
		// 勤務情報の値をコピー
		if (workInformation.getRecordInfo() != null) {
			workInformation.setScheduleInfo(workInformation.getRecordInfo().clone());
		}

		// 実績の勤務情報の編集状態を予定の勤務情報へ引き継ぐ
		List<EditStateOfDailyPerformance> editStateWorkInfo = editState.stream()
				.filter(x -> x.getAttendanceItemId() == 28 || x.getAttendanceItemId() == 29)
				.collect(Collectors.toList());

		editState.removeIf(x -> x.getAttendanceItemId() == 28 || x.getAttendanceItemId() == 29);
		
		editState.addAll(editStateWorkInfo.stream().map(x -> {
			return new EditStateOfDailyPerformance(x.getEmployeeId(), x.getAttendanceItemId() - 27, x.getYmd(),
					x.getEditStateSetting());
		}).collect(Collectors.toList()));
		
		// 日別実績の勤務情報と編集状態を返す
		return;
	}
}

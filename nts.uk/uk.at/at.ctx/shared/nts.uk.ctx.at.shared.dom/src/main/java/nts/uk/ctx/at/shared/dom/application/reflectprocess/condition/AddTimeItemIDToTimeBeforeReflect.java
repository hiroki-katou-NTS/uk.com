package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.AttendanceBeforeApplicationReflect;

/**
 * @author thanh_nx
 *
 *         勤怠項目IDを反映前の勤怠一覧に追加する
 */
public class AddTimeItemIDToTimeBeforeReflect {

	public static void addTime(DailyRecordOfApplication dailyApp, List<Integer> lstItemId) {
		
		lstItemId.stream().forEach(id -> {
			Optional<AttendanceBeforeApplicationReflect> itemOpt = dailyApp.getAttendanceBeforeReflect().stream()
					.filter(x -> x.getAttendanceId() == id).findFirst();
			if (itemOpt.isPresent()) {
				itemOpt.get().setValue("");
			} else {
				dailyApp.getAttendanceBeforeReflect()
						.add(new AttendanceBeforeApplicationReflect(id, "", Optional.empty()));
			}
		});
	}

}

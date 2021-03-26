package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.groupappabsence.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author thanh_nx
 *
 *         出退勤の削除
 */
public class DeleteAttendanceProcess {

	public static DailyAfterAppReflectResult process(Require require, Optional<WorkTypeCode> workTypeCode,
			DailyRecordOfApplication dailyApp) {

		List<Integer> itemId = new ArrayList<>();
		if (!workTypeCode.isPresent()) {
			return new DailyAfterAppReflectResult(dailyApp, itemId);
		}

		// 1日半日出勤・1日休日系の判定（休出判定あり）
		Optional<WorkType> workType = require.getWorkType(workTypeCode.get().v());
		if (!workType.isPresent())
			return new DailyAfterAppReflectResult(dailyApp, itemId);
		AttendanceDayAttr dayAttr = workType.get().chechAttendanceDay();

		// 取得した[出勤日区分]をチェック
		if (dayAttr == AttendanceDayAttr.HOLIDAY) {
			// 日別引退(work）の出退勤（List）でループ
			dailyApp.getAttendanceLeave().ifPresent(x -> {
				x.getTimeLeavingWorks().forEach(y -> {
					// [日別勤怠(work）の出退勤]をクリアする
					y.cleanTime(dailyApp.getClassification());
					itemId.add(CancelAppStamp.createItemId(31, y.getWorkNo().v(), 10));
					itemId.add(CancelAppStamp.createItemId(34, y.getWorkNo().v(), 10));
				});
			});

		}

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, itemId);
		return new DailyAfterAppReflectResult(dailyApp, itemId);
	}

	public static interface Require {

		Optional<WorkType> getWorkType(String workTypeCd);

	}
}

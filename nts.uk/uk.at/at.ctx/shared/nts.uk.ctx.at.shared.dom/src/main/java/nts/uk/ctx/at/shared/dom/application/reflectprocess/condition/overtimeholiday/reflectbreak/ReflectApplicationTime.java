package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday.ReflectHolidayDetail;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem.ReflectionSalaryTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem.SpecialDaySalaryTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.ReflectOvertimeDetail;

/**
 * @author thanh_nx
 *
 *         申請時間の反映
 */
public class ReflectApplicationTime {

	public static void process(List<OvertimeApplicationSettingShare> applicationTimes,
			DailyRecordOfApplication dailyApp, Optional<ReflectAppDestination> reflectAppDes) {

		List<Integer> lstId = new ArrayList<Integer>();

		// 勤怠種類ごとの申請時間(work）に、申請時間をセットする
		applicationTimes.forEach(overtime -> {

			// [申請時間(work）. 勤怠種類]をチェック
			switch (overtime.getAttendanceType()) {
			case NORMALOVERTIME:
				// 残業時間の反映
				if (reflectAppDes.isPresent())
					lstId.addAll(ReflectOvertimeDetail.process(overtime, dailyApp, reflectAppDes.get()));
				break;

			case BREAKTIME:
				// 休出時間の反映
				if (reflectAppDes.isPresent())
					lstId.addAll(ReflectHolidayDetail.process(overtime, dailyApp, reflectAppDes.get()));
				break;

			case BONUSPAYTIME:
				// 加給時間の反映
				lstId.addAll(ReflectionSalaryTime.process(overtime, dailyApp));
				break;

			case BONUSSPECIALDAYTIME:
				// 特定日加給時間の反映
				lstId.addAll(SpecialDaySalaryTime.process(overtime, dailyApp));
				break;

			default:
				break;
			}

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstId);

	}

}

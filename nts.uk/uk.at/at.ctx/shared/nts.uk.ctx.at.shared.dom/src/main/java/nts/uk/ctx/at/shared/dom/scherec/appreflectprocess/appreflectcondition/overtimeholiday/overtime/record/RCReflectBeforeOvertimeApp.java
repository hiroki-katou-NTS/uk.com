package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.overtime.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak.ReflectBreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事前残業申請の反映（勤務実績）
 */
public class RCReflectBeforeOvertimeApp {

	public static void process(Require require, AppOverTimeShare overTimeApp, DailyRecordOfApplication dailyApp,
			BeforeOtWorkAppReflect reflectOvertimeBeforeSet) {

		List<Integer> lstId = new ArrayList<Integer>();
		// [勤務情報、始業終業を反映する]をチェック
		if (reflectOvertimeBeforeSet.getReflectWorkInfoAtr() == NotUseAtr.USE) {

			// 勤務情報を勤務情報DTOへセット
			WorkInfoDto workInfoDto = overTimeApp.getWorkInfoOp().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));

			// 勤務情報の反映
			lstId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true)));

			// 予定出退勤の反映
			lstId.addAll(ReflectAttendance.reflect(overTimeApp.getWorkHoursOp(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(true), Optional.of(true)));

		}

		// 事前残業時間の反映 
		ReflectApplicationTime.process(overTimeApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.SCHEDULE));

		// [残業時間を実績項目へ反映する]をチェック
		if (reflectOvertimeBeforeSet.getReflectActualOvertimeHourAtr() == NotUseAtr.USE) {
			// 残業時間の反映
			ReflectApplicationTime.process(overTimeApp.getApplicationTime().getApplicationTime(), dailyApp,
					Optional.of(ReflectAppDestination.RECORD));
		}

		// 休憩の申請反映
		ReflectBreakApplication.process(overTimeApp.getBreakTimeOp(), dailyApp,
				reflectOvertimeBeforeSet.getBreakLeaveApplication());

	}

	public static interface Require extends ReflectWorkInformation.Require {

	}

}

package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.groupappabsence.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         休暇系申請の反映（勤務実績）
 */
public class RCReflectGroupApplyForLeaveApp {

	public static DailyAfterAppReflectResult process(Require require, WorkInformation workInfo,
			List<TimeZoneWithWorkNo> workingHours, PrePostAtrShare prePostAtr, NotUseAtr workChangeUse,
			DailyRecordOfApplication dailyApp, VacationAppReflectOption workAttendanceReflect) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 勤務情報]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.ofNullable(workInfo.getWorkTypeCode()),
				workInfo.getWorkTimeCodeNotNull());

		// [就業時間帯を反映する]をチェック
		boolean reflectWorkTime = false;
		// [就業時間帯を反映する]をチェック
		if (workAttendanceReflect.getReflectWorkHour() != ReflectWorkHourCondition.NOT_REFLECT) {
			// 変数[就業時間帯を反映する]に、[input. 就業時間帯を変更する]をセット
			reflectWorkTime = workChangeUse == NotUseAtr.USE;
		}

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(reflectWorkTime)));

		// 始業終業の反映
		lstItemId.addAll(ReflectStartEndWork.reflect(dailyApp, workingHours, prePostAtr));

		// [出退勤を反映する]をチェック
		if (workAttendanceReflect.getReflectAttendance() == NotUseAtr.USE) {
			// 出退勤の反映
			lstItemId.addAll(ReflectAttendance.reflect(workingHours, ScheduleRecordClassifi.RECORD, dailyApp,
					Optional.of(true), Optional.of(true)));
		}

		// [1日休暇の場合は出退勤を削除]をチェック
		if (workAttendanceReflect.getOneDayLeaveDeleteAttendance() == NotUseAtr.USE) {

			// 出退勤の削除
			val resultClean = DeleteAttendanceProcess.process(require, workInfoDto.getWorkTypeCode(), dailyApp);
			lstItemId.addAll(resultClean.getLstItemId());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	public static interface Require extends ReflectWorkInformation.Require, DeleteAttendanceProcess.Require {

	}
}

package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author ThanhNX
 *
 *         勤務種類に応じて就業時間帯の補正を行う
 */
@Stateless
public class CorrectWorkTimeByWorkType {

	@Inject
	private BasicScheduleService basicScheduleService;

	// 勤務種類に応じて就業時間帯の補正を行う
	public void correct(WorkingConditionItem workCondItem, WorkInfoOfDailyAttendance workInfo) {

		// 就業時間帯の必須チェック
		SetupType setupType = basicScheduleService
				.checkNeededOfWorkTimeSetting(workInfo.getRecordInfo().getWorkTypeCode().v());

		if (setupType == SetupType.NOT_REQUIRED) {

			// 就業時間帯コード←null
			workInfo.getRecordInfo().removeWorkTimeInHolydayWorkType();;
		}

		if (setupType == SetupType.REQUIRED && workInfo.getRecordInfo().getWorkTimeCode() == null) {
			// 就業時間帯コード＝nullの確認
			// 平日時の就業時間帯をセットする
			workInfo.getRecordInfo()
					.setWorkTimeCode(workCondItem.getWorkCategory().getWeekdayTime().getWorkTimeCode().orElse(null));
		}

		// 日別実績の勤務情報を返す
		return;

	}

}

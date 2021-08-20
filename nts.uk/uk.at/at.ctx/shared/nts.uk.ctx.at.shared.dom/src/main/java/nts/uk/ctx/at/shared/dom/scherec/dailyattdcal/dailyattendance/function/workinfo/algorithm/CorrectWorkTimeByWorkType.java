package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
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
	public Pair<ChangeDailyAttendance, IntegrationOfDaily> correct(WorkingConditionItem workCondItem,
			IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {

		// 就業時間帯の必須チェック
		SetupType setupType = basicScheduleService
				.checkNeededOfWorkTimeSetting(domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v());

		if (setupType == SetupType.NOT_REQUIRED) {

			// 就業時間帯コード←null
			domainDaily.getWorkInformation().getRecordInfo().removeWorkTimeInHolydayWorkType();
			// 勤務情報が変更された情報を記録
			changeAtt.setWorkInfo(true);
		}

		if (setupType == SetupType.REQUIRED
				&& domainDaily.getWorkInformation().getRecordInfo().getWorkTimeCode() == null) {
			// 就業時間帯コード＝nullの確認
			// 平日時の就業時間帯をセットする
			domainDaily.getWorkInformation().getRecordInfo()
					.setWorkTimeCode(workCondItem.getWorkCategory().getWorkTime().getWeekdayTime().getWorkTimeCode().orElse(null));
			// 勤務情報が変更された情報を記録
			changeAtt.setWorkInfo(true);
		}

		// 日別実績の勤務情報を返す
		return Pair.of(changeAtt, domainDaily);

	}

}

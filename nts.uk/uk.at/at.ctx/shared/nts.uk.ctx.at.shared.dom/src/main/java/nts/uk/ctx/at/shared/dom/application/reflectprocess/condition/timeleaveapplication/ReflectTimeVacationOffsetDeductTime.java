package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         控除時間と相殺する時間休暇を反映
 */
public class ReflectTimeVacationOffsetDeductTime {

	public static List<Integer> process(List<TimeLeaveApplicationDetailShare> appTimeLeavDetail,
			DailyRecordOfApplication dailyApp, TimeLeaveAppReflectCondition condition,
			NotUseAtr reflectActualTimeZone) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// input.時間休暇申請詳細（List）でループ
		for (TimeLeaveApplicationDetailShare detail : appTimeLeavDetail) {

			// 時間休暇の申請反映条件チェック
			TimeDigestApplicationShare timeDigest = CheckConditionReflectAppTimeLeave
					.check(detail.getTimeDigestApplication(), condition);

			// 時間休暇の時間消化の反映 
			lstItemId.addAll(ReflectTimeDigestTimeVacation.process(dailyApp, detail.getAppTimeType(), timeDigest));

			// [日別勤怠(work）.予定実績区分]をチェックする
			if (dailyApp.getClassification() == ScheduleRecordClassifi.RECORD
					&& reflectActualTimeZone == NotUseAtr.NOT_USE) {
				continue;
			}

			// 時間休暇時間帯の反映
			lstItemId.addAll(ReflectTimeVacationTimeZone.process(detail.getAppTimeType(),
					detail.getTimeZoneWithWorkNoLst(), dailyApp));

		}

		return lstItemId;
	}
}

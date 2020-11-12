package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author thanh_nx
 *
 *         勤務情報の反映
 */
public class ReflectWorkInformation {

	public static List<Integer> reflectInfo(Require require, WorkInfoDto workInfo, DailyRecordOfApplication dailyApp,
			Optional<Boolean> changeWorkType, Optional<Boolean> changeWorkTime) {

		List<Integer> lstItemId = new ArrayList<>();
		if (changeWorkType.orElse(false) || changeWorkTime.orElse(false)) {
			// [input. 勤務種類を反映する]をチェック
			if (changeWorkType.orElse(false)) {

				// 勤怠項目ID一覧 = [勤務種類コード]、[振休振出として扱う区分]、[振休振出として扱う日数]に該当する勤怠項目ID
				lstItemId.addAll(Arrays.asList(1, 1292, 1293));

			}

			// [input. 就業時間帯を反映する]をチェック
			if (changeWorkTime.orElse(false)) {

				// 勤怠項目ID一覧 = [就業時間帯コード]に該当する勤怠項目ID
				lstItemId.addAll(Arrays.asList(2));
			}
			
			// 勤務情報と始業終業を変更する
			dailyApp.getWorkInformation().changeWorkSchedule(require,
					new WorkInformation(workInfo.getWorkTypeCode().orElse(null), 
										workInfo.getWorkTimeCode().orElse(null)), 
					changeWorkType.orElse(false), changeWorkTime.orElse(false));
			
			if(!lstItemId.isEmpty()) {

				/// 申請反映状態にする
				UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
			}
		}
		
		return lstItemId;
	}

	public static interface Require extends WorkInfoOfDailyAttendance.Require {

	}
}

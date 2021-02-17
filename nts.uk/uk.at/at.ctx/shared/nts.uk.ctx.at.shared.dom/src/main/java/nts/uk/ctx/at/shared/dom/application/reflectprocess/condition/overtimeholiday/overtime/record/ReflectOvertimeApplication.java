package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         残業申請の反映（勤務実績）
 */
public class ReflectOvertimeApplication {

	public static List<Integer> process(Require require, String cid, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp, OtWorkAppReflect reflectOvertimeSet) {

		List<Integer> lstId = new ArrayList<Integer>();
		// [実績の勤務情報へ反映する]をチェック
		if (reflectOvertimeSet.getReflectActualWorkAtr() == NotUseAtr.USE) {
			// [勤務情報]を勤務情報DTOへセット
			WorkInfoDto workInfoDto = overTimeApp.getWorkInfoOp().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));

			// 勤務情報の反映
			lstId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true)));
		}

		// [input. 残業申請. 事前事後区分]をチェック
		if (overTimeApp.getPrePostAtr() == PrePostAtrShare.PREDICT) {
			// 事前残業申請の反映
			RCReflectBeforeOvertimeApp.process(require, overTimeApp, dailyApp, reflectOvertimeSet.getBefore());
		}

		if (overTimeApp.getPrePostAtr() == PrePostAtrShare.POSTERIOR) {
			// 事後残業申請の反映
			RCReflectAfterOvertimeApp.process(require, cid, overTimeApp, dailyApp, reflectOvertimeSet.getAfter());
		}

		return lstId;
	}

	public static interface Require extends ReflectWorkInformation.Require, RCReflectBeforeOvertimeApp.Require,
			RCReflectAfterOvertimeApp.Require {

	}
}

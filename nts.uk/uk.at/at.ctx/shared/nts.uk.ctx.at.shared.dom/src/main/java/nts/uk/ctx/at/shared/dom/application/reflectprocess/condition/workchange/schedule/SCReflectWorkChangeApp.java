package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectWorkChangeApplication;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         勤務変更申請の反映（勤務予定）
 */
public class SCReflectWorkChangeApp {

	public static Collection<Integer> reflect(Require require, AppWorkChangeShare appWorkChange,
			DailyRecordOfApplication dailyApp, ReflectWorkChangeApplication reflectWorkChange) {
		Set<Integer> lstItemId = new HashSet<Integer>();
		// ReflectWorkChangeApplication
		// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(appWorkChange.getOpWorkTypeCD(), appWorkChange.getOpWorkTimeCD());
		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp,
				Optional.of(appWorkChange.getOpWorkTypeCD().isPresent()),
				Optional.of(appWorkChange.getOpWorkTimeCD().isPresent())));

		// [出退勤を反映するか]をチェック
		if (reflectWorkChange.getReflectAttendance() == NotUseAtr.USE) {
			/// 出退勤の反映員 in process
			lstItemId.addAll(ReflectAttendance.reflect(appWorkChange.getTimeZoneWithWorkNoLst(),
					ScheduleRecordClassifi.SCHEDULE, dailyApp, Optional.of(true), Optional.of(true)));
		}

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appWorkChange.getStraightBack(),
				appWorkChange.getStraightGo()));

		return lstItemId;
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}

	@AllArgsConstructor
	@Data
	public static class WorkInfoDto {

		// 勤務種類コード
		private Optional<WorkTypeCode> workTypeCode;

		// 就業時間帯コード
		private Optional<WorkTimeCode> workTimeCode;

	}
}

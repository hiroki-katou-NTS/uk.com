package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.workrecord;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectWorkChangeApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         勤務変更申請の反映（勤務実績）
 */
public class RCReflectWorkChangeApp {

	public static Collection<Integer> reflect(Require require, AppWorkChangeShare appWorkChange,
			DailyRecordOfApplication dailyApp, ReflectWorkChangeApplication reflectWorkChange) {
		Set<Integer> lstItemId = new HashSet<>();
		// ReflectWorkChangeApplication
		// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(appWorkChange.getOpWorkTypeCD(), appWorkChange.getOpWorkTimeCD());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp,
				Optional.of(appWorkChange.getOpWorkTypeCD().isPresent()),
				Optional.of(appWorkChange.getOpWorkTimeCD().isPresent())));

		// 始業終業の反映
		lstItemId.addAll(ReflectStartEndWork.reflect(dailyApp, appWorkChange.getTimeZoneWithWorkNoLst(),
				appWorkChange.getPrePostAtr()));

		// [出退勤を反映するか]をチェック

		// [出退勤を反映するか]をチェック
		if (reflectWorkChange.getReflectAttendance() == NotUseAtr.USE) {
			/// 出退勤の反映
			lstItemId.addAll(ReflectAttendance.reflect(appWorkChange.getTimeZoneWithWorkNoLst(),
					ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(true), Optional.of(true)));
		}

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appWorkChange.getStraightBack(),
				appWorkChange.getStraightGo()));

		return lstItemId;
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}
}

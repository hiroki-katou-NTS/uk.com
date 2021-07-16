package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.substituteworkapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.RecruitmentAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.振出申請
 * 振出申請の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubstituteWorkAppReflect extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String cid;

	// 出退勤を反映する
	private NotUseAtr reflectAttendanceAtr;

	/**
	 * @author thanh_nx
	 *
	 *         振出申請の反映（勤務予定）
	 */
	public DailyAfterAppReflectResult reflectSC(RequireSC require, RecruitmentAppShare recruitApp,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<>();

		// 勤務情報を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.ofNullable(recruitApp.getWorkInformation().getWorkTypeCode()),
				recruitApp.getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// [出退勤を反映する]をチェック
		if (this.reflectAttendanceAtr == NotUseAtr.USE) {
			// 出退勤の反映
			lstItemId.addAll(ReflectAttendance.reflect(require, cid, recruitApp.getWorkingHours(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));
		}

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);

	}

	public static interface RequireSC extends ReflectWorkInformation.Require, ReflectAttendance.Require {

	}

	/**
	 * @author thanh_nx
	 *
	 *         振出申請の反映（勤務実績）
	 */
	public DailyAfterAppReflectResult reflectRC(RequireRC require, RecruitmentAppShare recruitApp,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<>();

		// 勤務情報を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.ofNullable(recruitApp.getWorkInformation().getWorkTypeCode()),
				recruitApp.getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// 始業終業の反映
		lstItemId.addAll(
				ReflectStartEndWork.reflect(require, cid, dailyApp, recruitApp.getWorkingHours()));

		// [出退勤を反映する]をチェック
		if (this.reflectAttendanceAtr == NotUseAtr.USE) {
			// 出退勤の反映
			lstItemId.addAll(ReflectAttendance.reflect(require, cid, recruitApp.getWorkingHours(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));
		}
		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	public static interface RequireRC extends ReflectWorkInformation.Require, ReflectStartEndWork.Require {

	}
}

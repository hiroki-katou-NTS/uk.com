package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.勤務変更申請.勤務変更申請の反映
 * 
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReflectWorkChangeApp implements DomainAggregate {

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * 出退勤を反映するか
	 */
	private NotUseAtr whetherReflectAttendance;

	/**
	 * @author thanh_nx
	 *
	 *         勤務変更申請の反映（勤務予定）
	 */

	public Collection<Integer> reflectSchedule(Require require, AppWorkChangeShare appWorkChange,
			DailyRecordOfApplication dailyApp) {
		Set<Integer> lstItemId = new HashSet<Integer>();
		// ReflectWorkChangeApplication
		// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(appWorkChange.getOpWorkTypeCD(), appWorkChange.getOpWorkTimeCD());
		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, companyID, workInfoDto, dailyApp,
				Optional.of(appWorkChange.getOpWorkTypeCD().isPresent()),
				Optional.of(appWorkChange.getOpWorkTimeCD().isPresent())));

		/// 出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(require, companyID, appWorkChange.getTimeZoneWithWorkNoLst(),
				ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(true), Optional.of(true),
				Optional.of(TimeChangeMeans.APPLICATION)));

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appWorkChange.getStraightBack(),
				appWorkChange.getStraightGo()));

		return lstItemId;
	}

	/**
	 * @author thanh_nx
	 *
	 *         勤務変更申請の反映（勤務実績）
	 */

	public Collection<Integer> reflectRecord(Require require, AppWorkChangeShare appWorkChange,
			DailyRecordOfApplication dailyApp) {
		Set<Integer> lstItemId = new HashSet<>();
		// ReflectWorkChangeApplication
		// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(appWorkChange.getOpWorkTypeCD(), appWorkChange.getOpWorkTimeCD());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require,  companyID, workInfoDto, dailyApp,
				Optional.of(appWorkChange.getOpWorkTypeCD().isPresent()),
				Optional.of(appWorkChange.getOpWorkTimeCD().isPresent())));

		// 始業終業の反映
		lstItemId.addAll(ReflectStartEndWork.reflect(require, companyID, dailyApp, appWorkChange.getTimeZoneWithWorkNoLst(),
				appWorkChange.getPrePostAtr()));

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appWorkChange.getStraightBack(),
				appWorkChange.getStraightGo()));

		return lstItemId;
	}

	public static interface Require extends ReflectWorkInformation.Require, ReflectStartEndWork.Require {

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

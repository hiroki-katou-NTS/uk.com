package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.groupappabsence.algorithm.DeleteAttendanceProcess;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * 休暇系申請の反映
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOption extends DomainObject {
    /**
     * 1日休暇の場合は出退勤を削除
     */
    private NotUseAtr oneDayLeaveDeleteAttendance;

    /**
     * 就業時間帯を反映する
     */
    private ReflectWorkHourCondition reflectWorkHour;
    
	/**
	 * @author thanh_nx
	 *
	 *         休暇系申請の反映（勤務実績）
	 */
	public DailyAfterAppReflectResult process(Require require, String cid, WorkInformation workInfo,
			List<TimeZoneWithWorkNo> workingHours, PrePostAtrShare prePostAtr, NotUseAtr workChangeUse,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 勤務情報]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.ofNullable(workInfo.getWorkTypeCode()),
				workInfo.getWorkTimeCodeNotNull());

		// [就業時間帯を反映する]をチェック
		boolean reflectWorkTime = false;
		// [就業時間帯を反映する]をチェック
		if (this.getReflectWorkHour() != ReflectWorkHourCondition.NOT_REFLECT) {
			// 変数[就業時間帯を反映する]に、[input. 就業時間帯を変更する]をセット
			reflectWorkTime = workChangeUse == NotUseAtr.USE;
		}

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(reflectWorkTime)));

		// 始業終業の反映
		lstItemId.addAll(ReflectStartEndWork.reflect(require, cid, dailyApp, workingHours));
		
		// [1日休暇の場合は出退勤を削除]をチェック
		if (this.getOneDayLeaveDeleteAttendance() == NotUseAtr.USE) {

			// 出退勤の削除
			val resultClean = DeleteAttendanceProcess.process(require, workInfoDto.getWorkTypeCode(), dailyApp);
			lstItemId.addAll(resultClean.getLstItemId());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	public static interface Require extends ReflectWorkInformation.Require, DeleteAttendanceProcess.Require,
			ReflectStartEndWork.Require, ReflectAttendance.Require {

	}
	
	/**
	 * @author thanh_nx
	 *
	 *         休暇系申請の反映（勤務予定）
	 */
	public DailyAfterAppReflectResult processSC(RequireSC require, String cid, WorkInformation workInfo,
			List<TimeZoneWithWorkNo> workingHours, NotUseAtr workChangeUse, DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 勤務情報]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.ofNullable(workInfo.getWorkTypeCode()),
				workInfo.getWorkTimeCodeNotNull());

		boolean reflectWorkTime = false;
		// [就業時間帯を反映する]をチェック
		if (this.getReflectWorkHour() != ReflectWorkHourCondition.NOT_REFLECT) {
			/// 変数[就業時間帯を反映する]に、[input. 就業時間帯を変更する]をセット
			reflectWorkTime = workChangeUse == NotUseAtr.USE;
		}

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(reflectWorkTime)));

		// 出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(require, cid, workingHours, ScheduleRecordClassifi.RECORD, dailyApp,
				Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	public static interface RequireSC extends ReflectWorkInformation.Require, ReflectAttendance.Require {

	}
}

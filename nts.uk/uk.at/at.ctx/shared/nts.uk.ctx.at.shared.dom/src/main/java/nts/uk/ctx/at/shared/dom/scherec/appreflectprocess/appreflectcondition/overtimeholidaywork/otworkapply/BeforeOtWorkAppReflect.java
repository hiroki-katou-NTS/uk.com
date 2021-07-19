package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.ReflectFlexTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 事前残業申請の反映
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BeforeOtWorkAppReflect {

	/**
	 * 休憩・外出を申請反映する
	 */
	private BreakApplication breakLeaveApplication;
	/**
	 * 勤務情報、出退勤を反映する
	 */
	private NotUseAtr reflectWorkInfoAtr;

	/**
	 * 残業時間を実績項目へ反映する
	 */
	private NotUseAtr reflectActualOvertimeHourAtr;

	public static BeforeOtWorkAppReflect create(int reflectWorkInfo, int reflectActualOvertimeHour,
			int reflectBeforeBreak) {
		return new BeforeOtWorkAppReflect(
				new BreakApplication(EnumAdaptor.valueOf(reflectBeforeBreak, NotUseAtr.class)),
				EnumAdaptor.valueOf(reflectWorkInfo, NotUseAtr.class),
				EnumAdaptor.valueOf(reflectActualOvertimeHour, NotUseAtr.class));
	}

	/**
	 * @author thanh_nx
	 *
	 *         事前残業申請の反映（勤務実績）
	 */

	public void processRC(Require require,  String cid, AppOverTimeShare overTimeApp, DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// [勤務情報、始業終業を反映する]をチェック
		if (this.getReflectWorkInfoAtr() == NotUseAtr.USE) {

			// 勤務情報を勤務情報DTOへセット
			WorkInfoDto workInfoDto = overTimeApp.getWorkInfoOp().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));

			// 勤務情報の反映
			lstId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true)));

			// 予定出退勤の反映
			lstId.addAll(ReflectAttendance.reflect(require, cid, overTimeApp.getWorkHoursOp(), ScheduleRecordClassifi.SCHEDULE,
					dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));

		}

		// 事前残業時間の反映
		ReflectApplicationTime.process(overTimeApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.SCHEDULE));

		// [残業時間を実績項目へ反映する]をチェック
		if (this.getReflectActualOvertimeHourAtr() == NotUseAtr.USE) {
			// 残業時間の反映
			ReflectApplicationTime.process(overTimeApp.getApplicationTime().getApplicationTime(), dailyApp,
					Optional.of(ReflectAppDestination.RECORD));
		}
		
		// 事前フレックス時間を反映する
		if(overTimeApp.getApplicationTime().getFlexOverTime().isPresent()) {
			ReflectFlexTime.beforeReflectFlex(dailyApp, overTimeApp.getApplicationTime().getFlexOverTime().get());
		}

		// 休憩の申請反映
		this.getBreakLeaveApplication().process(overTimeApp.getBreakTimeOp(), dailyApp);

	}

	/**
	 * @author thanh_nx
	 *
	 *         事前残業申請の反映（勤務予定）
	 */
	public List<Integer> processSC(Require require, String cid, AppOverTimeShare overTimeApp, DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// [勤務情報、出退勤を反映する]をチェック
		if (this.getReflectWorkInfoAtr() == NotUseAtr.USE) {

			// 勤務情報を勤務情報DTOへセット
			WorkInfoDto workInfoDto = overTimeApp.getWorkInfoOp().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));

			// 勤務情報の反映
			lstId.addAll(ReflectWorkInformation.reflectInfo(require, cid, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true)));

			// 出退勤の反映
			lstId.addAll(ReflectAttendance.reflect(require, cid, overTimeApp.getWorkHoursOp(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));

		}

		// 休憩の申請反映
		this.getBreakLeaveApplication().process(overTimeApp.getBreakTimeOp(), dailyApp);

		return lstId;

	}

	public static interface Require extends ReflectWorkInformation.Require, ReflectAttendance.Require {

	}
}

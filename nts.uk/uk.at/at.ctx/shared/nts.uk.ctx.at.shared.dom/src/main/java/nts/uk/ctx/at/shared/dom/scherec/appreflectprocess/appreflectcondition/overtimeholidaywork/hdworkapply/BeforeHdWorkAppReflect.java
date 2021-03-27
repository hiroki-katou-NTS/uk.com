package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4 事前休日出勤申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BeforeHdWorkAppReflect extends DomainObject {
	/**
	 * 休日出勤時間を実績項目へ反映する
	 */
	private NotUseAtr reflectActualHolidayWorkAtr;

	/**
	 * @author thanh_nx
	 *
	 *         事前休日出勤申請の反映（勤務実績）
	 */
	public DailyAfterAppReflectResult process(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 勤務情報を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.ofNullable(holidayApp.getWorkInformation().getWorkTypeCode()),
				holidayApp.getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// 始業終業の反映
		lstId.addAll(
				ReflectStartEndWork.reflect(dailyApp, holidayApp.getWorkingTimeList(), holidayApp.getPrePostAtr()));

		// 事前休出時間の反映
		ReflectApplicationTime.process(holidayApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.SCHEDULE));

		// [休日出勤時間を実績項目へ反映する]をチェック
		if (this.getReflectActualHolidayWorkAtr() == NotUseAtr.USE) {
			// 休出時間の反映
			ReflectApplicationTime.process(holidayApp.getApplicationTime().getApplicationTime(), dailyApp,
					Optional.of(ReflectAppDestination.RECORD));
		}
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}

	/**
	 * @author thanh_nx
	 *
	 *
	 *         事前休日出勤申請の反映(勤務予定）
	 */

	public DailyAfterAppReflectResult processSC(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 勤務情報を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.ofNullable(holidayApp.getWorkInformation().getWorkTypeCode()),
				holidayApp.getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// 出退勤の反映
		lstId.addAll(ReflectAttendance.reflect(holidayApp.getWorkingTimeList(), ScheduleRecordClassifi.RECORD, dailyApp,
				Optional.of(true), Optional.of(true)));

		// ドメイン「休憩の申請反映」を作成する
		// 休憩・外出の申請反映
		new BreakApplication(NotUseAtr.USE).process(holidayApp.getBreakTimeList(), dailyApp);
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

}

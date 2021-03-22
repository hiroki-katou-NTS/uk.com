package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak.ReflectBreakApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *
 *         事前休日出勤申請の反映(勤務予定）
 */
public class SCBeforeReflectAppHolidayWork {

	public static DailyAfterAppReflectResult process(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, BeforeHdWorkAppReflect before) {

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

		//ドメイン「休憩の申請反映」を作成する
		// 休憩・外出の申請反映
		ReflectBreakApplication.process(holidayApp.getBreakTimeList(), dailyApp, new BreakApplication(NotUseAtr.USE));
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}
}

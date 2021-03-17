package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事前休日出勤申請の反映（勤務実績）
 */
public class RCBeforeReflectAppHolidayWork {
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

		// 始業終業の反映
		lstId.addAll(
				ReflectStartEndWork.reflect(dailyApp, holidayApp.getWorkingTimeList(), holidayApp.getPrePostAtr()));

		// 事前休出時間の反映
		ReflectApplicationTime.process(holidayApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.SCHEDULE));

		// [休日出勤時間を実績項目へ反映する]をチェック
		if (before.getReflectActualHolidayWorkAtr() == NotUseAtr.USE) {
			// 休出時間の反映
			ReflectApplicationTime.process(holidayApp.getApplicationTime().getApplicationTime(), dailyApp,
					Optional.of(ReflectAppDestination.RECORD));
		}
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}
}

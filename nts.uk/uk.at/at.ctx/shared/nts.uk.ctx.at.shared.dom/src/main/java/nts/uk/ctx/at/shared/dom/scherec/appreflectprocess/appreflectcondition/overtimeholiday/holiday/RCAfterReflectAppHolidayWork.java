package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.otheritem.ReflectOtherItems;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak.ReflectBreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.subtransfer.TranferOvertimeCompensatory;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事後休日出勤申請の反映
 */
public class RCAfterReflectAppHolidayWork {

	public static DailyAfterAppReflectResult process(Require require, String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, AfterHdWorkAppReflect after) {
		List<Integer> lstId = new ArrayList<Integer>();
		// 勤務情報を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.ofNullable(holidayApp.getWorkInformation().getWorkTypeCode()),
				holidayApp.getWorkInformation().getWorkTimeCodeNotNull());

		// 日別勤怠と申請の勤務種類、就業時間帯が同じ
		String workTypeApp = holidayApp.getWorkInformation().getWorkTypeCode().v();
		String workTimeApp = workInfoDto.getWorkTimeCode().map(x -> x.v()).orElse(null);
		String workTypeDomain = dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v();
		String workTimeDomain = dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v();
		if (!(workTypeApp.equals(workTypeDomain)
				&& (workTimeApp == null ? workTimeDomain == null : workTimeApp.equals(workTimeDomain)))) {
			// 勤務情報の反映
			lstId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true)));
		}

		// 直行直帰区分の反映
		lstId.addAll(
				ReflectDirectBounceClassifi.reflect(dailyApp, holidayApp.getBackHomeAtr(), holidayApp.getGoWorkAtr()));

		// [出退勤を反映する]をチェック
		if (after.getWorkReflect() == NotUseAtr.USE) {
			// 出退勤の反映
			lstId.addAll(ReflectAttendance.reflect(holidayApp.getWorkingTimeList(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(holidayApp.getGoWorkAtr() == NotUseAtr.NOT_USE),
					Optional.of(holidayApp.getBackHomeAtr() == NotUseAtr.NOT_USE)));
		}

		// 休出時間の反映
		ReflectApplicationTime.process(holidayApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.RECORD));

		// その他項目の反映
		ReflectOtherItems.process(holidayApp.getApplicationTime(), dailyApp, after.getOthersReflect());

		// 休憩・外出の申請反映
		ReflectBreakApplication.process(holidayApp.getBreakTimeList(), dailyApp, after.getBreakLeaveApplication());

		// 休日出勤時間の代休振替
		TranferOvertimeCompensatory.process(require, cid, dailyApp.getDomain());
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends ReflectWorkInformation.Require, TranferOvertimeCompensatory.Require {

	}
}

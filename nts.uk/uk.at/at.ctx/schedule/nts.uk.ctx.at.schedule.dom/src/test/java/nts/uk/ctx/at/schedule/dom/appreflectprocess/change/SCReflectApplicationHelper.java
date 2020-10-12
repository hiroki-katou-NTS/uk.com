package nts.uk.ctx.at.schedule.dom.appreflectprocess.change;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationDateShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectionStatusShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeZoneAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppOtherShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.StayingTimeOfDaily;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class SCReflectApplicationHelper {

	public static ApplicationShare createApp(ApplicationTypeShare appType, PrePostAtrShare pre) {

		return new ApplicationShare(1, "1", pre, "1", appType, new ApplicationDateShare(GeneralDate.today()), "1",
				GeneralDateTime.now(), new ReflectionStatusShare(new ArrayList<>()));
	}

	public static ApplicationShare createApp(PrePostAtrShare pre) {
		return createApp(ApplicationTypeShare.STAMP_APPLICATION, pre);
	}

	public static AppStampShare createAppStamp() {
		return createAppStamp(TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, TimeZoneStampClassificationShare.NURSE,
				PrePostAtrShare.POSTERIOR);
	}

	public static AppStampShare createAppStamp(TimeStampAppEnumShare type, TimeZoneStampClassificationShare classsifi,
			PrePostAtrShare pre) {
		List<TimeStampAppShare> lstStampAppShare = ReflectApplicationHelper.createlstTimeStamp(
				StartEndClassificationShare.START, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo");// 勤務場所

		List<DestinationTimeAppShare> listDestinationTimeApp = ReflectApplicationHelper.createlstDisTimeStamp(type,
				StartEndClassificationShare.START, 1);

		List<TimeStampAppOtherShare> listTimeStampAppOther = ReflectApplicationHelper.createlstTimeStampOther(1, 666,
				1111, classsifi);

		List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp = ReflectApplicationHelper.createlstDisTimezone(1,
				classsifi);

		return new AppStampShare(lstStampAppShare, listDestinationTimeApp, listTimeStampAppOther,
				listDestinationTimeZoneApp,
				ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION, pre));
	}

	public static ReflectStatusResultShare createReflectStatusResult() {
		return new ReflectStatusResultShare(ReflectedStateShare.NOTREFLECTED, null, null);
	}

	public static WorkSchedule createWorkSchedule() {

		// 日別勤怠の出退勤
		TimeLeavingWork work = new TimeLeavingWork(new WorkNo(1), null, null);
		work.setAttendanceStamp(Optional.of(new TimeActualStamp(null,
				new WorkStamp(new TimeWithDayAttr(480),
						new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, null),
								new TimeWithDayAttr(480)),
						Optional.empty()),
				0)));

		work.setLeaveStamp(Optional.of(new TimeActualStamp(null,
				new WorkStamp(new TimeWithDayAttr(1200),
						new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, null),
								new TimeWithDayAttr(1200)),
						Optional.empty()),
				0)));

		List<TimeLeavingWork> timeLeavingWorks = new ArrayList<TimeLeavingWork>();
		timeLeavingWorks.add(work);
		Optional<TimeLeavingOfDailyAttd> attendanceLeave = Optional
				.of(new TimeLeavingOfDailyAttd(timeLeavingWorks, new WorkTimes(1)));

		// 日別勤怠の短時間勤務時間帯
		List<ShortWorkingTimeSheet> shortWorkingTimeSheets = new ArrayList<>();
		shortWorkingTimeSheets.add(new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(1), ChildCareAttribute.CARE,
				new TimeWithDayAttr(480), new TimeWithDayAttr(1020)));
		Optional<ShortTimeOfDailyAttd> shortTime = Optional.of(new ShortTimeOfDailyAttd(shortWorkingTimeSheets));

		// 日別勤怠の休憩時間帯
		List<BreakTimeOfDailyAttd> breakTime = new ArrayList<>();
		List<BreakTimeSheet> breakTimeSheets = new ArrayList<>();
		breakTimeSheets
				.add(new BreakTimeSheet(new BreakFrameNo(1), new TimeWithDayAttr(480), new TimeWithDayAttr(1020)));
		breakTime.add(new BreakTimeOfDailyAttd(BreakType.REFER_WORK_TIME, breakTimeSheets));

		Optional<AttendanceTimeOfDailyAttendance> attTimeOpt = Optional.of(new AttendanceTimeOfDailyAttendance(
				WorkScheduleTimeOfDaily.defaultValue(), ActualWorkingTimeOfDaily.defaultValue(),
				StayingTimeOfDaily.defaultValue(), new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0),
				MedicalCareTimeOfDaily.defaultValue()));
		return new WorkSchedule("1", GeneralDate.today(), ConfirmedATR.UNSETTLED,
				new WorkInfoOfDailyAttendance(new WorkInformation("001", "001"), new WorkInformation("001", "001"),
						CalculationState.No_Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use,
						DayOfWeek.FRIDAY, new ArrayList<>()),
				null, breakTime, new ArrayList<>(), attendanceLeave, attTimeOpt, shortTime);
	}

	public static ReflectAppStamp createReflectAppSet() {
		return new ReflectAppStamp("1", NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}
}

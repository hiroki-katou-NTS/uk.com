package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.timeleaveapplication.vacation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *休暇申請の時間消化の反映
 */
@RunWith(JMockit.class)
public class TimeLeaveAppReflectConditionTest {

	/*
	 * テストしたい内容
	 * 
	 * →休暇申請の時間消化の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 時間消化申請のデータ
	 */
	@Test
	public void test() {
		TimeDigestApplicationShare timeDigestApp = new TimeDigestApplicationShare(new AttendanceTime(10), // 60H超休
				new AttendanceTime(20), // 介護時間
				new AttendanceTime(30), // 子の看護時間
				new AttendanceTime(40), // 時間代休時間
				new AttendanceTime(50), // 時間特別休暇
				new AttendanceTime(60), // 時間年休時間
				Optional.of(1));
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);

		// 時間年休 = する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.USE, NotUseAtr.NOT_USE);
		condition.processTimeDigest(timeDigestApp, dailyApp);
		// 時間年休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getAnnual().getDigestionUseTime())
						.isEqualTo(timeDigestApp.getTimeAnnualLeave());
		// 時間代休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSubstitute().getDigestionUseTime().v()).isEqualTo(0);
		// 時間特別休暇
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday().getDigestionUseTime().v()).isEqualTo(0);
		// 60H超休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getOverSalary().getDigestionUseTime().v()).isEqualTo(0);

		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		// 時間代休 = する
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		condition.processTimeDigest(timeDigestApp, dailyApp);
		// 時間年休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getAnnual().getDigestionUseTime().v()).isEqualTo(0);
		// 時間代休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSubstitute().getDigestionUseTime())
						.isEqualTo(timeDigestApp.getTimeOff());
		// 時間特別休暇
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday().getDigestionUseTime().v()).isEqualTo(0);
		// 60H超休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getOverSalary().getDigestionUseTime().v()).isEqualTo(0);

		// 時間特別休暇 = する
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		condition.processTimeDigest(timeDigestApp, dailyApp);
		// 時間特別休暇
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getAnnual().getDigestionUseTime().v()).isEqualTo(0);
		// 時間代休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSubstitute().getDigestionUseTime().v()).isEqualTo(0);
		// 時間特別休暇
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday().getDigestionUseTime())
						.isEqualTo(timeDigestApp.getTimeSpecialVacation());
		// 60H超休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getOverSalary().getDigestionUseTime().v()).isEqualTo(0);

		// 時間60H超休= する
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		condition.processTimeDigest(timeDigestApp, dailyApp);
		// 時間年休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getAnnual().getDigestionUseTime().v()).isEqualTo(0);
		// 時間代休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSubstitute().getDigestionUseTime().v()).isEqualTo(0);
		// 時間特別休暇
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday().getDigestionUseTime().v()).isEqualTo(0);
		// 60H超休
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getHolidayOfDaily().getOverSalary().getDigestionUseTime())
						.isEqualTo(timeDigestApp.getOvertime60H());

	}

}

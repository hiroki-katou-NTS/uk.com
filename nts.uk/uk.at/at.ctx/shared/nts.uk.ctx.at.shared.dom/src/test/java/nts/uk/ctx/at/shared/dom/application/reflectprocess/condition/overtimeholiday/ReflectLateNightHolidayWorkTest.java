package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.HolidayMidNightTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OverTimeShiftNightShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * @author thanh_nx
 *
 * 時間外深夜時間の反映（休日出勤）
 */
@RunWith(JMockit.class)
public class ReflectLateNightHolidayWorkTest {

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →就業時間外深夜時間がエンプティーの場合就業時間外深夜時間の反映ができない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 
	 */
	@Test
	public void test() {
		ApplicationTimeShare applicationTimeShare = createAppTimeNightNull();// 残業深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		// before setting
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().setExcessOfStatutoryMidNightTime(new ExcessOfStatutoryMidNightTime(
						TimeDivergenceWithCalculation.sameTime(new AttendanceTime(1200)), new AttendanceTime(0)));// 法定外深夜時間.事前時間

		AppReflectOtHdWork.processLateNightHolidayWork(dailyApp, applicationTimeShare, PrePostAtrShare.PREDICT);

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime().getTime().v())
								.isEqualTo(1200);
	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →時間外深夜時間の反映 ①日別勤怠の所定外時間. 所定外深夜時間. 事前申請時間にセットする
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →就業時間外深夜時間がある
	 * 
	 * →事前事後区分＝事前
	 * 
	 */
	@Test
	public void test2() {

		ApplicationTimeShare applicationTimeShare = createAppTime(1222);// 残業深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		// before setting
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().setExcessOfStatutoryMidNightTime(new ExcessOfStatutoryMidNightTime(
						TimeDivergenceWithCalculation.defaultValue(), new AttendanceTime(1200)));// 法定外深夜時間.事前時間

		AppReflectOtHdWork.processLateNightHolidayWork(dailyApp, applicationTimeShare, PrePostAtrShare.PREDICT);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(1222);
	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →時間外深夜時間の反映
	 * 
	 * ①日別勤怠の所定外時間. 所定外深夜時間. 時間に「申請時間.就業時間外深夜時間.合計外深夜時間」をセットする
	 * 
	 * ②日別勤怠の休出時間. 休出深夜. 休出深夜時間. 時間に「日別勤怠の休出時間. 休出深夜. 休出深夜時間. 時間」をセットする
	 * 　　→日別勤怠の休出時間. 休出深夜. 休出深夜時間. 法定区分を保存した　→　値を更新する
	 * 
	 * 　　→日別勤怠の休出時間. 休出深夜. 休出深夜時間. 法定区分を保存しない　→新しい値を作る
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →就業時間外深夜時間がある
	 * 
	 * →事前事後区分＝事後
	 * 
	 */
	@Test
	public void test3() {

		ApplicationTimeShare applicationTimeShare = createAppTime(1222, // 合計外深夜時間
				//日別勤怠の休出時間. 休出深夜. 休出深夜時間
				Arrays.asList(new HolidayMidNightTimeShare(666, StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork), // 休出深夜時間.法定区分
						new HolidayMidNightTimeShare(777, StaturoryAtrOfHolidayWork.PublicHolidayWork)));//
																											// 申請時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);

		List<HolidayWorkMidNightTime> holidayWorkMidNightTime = new ArrayList<>();
		holidayWorkMidNightTime.add(new HolidayWorkMidNightTime(TimeDivergenceWithCalculation.defaultValue(), // 0
				StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork));// 法定外休出
		// 日別実績の所定外時間.休出時間.休出深夜.休出深夜時間 before
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayMidNightWork()
				.set(new HolidayMidnightWork(holidayWorkMidNightTime));

		AppReflectOtHdWork.processLateNightHolidayWork(dailyApp, applicationTimeShare, PrePostAtrShare.POSTERIOR);

		// 日別勤怠の所定外時間. 所定外深夜時間. 時間
		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime().getTime().v())
								.isEqualTo(1222);

		// compare 日別勤怠の休出時間. 休出深夜. 休出深夜時間. 時間
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get()
				.getHolidayMidNightWork().get().getHolidayWorkMidNightTime())
						.extracting(x -> x.getStatutoryAtr(), x -> x.getTime().getTime().v())
						.containsExactly(Tuple.tuple(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork, 666), // 値を更新する
								Tuple.tuple(StaturoryAtrOfHolidayWork.PublicHolidayWork, 777));// 新しい値を作る

	}

	private ApplicationTimeShare createAppTimeNightNull() {
		return new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.empty(), //
				new ArrayList<>(), new ArrayList<>());
	}

	private ApplicationTimeShare createAppTime(int time) {
		return new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.of(new OverTimeShiftNightShare(new ArrayList<>(), new AttendanceTime(time), // 合計外深夜時間
						new AttendanceTime(time))), //
				new ArrayList<>(), new ArrayList<>());
	}

	private ApplicationTimeShare createAppTime(int timeSum, List<HolidayMidNightTimeShare> midNightHolidayTimes) {
		return new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), 
				Optional.of(new OverTimeShiftNightShare(midNightHolidayTimes, // 休出深夜時間
						new AttendanceTime(timeSum), // 合計外深夜時間
						new AttendanceTime(0))), //
				new ArrayList<>(), new ArrayList<>());
	}
}

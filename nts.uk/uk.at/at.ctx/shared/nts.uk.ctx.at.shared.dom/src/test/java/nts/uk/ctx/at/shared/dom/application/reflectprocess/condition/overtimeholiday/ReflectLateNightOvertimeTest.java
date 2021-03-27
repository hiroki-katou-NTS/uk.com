package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.OverTimeShiftNightShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.ReflectLateNightOvertime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;

public class ReflectLateNightOvertimeTest {

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →就業時間外深夜時間がエンプティーの場合就業時間外深夜時間の反映ができない
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →就業時間外深夜時間がエンプティー
	 * 
	 * →事前事後区分＝事前
	 */
	@Test
	public void test1() {

		ApplicationTimeShare applicationTimeShare = createAppTimeNightNull();// 残業深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().setExcessOfStatutoryMidNightTime(new ExcessOfStatutoryMidNightTime(
						TimeDivergenceWithCalculation.defaultValue(), new AttendanceTime(1200)));// 法定外深夜時間.事前時間

		ReflectLateNightOvertime.process(dailyApp, applicationTimeShare, PrePostAtrShare.PREDICT);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
				.getBeforeApplicationTime().v()).isEqualTo(1200);
	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →時間外深夜時間の反映
	 *    ①日別勤怠の所定外時間. 所定外深夜時間. 事前申請時間にセットする
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

		ApplicationTimeShare applicationTimeShare = createAppTime(1222, 0);// 残業深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
				.getExcessOfStatutoryTimeOfDaily().setExcessOfStatutoryMidNightTime(new ExcessOfStatutoryMidNightTime(
						TimeDivergenceWithCalculation.defaultValue(), new AttendanceTime(1200)));// 法定外深夜時間.事前時間

		ReflectLateNightOvertime.process(dailyApp, applicationTimeShare, PrePostAtrShare.PREDICT);

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
	 * ①日別勤怠の残業時間. 所定外深夜時間. 時間 にセットする
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

		ApplicationTimeShare applicationTimeShare = createAppTime(1222, 1223);// 残業深夜時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);

		ReflectLateNightOvertime.process(dailyApp, applicationTimeShare, PrePostAtrShare.POSTERIOR);

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime().getTime().v())
								.isEqualTo(1222);// 日別勤怠の所定外時間. 所定外深夜時間. 時間

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()
				.getExcessOverTimeWorkMidNightTime().get().getTime().getTime().v()).isEqualTo(1223);// 日別勤怠の残業時間.
																									// 所定外深夜時間. 時間

	}

	private ApplicationTimeShare createAppTime(int timeSum, int time) {
		return new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.of(new OverTimeShiftNightShare(new ArrayList<>(), new AttendanceTime(timeSum),//合計外深夜時間
						new AttendanceTime(time))), //残業深夜時間
				new ArrayList<>(), new ArrayList<>());
	}

	private ApplicationTimeShare createAppTimeNightNull() {
		return new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), //
				Optional.empty(), //
				new ArrayList<>(), new ArrayList<>());
	}
}

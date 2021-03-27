package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

public class ReflectApplicationTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →残業時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「勤怠種類」設定＝残業時間
	 * 
	 */
	@Test
	public void test1() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				0);

		val applicationTimes = ReflectApplicationHelper.createAppSettingShare(AttendanceTypeShare.NORMALOVERTIME, 195);//

		ReflectApplicationTime.process(Arrays.asList(applicationTimes), dailyApp,
				Optional.of(ReflectAppDestination.RECORD));

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getOverTimeWork().getTime().v())
								.containsExactly(Tuple.tuple(0, 195));
	}

	/*
	 * テストしたい内容
	 * 
	 * →休出時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「勤怠種類」設定＝休出時間
	 * 
	 */
	@Test
	public void test2() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				0);

		val applicationTimes = ReflectApplicationHelper.createAppSettingShare(AttendanceTypeShare.BREAKTIME, 195);//

		ReflectApplicationTime.process(Arrays.asList(applicationTimes), dailyApp,
				Optional.of(ReflectAppDestination.RECORD));

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime())
								.extracting(x -> x.getHolidayFrameNo().v(),
										x -> x.getHolidayWorkTime().get().getTime().v())
								.containsExactly(Tuple.tuple(0, 195));

	}

	
	/*
	 * テストしたい内容
	 * 
	 * →加給時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「勤怠種類」設定＝加給時間
	 * 
	 */
	@Test
	public void test3() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				0);

		val applicationTimes = ReflectApplicationHelper.createAppSettingShare(AttendanceTypeShare.BONUSPAYTIME, 195);//

		ReflectApplicationTime.process(Arrays.asList(applicationTimes), dailyApp,
				Optional.of(ReflectAppDestination.RECORD));

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get()
						.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
						.getRaisingSalaryTimes())
								.extracting(x -> x.getBonusPayTimeItemNo(),
										x -> x.getBonusPayTime().v())
								.containsExactly(Tuple.tuple(0, 195));

	}
	
	/*
	 * テストしたい内容
	 * 
	 * →特定日加給時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「勤怠種類」設定＝特定日加給時間
	 * 
	 */
	@Test
	public void test4() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				0);

		val applicationTimes = ReflectApplicationHelper.createAppSettingShare(AttendanceTypeShare.BONUSSPECIALDAYTIME, 195);//

		ReflectApplicationTime.process(Arrays.asList(applicationTimes), dailyApp,
				Optional.of(ReflectAppDestination.SCHEDULE));

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get()
						.getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
						.getAutoCalRaisingSalarySettings())
								.extracting(x -> x.getBonusPayTimeItemNo(),
										x -> x.getBonusPayTime().v())
								.containsExactly(Tuple.tuple(0, 195));

	}
}

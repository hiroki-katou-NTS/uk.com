package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.overtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

/**
 * @author thanh_nx
 *
 *         残業時間の反映
 */
public class ReflectApplicationTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →残業枠NOを保存するとき残業時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「残業枠NO」＝1
	 * 
	 * → 「申請の反映先」＝実績
	 * 
	 */
	@Test
	public void test1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 残業枠NO= 1

		val applicationTime = ReflectApplicationHelper.createAppSettingShare(1, AttendanceTypeShare.NORMALOVERTIME,
				195);// 残業枠NO = 1

		 NtsAssert.Invoke.staticMethod(ReflectApplicationTime.class,
				"processOvertimeDetail", applicationTime, dailyApp, ReflectAppDestination.RECORD);

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getOverTimeWork().getTime().v())
								.containsExactly(Tuple.tuple(1, 195));
	}

	/*
	 * テストしたい内容
	 * 
	 * →残業枠NOを保存しないとき残業時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「残業枠NO」＝1
	 * 
	 * → 「申請の反映先」＝実績
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);// 残業枠NO= 1

		val applicationTime = ReflectApplicationHelper.createAppSettingShare(2, AttendanceTypeShare.NORMALOVERTIME,
				195);// 残業枠NO = ２

		NtsAssert.Invoke.staticMethod(ReflectApplicationTime.class,
				"processOvertimeDetail", applicationTime, dailyApp, ReflectAppDestination.RECORD);

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getOverTimeWork().getTime().v())
								.containsExactly(Tuple.tuple(1, 0), Tuple.tuple(2, 195));
	}

	/*
	 * テストしたい内容
	 * 
	 * →予定残業時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「申請の反映先」＝予定
	 * 
	 */
	@Test
	public void test3() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 残業枠NO= 1

		val applicationTime = ReflectApplicationHelper.createAppSettingShare(1, AttendanceTypeShare.NORMALOVERTIME,
				195);// 残業枠NO = 1

		NtsAssert.Invoke.staticMethod(ReflectApplicationTime.class,
				"processOvertimeDetail", applicationTime, dailyApp, ReflectAppDestination.SCHEDULE);

		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getBeforeApplicationTime().v())
								.containsExactly(Tuple.tuple(1, 195));
	}

}

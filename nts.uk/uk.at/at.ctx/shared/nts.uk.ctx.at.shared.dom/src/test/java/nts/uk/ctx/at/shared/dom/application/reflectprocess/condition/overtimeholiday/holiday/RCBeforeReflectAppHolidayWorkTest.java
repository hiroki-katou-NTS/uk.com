package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事前休日出勤申請の反映（勤務実績）
 */
@RunWith(JMockit.class)
public class RCBeforeReflectAppHolidayWorkTest {

	@Injectable
	private BeforeHdWorkAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 事前休日出勤申請の反映（勤務実績）
	 * 
	 * →勤務情報の反映
	 * 
	 * →始業終業の反映
	 * 
	 * →事前休出時間の反映
	 * 
	 * → 休出時間の反映がない
	 *
	 * 準備するデータ
	 * 
	 * →[休日出勤時間を実績項目へ反映する]=しない
	 * 
	 * →残業時間＝111
	 */
	@Test
	public void test1() {
		AppHolidayWorkShare holidayApp = ReflectApplicationHelper.createAppHoliday("005", // 勤務種類コード
				"006", // 就業時間帯コード
				1, // No
				488, // 勤務時間帯-開始時刻
				1088, // 勤務時間帯-終了時刻
				111);// 残業時間
		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		BeforeHdWorkAppReflect before = new BeforeHdWorkAppReflect(NotUseAtr.NOT_USE);// [休日出勤時間を実績項目へ反映する]=しない
		before.process(require, "", holidayApp, dailyApp);

		// 勤務情報の反映ができます
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");

		// 始業終業の反映
		assertThat(dailyApp.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), // 開始時刻
						x -> x.getLeaveWork().v()) // 勤務時間帯-終了時刻
				.containsExactly(Tuple.tuple(1, 488, 1088));

		// 事前休出時間の反映
		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getBeforeApplicationTime().v())
								.containsExactly(Tuple.tuple(1, 111));// 日別勤怠の残業時間.残業枠時間.事前申請時間

		// 休出時間の反映がない
		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getOverTimeWork().getTime().v())
								.containsExactly(Tuple.tuple(1, 0));// 日別勤怠の残業時間.残業枠時間.残業時間.時間

	}

	/*
	 * テストしたい内容
	 * 
	 * 事前休日出勤申請の反映（勤務実績）
	 * 
	 * →勤務情報の反映
	 * 
	 * →始業終業の反映
	 * 
	 * →事前休出時間の反映
	 * 
	 * → 休出時間の反映
	 *
	 * 準備するデータ
	 * 
	 * →[休日出勤時間を実績項目へ反映する]=する
	 * 
	 */
	@Test
	public void test2() {

		AppHolidayWorkShare holidayApp = ReflectApplicationHelper.createAppHoliday("005", // 勤務種類コード
				"006", // 就業時間帯コード
				1, // No
				488, // 勤務時間帯-開始時刻
				1088, // 勤務時間帯-終了時刻
				111);// 残業時間
		val dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		BeforeHdWorkAppReflect before = new BeforeHdWorkAppReflect(NotUseAtr.USE);// [休日出勤時間を実績項目へ反映する]=する
		before.process(require, "", holidayApp, dailyApp);

		// 勤務情報の反映ができます
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("005");
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("006");

		// 始業終業の反映
		assertThat(dailyApp.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), // 開始時刻
						x -> x.getLeaveWork().v()) // 勤務時間帯-終了時刻
				.containsExactly(Tuple.tuple(1, 488, 1088));

		// 事前休出時間の反映
		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getBeforeApplicationTime().v())
								.containsExactly(Tuple.tuple(1, 111));// 日別勤怠の残業時間.残業枠時間.事前申請時間

		// 休出時間の反映がある
		assertThat(
				dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime())
								.extracting(x -> x.getOverWorkFrameNo().v(), x -> x.getOverTimeWork().getTime().v())
								.containsExactly(Tuple.tuple(1, 111));// 日別勤怠の残業時間.残業枠時間.残業時間.時間
	}

}

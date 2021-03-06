package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.timeleaveapplication.digest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;

/**
 * @author thanh_nx
 *
 *時間休暇の時間消化の反映
 */
public class TimeLeaveAppReflectConditionTest {

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇の時間消化の反映
	 * 
	 * 日別勤怠の遅刻時間に値をセットする
	 * 
	 * 準備するデータ
	 * 
	 * 時間休暇種類 = 0：出勤前、 2：出勤前2
	 * 
	 */
	@Test
	public void test1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		List<Integer> result = new TimeLeaveAppReflectCondition().process(dailyApp, AppTimeType.ATWORK, createDigest()).getLstItemId();
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCompensatoryLeaveUseTime().v()).isEqualTo(70);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeSpecialHolidayUseTime().v()).isEqualTo(80);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSixtyHourExcessHolidayUseTime().v()).isEqualTo(90);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCareHolidayUseTime().v()).isEqualTo(100);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeChildCareHolidayUseTime().v()).isEqualTo(110);

		assertThat(result).isEqualTo(Arrays.asList(595, 596, 597, 1123, 1124, 1125, 1126));

		// 日別勤怠の早退時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

		// 日別実績の外出時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

	}

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇の時間消化の反映
	 * 
	 * 日別勤怠の早退時間に値をセットする
	 * 
	 * 準備するデータ
	 * 
	 * 時間休暇種類 1：退勤後、 3：退勤後2
	 * 
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		List<Integer> result = new TimeLeaveAppReflectCondition().process(dailyApp, AppTimeType.OFFWORK, createDigest()).getLstItemId();
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(70);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeSpecialHolidayUseTime().v()).isEqualTo(80);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(90);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCareHolidayUseTime().v()).isEqualTo(100);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeChildCareHolidayUseTime().v()).isEqualTo(110);
		assertThat(result).isEqualTo(Arrays.asList(607, 608, 609, 1131, 1132, 1133, 1134));

		// 日別勤怠の遅刻時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

		// 日別実績の外出時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

	}

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇の時間消化の反映
	 * 
	 * 日別実績の外出時間に値をセットする
	 * 
	 * 準備するデータ
	 * 
	 * 時間休暇種類 4：私用外出、 5：組合外出
	 * 
	 */
	@Test
	public void test3() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		List<Integer> result = new TimeLeaveAppReflectCondition().process(dailyApp, AppTimeType.PRIVATE, createDigest()).getLstItemId();
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(70);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().v()).isEqualTo(80);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(90);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime().v()).isEqualTo(100);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime().v()).isEqualTo(110);
		assertThat(result).isEqualTo(Arrays.asList(502, 503, 504, 1145, 505, 1140, 1141));

		// 日別勤怠の早退時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

		// 日別勤怠の遅刻時間の値が変わらない
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeAnnualLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCompensatoryLeaveUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeSpecialHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSpecialHolidayFrameNo()).isEqualTo(Optional.empty());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSixtyHourExcessHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCareHolidayUseTime().v()).isEqualTo(0);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeChildCareHolidayUseTime().v()).isEqualTo(0);

	}

	// 時間消化申請
	private TimeDigestApplicationShare createDigest() {
		return new TimeDigestApplicationShare(new AttendanceTime(90), //60H超休
				new AttendanceTime(100), //介護時間
				new AttendanceTime(110), //子の看護時間
				new AttendanceTime(70),//時間代休時間
				new AttendanceTime(80), //時間特別休暇
				new AttendanceTime(60),//時間年休時間
				Optional.of(1));//特別休暇枠NO
	}
}

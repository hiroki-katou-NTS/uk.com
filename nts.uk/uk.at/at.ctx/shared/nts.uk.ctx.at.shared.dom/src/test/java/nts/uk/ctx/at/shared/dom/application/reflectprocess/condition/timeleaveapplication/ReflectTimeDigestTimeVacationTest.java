package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;

public class ReflectTimeDigestTimeVacationTest {

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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);
		List<Integer> result = ReflectTimeDigestTimeVacation.process(dailyApp, AppTimeType.ATWORK, createDigest());
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCompensatoryLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeSpecialHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getSixtyHourExcessHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeCareHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLateTimeOfDaily().get(0).getTimePaidUseTime()
				.getTimeChildCareHolidayUseTime().v()).isEqualTo(60);

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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);
		List<Integer> result = ReflectTimeDigestTimeVacation.process(dailyApp, AppTimeType.OFFWORK, createDigest());
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeSpecialHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(60);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeCareHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getLeaveEarlyTimeOfDaily().get(0)
				.getTimePaidUseTime().getTimeChildCareHolidayUseTime().v()).isEqualTo(60);
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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);
		List<Integer> result = ReflectTimeDigestTimeVacation.process(dailyApp, AppTimeType.PRIVATE, createDigest());
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo().get().v()).isEqualTo(1);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().v()).isEqualTo(60);
		
		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime().v()).isEqualTo(60);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getOutingTimeOfDaily().get(0)
				.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime().v()).isEqualTo(60);
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
		AttendanceTime timeCommon = new AttendanceTime(60);
		return new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon, timeCommon, timeCommon, timeCommon,
				Optional.of(1));
	}
}

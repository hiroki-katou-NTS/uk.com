package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.timeleaveapplication.ReflectTimeVacationOffsetDeductTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ReflectTimeVacationOffsetDeductTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →時間休暇時間帯を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →予定実績区分＝実績 or 予定
	 * 
	 * →実績の時間帯へ反映する ＝ する
	 */
	@Test
	public void testCase1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		
		List<TimeLeaveApplicationDetailShare> appTimeLeavDetail = createAppTimeLeav(AppTimeType.ATWORK, 1, //NO
				60, //時間消化申請
				495,//時間帯 開始時刻
				1080);//時間帯 終了時刻
		
		TimeLeaveAppReflectCondition condition = createCondi();
		List<Integer> lstResult = ReflectTimeVacationOffsetDeductTime.process(appTimeLeavDetail, dailyApp, condition,
				NotUseAtr.USE);
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

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(1080);//勤怠打刻
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);//時刻変更手段

		assertThat(lstResult).isEqualTo(Arrays.asList(595, 596, 597, 1123, 1124, 1125, 1126, 31));

	}

	/*
	 * テストしたい内容
	 * 
	 * →時間休暇時間帯を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * →予定実績区分＝実績
	 * 
	 * →実績の時間帯へ反映する ＝ しない
	 * 
	 */@Test
	public void testCase２() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);
		int attendanceBefore = dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp()
				.get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
		TimeChangeMeans timeChangeMeansBefore = dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0)
				.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans();
		
		List<TimeLeaveApplicationDetailShare> appTimeLeavDetail = createAppTimeLeav(AppTimeType.ATWORK, 1,
				60, //時間消化申請
				495,//時間帯 開始時刻
				1080);//時間帯 終了時刻);
		TimeLeaveAppReflectCondition condition = createCondi();
		List<Integer> lstResult = ReflectTimeVacationOffsetDeductTime.process(appTimeLeavDetail, dailyApp, condition,
				NotUseAtr.NOT_USE);
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

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(attendanceBefore);//勤怠打刻
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(timeChangeMeansBefore);//時刻変更手段
		assertThat(lstResult).isEqualTo(Arrays.asList(595, 596, 597, 1123, 1124, 1125, 1126));
	}

	private List<TimeLeaveApplicationDetailShare> createAppTimeLeav(AppTimeType appTimeType, int no, int timeDigest,
			int timeZoneStart, int timeZoneEnd) {

		//時間消化申請
		AttendanceTime timeCommon = new AttendanceTime(timeDigest);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon,
				timeCommon, timeCommon, timeCommon, Optional.of(1));
		TimeZoneWithWorkNo timeZone = new TimeZoneWithWorkNo(no, timeZoneStart, timeZoneEnd);
		TimeLeaveApplicationDetailShare detail = new TimeLeaveApplicationDetailShare(appTimeType,
				Arrays.asList(timeZone), digest);
		return Arrays.asList(detail);
	}

	private TimeLeaveAppReflectCondition createCondi() {
		return new TimeLeaveAppReflectCondition(NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE,
				NotUseAtr.USE, NotUseAtr.USE);
	}
}

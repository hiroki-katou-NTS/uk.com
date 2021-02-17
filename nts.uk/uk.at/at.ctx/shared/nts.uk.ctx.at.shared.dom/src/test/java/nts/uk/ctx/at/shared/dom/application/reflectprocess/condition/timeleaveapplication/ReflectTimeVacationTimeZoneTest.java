package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class ReflectTimeVacationTimeZoneTest {

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇時間帯の反映
	 * 
	 * データを日別勤怠の出退勤の時間休暇時間帯に反映する
	 * 
	 * 準備するデータ
	 * 
	 * 時間休種類 = 0：出勤前、1：退勤後、2：出勤前2、3：退勤後2
	 * 
	 * 予定実績区分 ＝予定
	 */
	@Test
	public void test1() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK, timeZoneWithWorkNoLst,
				dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getTimeVacation().get().getStart().v()).isEqualTo(481);
		assertThat(lstResult).isEqualTo(Arrays.asList(1288));

		// ドメイン日別実績には勤務Noが存在しない
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(2, 481, 1081));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK2, timeZoneWithWorkNoLst, dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(1).getAttendanceStamp().get()
				.getTimeVacation().get().getStart().v()).isEqualTo(481);
		assertThat(lstResult).isEqualTo(Arrays.asList(1290));

	}

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇時間帯の反映
	 * 
	 * データを日別勤怠の出退勤の時間休暇時間帯に反映する
	 * 
	 * 準備するデータ
	 * 
	 * 時間休種類 = 0：出勤前、1：退勤後、2：出勤前2、3：退勤後2
	 * 
	 * 予定実績区分 ＝実績
	 */
	@Test
	public void test2() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK, timeZoneWithWorkNoLst,
				dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(481);
		
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(0).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);
		assertThat(lstResult).isEqualTo(Arrays.asList(31));

		// ドメイン日別実績には勤務Noが存在しない
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(2, 481, 1081));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK2, timeZoneWithWorkNoLst, dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(1).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(481);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().get(1).getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);
		assertThat(lstResult).isEqualTo(Arrays.asList(41));
	}

	/*
	 * テストしたい内容
	 * 
	 * 時間休暇時間帯の反映
	 * 
	 * データを日別勤怠の外出時間帯に反映する
	 * 
	 * 準備するデータ
	 * 
	 * 時間休種類 = 4：私用外出、5：組合外出
	 * 
	 */
	@Test
	public void test3() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1, true);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.PRIVATE, timeZoneWithWorkNoLst,
				dailyApp);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(481);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getComeBack().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(1081);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getReasonForGoOut()).isEqualTo(GoingOutReason.PRIVATE);
		assertThat(lstResult).isEqualTo(Arrays.asList(88, 91, 86));

		// ドメイン日別実績には勤務Noが存在しない
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(2, 481, 1081));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.PRIVATE, timeZoneWithWorkNoLst, dailyApp);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getGoOut().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(481);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getComeBack().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(1081);
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getReasonForGoOut()).isEqualTo(GoingOutReason.PRIVATE);
		assertThat(lstResult).isEqualTo(Arrays.asList(95, 98, 93));
	}
}

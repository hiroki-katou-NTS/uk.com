package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

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
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081),
				new TimeZoneWithWorkNo(2, 1082, 1200));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK, timeZoneWithWorkNoLst,
				dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo().v(),
						x -> x.getAttendanceStamp().get().getTimeVacation().get().getStart().v(),
						x -> x.getAttendanceStamp().get().getTimeVacation().get().getEnd().v())
				.contains(Tuple.tuple(1, 481, 1081));
		assertThat(lstResult).isEqualTo(Arrays.asList(1288));

		// ドメイン日別実績には勤務Noが存在しない
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081),
				new TimeZoneWithWorkNo(2, 1082, 1200));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK2, timeZoneWithWorkNoLst, dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getWorkNo() == null ? null : x.getWorkNo().v(),
						x -> x.getAttendanceStamp().get().getTimeVacation().map(y -> y.getStart().v()).orElse(null),
						x -> x.getAttendanceStamp().get().getTimeVacation().map(y -> y.getEnd().v()).orElse(null))
				.contains(Tuple.tuple(1, null, null), Tuple.tuple(2, 1082, 1200));
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

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081),
				new TimeZoneWithWorkNo(2, 1082, 1200));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK, timeZoneWithWorkNoLst,
				dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(x -> x.getWorkNo().v(),
				x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
						.getTimeChangeMeans())
				.contains(Tuple.tuple(1, 1081, TimeChangeMeans.APPLICATION));
		assertThat(lstResult).isEqualTo(Arrays.asList(31));

		// ドメイン日別実績には勤務Noが存在しない
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081),
				new TimeZoneWithWorkNo(2, 1082, 1200));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.ATWORK2, timeZoneWithWorkNoLst, dailyApp);
		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(x -> x.getWorkNo().v(),
				x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
						.getTimeChangeMeans())
				.contains(Tuple.tuple(1, 480, TimeChangeMeans.AUTOMATIC_SET),
						Tuple.tuple(2, 1200, TimeChangeMeans.APPLICATION));
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
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);

		// ドメイン日別実績には勤務Noが存在します
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(1, 481, 1081));
		List<Integer> lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.PRIVATE, timeZoneWithWorkNoLst,
				dailyApp);// 私用外出
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets()).extracting(x -> x.getOutingFrameNo().v(),
				x -> x.getGoOut().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getComeBack().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getReasonForGoOut()).contains(Tuple.tuple(1, 481, 1081, GoingOutReason.PRIVATE));
		assertThat(lstResult).isEqualTo(Arrays.asList(88, 91, 86));

		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.UNION, timeZoneWithWorkNoLst, dailyApp);// 組合外出
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets()).extracting(x -> x.getOutingFrameNo().v(),
				x -> x.getGoOut().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getComeBack().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getReasonForGoOut()).contains(Tuple.tuple(1, 481, 1081, GoingOutReason.UNION));
		assertThat(lstResult).isEqualTo(Arrays.asList(88, 91, 86));

		// ドメイン日別実績には勤務Noが存在しない
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		timeZoneWithWorkNoLst = Arrays.asList(new TimeZoneWithWorkNo(2, 481, 1081));
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.PRIVATE, timeZoneWithWorkNoLst, dailyApp);// 私用外出
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets()).extracting(x -> x.getOutingFrameNo().v(),
				x -> x.getGoOut().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getComeBack().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getReasonForGoOut()).contains(Tuple.tuple(1, 480, 480, GoingOutReason.PUBLIC),
						Tuple.tuple(2, 481, 1081, GoingOutReason.PRIVATE));
		assertThat(lstResult).isEqualTo(Arrays.asList(95, 98, 93));

		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);
		lstResult = ReflectTimeVacationTimeZone.process(AppTimeType.UNION, timeZoneWithWorkNoLst, dailyApp);// 組合外出
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets()).extracting(x -> x.getOutingFrameNo().v(),
				x -> x.getGoOut().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getComeBack().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getReasonForGoOut()).contains(Tuple.tuple(1, 480, 480, GoingOutReason.PUBLIC),
						Tuple.tuple(2, 481, 1081, GoingOutReason.UNION));
		assertThat(lstResult).isEqualTo(Arrays.asList(95, 98, 93));
	}
}

package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

public class ReflectAttendanceTest {

	/*
	 * テストしたい内容
	 * 
	 * →「出勤.時刻」と「退勤.時刻」を更新する
	 * 
	 * 準備するデータ
	 * 
	 * → 予定実績区分が予定
	 * 
	 * → 勤務予定時間帯の勤務NOを存在する
	 */
	@Test
	public void testUpdateSchedule() {

		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(2, 2, 2));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.SCHEDULE,
				dailyApp, Optional.of(true), Optional.of(true));

		assertThat(actualResult).isEqualTo(Arrays.asList(3, 4, 5, 6));

		assertThat(dailyApp.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 
						1, // 
						1), 
						Tuple.tuple(2, 2, 2));

	}

	/*
	 * テストしたい内容
	 * 
	 * →「予定時間帯」を新規作成する
	 * 
	 * 準備するデータ
	 * 
	 * → 予定実績区分が予定
	 * 
	 * → 勤務予定時間帯の勤務NOを存在しない
	 */
	@Test
	public void testCreateSchedule() {

		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(2, 2, 2));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				0);

		List<Integer> actualResult = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.SCHEDULE,
				dailyApp, Optional.of(true), Optional.of(true));

		assertThat(actualResult).isEqualTo(Arrays.asList(3, 4, 5, 6));

		assertThat(dailyApp.getWorkInformation().getScheduleTimeSheets())
				.extracting(x -> x.getWorkNo().v(), x -> x.getAttendance().v(), x -> x.getLeaveWork().v())
				.containsExactly(Tuple.tuple(1, 1, 1), 
						Tuple.tuple(2, 2, 2));
	}

	/*
	 * テストしたい内容
	 * 
	 * →「出勤.時刻」と「退勤.時刻」を更新する →時刻変更手段:申請
	 * 
	 * 準備するデータ
	 * 
	 * → 予定実績区分が実績
	 * 
	 * → 勤務予定時間帯の勤務NOを存在する
	 * 
	 * → 出勤を反映する（初期値＝true, false）, 退勤を反映する（初期値＝true, false）
	 */
	@Test
	public void testUpdateRecord() {

		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));
		// timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(2, 2, 2));

		// 出勤を反映する（初期値＝true）
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// WorkNO = 1;

		List<Integer> actualResult = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp, Optional.of(true), Optional.of(false));

		assertThat(actualResult).isEqualTo(Arrays.asList(31));

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
								.getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝true）
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult2 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp2, Optional.of(false), Optional.of(true));

		assertThat(actualResult2).isEqualTo(Arrays.asList(34));

		assertThat(dailyApp2.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝false）
		// 出勤を反映する（初期値＝false）
		DailyRecordOfApplication dailyApp3 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult3 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp3, Optional.of(true), Optional.of(true));

		assertThat(actualResult3).isEqualTo(Arrays.asList(31, 34));

		assertThat(dailyApp2.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, TimeChangeMeans.APPLICATION));

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
								.getTimeChangeMeans())
				.containsExactly(Tuple.tuple(1, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝false）
		// 出勤を反映する（初期値＝false）
		DailyRecordOfApplication dailyApp4 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult4 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp4, Optional.of(false), Optional.of(false));

		assertThat(actualResult4).isEqualTo(Arrays.asList());

	}
	
	/*
	 * テストしたい内容
	 * 
	 * →「出勤.時刻」と「退勤.時刻」を更新する →時刻変更手段:申請
	 * 
	 * 準備するデータ
	 * 
	 * → 予定実績区分が実績
	 * 
	 * → 勤務予定時間帯の勤務NOを存在しない
	 * 
	 * → 出勤を反映する（初期値＝true, false）, 退勤を反映する（初期値＝true, false）
	 */
	@Test
	public void testCreateRecord() {

		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(2, 2, 2));

		// 出勤を反映する（初期値＝true）
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);// WorkNO = 1;

		List<Integer> actualResult = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp, Optional.of(true), Optional.of(false));

		assertThat(actualResult).isEqualTo(Arrays.asList(41));

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
								.getTimeChangeMeans())
				.containsExactly(Tuple.tuple(480, TimeChangeMeans.AUTOMATIC_SET), 
						Tuple.tuple(2, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝true）
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult2 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp2, Optional.of(false), Optional.of(true));

		assertThat(actualResult2).isEqualTo(Arrays.asList(44));

		assertThat(dailyApp2.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
		.containsExactly(Tuple.tuple(1200, TimeChangeMeans.AUTOMATIC_SET), Tuple.tuple(2, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝false）
		// 出勤を反映する（初期値＝false）
		DailyRecordOfApplication dailyApp3 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult3 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp3, Optional.of(true), Optional.of(true));

		assertThat(actualResult3).isEqualTo(Arrays.asList(41, 44));

		assertThat(dailyApp2.getAttendanceLeave().get().getTimeLeavingWorks()).extracting(
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
				x -> x.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
		.containsExactly(Tuple.tuple(1200, TimeChangeMeans.AUTOMATIC_SET), Tuple.tuple(2, TimeChangeMeans.APPLICATION));

		assertThat(dailyApp.getAttendanceLeave().get().getTimeLeavingWorks())
				.extracting(x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v(),
						x -> x.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange()
								.getTimeChangeMeans())
				.containsExactly(Tuple.tuple(480, TimeChangeMeans.AUTOMATIC_SET), Tuple.tuple(2, TimeChangeMeans.APPLICATION));

		// 退勤を反映する（初期値＝true）
		// 出勤を反映する（初期値＝true）
		DailyRecordOfApplication dailyApp4 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);// WorkNO = 1;

		List<Integer> actualResult4 = ReflectAttendance.reflect(timeZoneWithWorkNoLst, ScheduleRecordClassifi.RECORD,
				dailyApp4, Optional.of(false), Optional.of(false));

		assertThat(actualResult4).isEqualTo(Arrays.asList());

	}
}

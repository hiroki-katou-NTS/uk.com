package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

@RunWith(JMockit.class)
public class CancelAppStampTest {

	/*
	 * テストしたい内容
	 * 
	 * 日別勤怠の出退勤.出退勤.出勤.打刻の値を削除
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請時刻分類 = 「出勤／退勤」
	 * 
	 * 開始終了区分が開始or終了
	 */
	@Test
	public void testAtt() {

		// 開始終了区分が開始
		List<DestinationTimeAppShare> listDestinationTimeApp = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, // 出勤／退勤
				StartEndClassificationShare.START, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);

		List<Integer> actualResult = CancelAppStamp.process(dailyApp, listDestinationTimeApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(31, 30));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getLocationCode()).isEqualTo(Optional.empty());

		// compare 丸め後の時刻

		/************************************************************************************************/
		// 開始終了区分が終了
		List<DestinationTimeAppShare> listDestinationTimeApp2 = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, // 出勤／退勤
				StartEndClassificationShare.END, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1);

		List<Integer> actualResult2 = CancelAppStamp.process(dailyApp2, listDestinationTimeApp2);

		assertThat(actualResult2).isEqualTo(Arrays.asList(34, 33));

		// compare 時刻
		assertThat(dailyApp2.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp2.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp2.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getLocationCode()).isEqualTo(Optional.empty());

	}

	/*
	 * テストしたい内容
	 * 
	 * 日別勤怠の臨時出退勤.出退勤.出勤.打刻の値を削除
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請時刻分類 = 「臨時」
	 * 
	 * 開始終了区分が開始or終了
	 */
	@Test
	public void testExtra() {

		// 開始終了区分が開始
		List<DestinationTimeAppShare> listDestinationTimeApp = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.EXTRAORDINARY, // 「臨時」
				StartEndClassificationShare.START, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);

		List<Integer> actualResult = CancelAppStamp.process(dailyApp, listDestinationTimeApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(51, 50));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getLocationCode()).isEqualTo(Optional.empty());

		/************************************************************************************************/
		// 開始終了区分が終了
		List<DestinationTimeAppShare> listDestinationTimeApp2 = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.EXTRAORDINARY, // 「臨時」
				StartEndClassificationShare.END, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1, true);

		List<Integer> actualResult2 = CancelAppStamp.process(dailyApp2, listDestinationTimeApp2);

		assertThat(actualResult2).isEqualTo(Arrays.asList(53, 52));

		// compare 時刻
		assertThat(dailyApp2.getTempTime().get().getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().get()
				.getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp2.getTempTime().get().getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp2.getTempTime().get().getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().get()
				.getLocationCode()).isEqualTo(Optional.empty());

	}

	/*
	 * テストしたい内容
	 * 
	 * 日別勤怠の外出時間帯.出退勤.出勤.打刻の値を削除
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請時刻分類 = 「外出／戻り」
	 * 
	 * 開始終了区分が開始or終了
	 */
	@Test
	public void testGoout() {

		// 開始終了区分が開始
		List<DestinationTimeAppShare> listDestinationTimeApp = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.GOOUT_RETURNING, // 「外出／戻り」
				StartEndClassificationShare.START, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);

		List<Integer> actualResult = CancelAppStamp.process(dailyApp, listDestinationTimeApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(88, 87));

		// compare 時刻
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getStamp().get().getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getStamp().get().getLocationCode()).isEqualTo(Optional.empty());

		// compare 丸め後の時刻
		/************************************************************************************************/
		// 開始終了区分が終了
		List<DestinationTimeAppShare> listDestinationTimeApp2 = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.GOOUT_RETURNING, // 「外出／戻り」
				StartEndClassificationShare.END, // 開始終了区分
				1);// 打刻枠No

		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1, true);

		List<Integer> actualResult2 = CancelAppStamp.process(dailyApp2, listDestinationTimeApp2);

		assertThat(actualResult2).isEqualTo(Arrays.asList(91, 90));

		// compare 時刻
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get().getStamp().get()
				.getTimeDay().getTimeWithDay()).isEqualTo(Optional.empty());

		// compare 時刻変更手段
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get().getStamp().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get().getStamp().get()
				.getLocationCode()).isEqualTo(Optional.empty());

		// 開始終了区分が終了
	}

}

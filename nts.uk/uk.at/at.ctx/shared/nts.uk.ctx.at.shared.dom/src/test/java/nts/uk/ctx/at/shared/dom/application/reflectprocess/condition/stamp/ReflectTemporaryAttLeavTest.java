package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

@RunWith(JMockit.class)
public class ReflectTemporaryAttLeavTest {

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の臨時出退勤.出勤」を更新する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の臨時出退勤.出退勤に勤務NOを存在する
	 * 
	 * 開始終了区分が開始
	 */
	@Test
	public void testUpdateStart() {
		
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);//打刻NO= 1
		
		List<Integer> actualResult = ReflectTemporaryAttLeav.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStamp(TimeStampAppEnumShare.EXTRAORDINARY, StartEndClassificationShare.START, //開始終了区分
						540));//時刻

		assertThat(actualResult).isEqualTo(Arrays.asList(50, 51));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(540);

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getAttendanceStamp().get()
				.getStamp().get().getLocationCode().get().v()).isEqualTo("0001");
	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の臨時出退勤.出勤」を作成する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の臨時出退勤に勤務NOを存在しない
	 * 
	 * 開始終了区分が開始
	 */
	@Test
	public void testCreateStart() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 
						1, true);//打刻NO= 1
		
		List<Integer> actualResult = ReflectTemporaryAttLeav.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStamp(StartEndClassificationShare.START, //開始終了区分
						540, //時刻
						2));// 打刻枠NO

		assertThat(actualResult).isEqualTo(Arrays.asList(59, 58));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(540);

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getAttendanceStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getAttendanceStamp().get()
				.getStamp().get().getLocationCode().get().v()).isEqualTo("0001");
	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の臨時出退勤.退勤」を更新する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の臨時出退勤に勤務NOを存在する
	 * 
	 * 開始終了区分が開始
	 */
	@Test
	public void testUpdateEnd() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);//打刻NO= 1
		
		List<Integer> actualResult = ReflectTemporaryAttLeav.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStamp(StartEndClassificationShare.END, //開始終了区分
						540));//時刻

		assertThat(actualResult).isEqualTo(Arrays.asList(52, 53));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(540);

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(1).get().getLeaveStamp().get()
				.getStamp().get().getLocationCode().get().v()).isEqualTo("0001");
	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の臨時出退勤.退勤」をを作成する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の臨時出退勤に勤務NOを存在しない
	 * 
	 * 開始終了区分が開始
	 */
	@Test
	public void testCreateEnd() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 
						1, true);//打刻NO= 1
		
		List<Integer> actualResult = ReflectTemporaryAttLeav.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStamp(StartEndClassificationShare.END, //開始終了区分
						540, //時刻
						2));// 打刻枠NO

		assertThat(actualResult).isEqualTo(Arrays.asList(61, 60));

		// compare 時刻
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getTimeWithDay().get().v()).isEqualTo(540);

		// compare 時刻変更手段
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getLeaveStamp().get()
				.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans())
						.isEqualTo(TimeChangeMeans.APPLICATION);

		// compare 場所コード
		assertThat(dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(2).get().getLeaveStamp().get()
				.getStamp().get().getLocationCode().get().v()).isEqualTo("0001");
	}
}

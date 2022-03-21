package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.ReflectOutingTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

@RunWith(JMockit.class)
public class ReflectOutingTimeZoneTest {

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の外出時間帯.外出時間帯.外出を更新する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の外出時間帯に勤務NOを存在する
	 * 
	 * 開始終了区分が開始
	 */

	@Test
	public void testUpdateGoOut() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 打刻NO= 1

		List<TimeStampAppShare> listTimeStampApp = ReflectApplicationHelper
				.createlstTimeStamp(StartEndClassificationShare.START, 
						800, // time
						1, // no
						"0002");//location

		List<Integer> actualResult = ReflectOutingTimeZone.process(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(86, 87, 88));

		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getTimeDay().getTimeWithDay().get().v()).isEqualTo(800);//時刻
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);//時刻変更手段
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getGoOut().get()
				.getLocationCode().get().v()).isEqualTo("0002");//場所コード

	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の外出時間帯.外出時間帯.外出」を作成する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の外出時間帯に勤務NOを存在しない
	 * 
	 * 開始終了区分が開始
	 */
	@Test
	public void testCreateGoOut() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 打刻NO= 1

		List<TimeStampAppShare> listTimeStampApp = ReflectApplicationHelper
				.createlstTimeStamp(StartEndClassificationShare.START, 
						800, // time
						2, // 外出枠NO
						"0002");//location

		List<Integer> actualResult = ReflectOutingTimeZone.process(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(95, 94, 93));

		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getOutingFrameNo().v()).isEqualTo(2);//外出枠NO
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getGoOut().get()
				.getTimeDay().getTimeWithDay().get().v()).isEqualTo(800);//時刻
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getGoOut().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);//時刻変更手段
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getGoOut().get()
				.getLocationCode().get().v()).isEqualTo("0002");//場所コード
	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の外出時間帯.外出時間帯.戻るを更新する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の外出時間帯に勤務NOを存在する
	 * 
	 * 開始終了区分が終了
	 */
	@Test
	public void testUpdateGoback() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 打刻NO= 1

		List<TimeStampAppShare> listTimeStampApp = ReflectApplicationHelper
				.createlstTimeStamp(StartEndClassificationShare.END, 
						1111, // time
						1, // no
						"0002");//location

		List<Integer> actualResult = ReflectOutingTimeZone.process(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(91, 86, 90));

		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getComeBack().get()
				.getTimeDay().getTimeWithDay().get().v()).isEqualTo(1111);//時刻
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getComeBack().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);//時刻変更手段
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(0).getComeBack().get()
				.getLocationCode().get().v()).isEqualTo("0002");//場所コード
	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の外出時間帯.外出時間帯.戻る」をを作成する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の外出時間帯に勤務NOを存在しない
	 * 
	 * 開始終了区分が終了
	 */
	@Test
	public void testCreateGoback() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 打刻NO= 1

		List<TimeStampAppShare> listTimeStampApp = ReflectApplicationHelper
				.createlstTimeStamp(StartEndClassificationShare.END, 
						1111, // time
						2, // 外出枠NO
						"0002");//location

		List<Integer> actualResult = ReflectOutingTimeZone.process(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(98, 97, 93));

		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getOutingFrameNo().v()).isEqualTo(2);//外出枠NO
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getComeBack().get()
				.getTimeDay().getTimeWithDay().get().v()).isEqualTo(1111);//時刻
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getComeBack().get()
				.getTimeDay().getReasonTimeChange().getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);//時刻変更手段
		
		assertThat(dailyApp.getOutingTime().get().getOutingTimeSheets().get(1).getComeBack().get()
				.getLocationCode().get().v()).isEqualTo("0002");//場所コード
	}

}

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
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeZoneAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;

@RunWith(JMockit.class)
public class CancelTimeZoneApplicationTest {

	/*
	 * テストしたい内容
	 * 
	 * →日別勤怠の短時間勤務時間帯.時間帯の値を削除
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * →打刻申請時間帯分類 = 育児 or 介護
	 * 
	 * →日別勤怠の出退勤に勤務NOを存在する
	 */
	@Test
	public void testRemoveShortTime() {

		// case 介護
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// create shorttime no = 1;

		List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp = ReflectApplicationHelper.createlstDisTimezone(1,
				TimeZoneStampClassificationShare.NURSE);// 介護

		List<Integer> actualResult = CancelTimeZoneApplication.process(dailyApp, listDestinationTimeZoneApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(763, 764));

		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets()).isEmpty();

		// case 育児
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD, 1, true);// create shorttime no = 1;

		List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp2 = ReflectApplicationHelper.createlstDisTimezone(1,
				TimeZoneStampClassificationShare.PARENT);// 介護

		List<Integer> actualResult2 = CancelTimeZoneApplication.process(dailyApp2, listDestinationTimeZoneApp2);

		assertThat(actualResult2).isEqualTo(Arrays.asList(759, 760));

		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets()).isEmpty();
	}

	/*
	 * テストしたい内容
	 * 
	 * 日別勤怠の休憩時間帯.時間帯の値を更新するのがnull
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請時間帯分類 = 休憩
	 * 
	 * 日別勤怠の出退勤に勤務NOを存在する
	 */
	@Test
	public void testRemoveBreak() {

		// case 介護
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// 休憩時間帯.休憩枠NO = 1

		List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp = ReflectApplicationHelper.createlstDisTimezone(1,
				TimeZoneStampClassificationShare.BREAK);// 休憩

		List<Integer> actualResult = CancelTimeZoneApplication.process(dailyApp, listDestinationTimeZoneApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(7, 8));
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(0).getStartTime()).isNull();// 開始
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(0).getEndTime()).isNull();// 終了
	}
}

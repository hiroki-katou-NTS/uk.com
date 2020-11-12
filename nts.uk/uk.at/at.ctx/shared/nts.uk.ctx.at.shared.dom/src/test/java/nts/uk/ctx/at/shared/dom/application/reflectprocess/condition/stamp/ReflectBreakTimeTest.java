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
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppOtherShare;

@RunWith(JMockit.class)
public class ReflectBreakTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の休憩時間帯.休憩時間帯を更新する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の休憩時間帯に休憩枠NOを存在する
	 * 
	 * 休憩種類 = 実績
	 */

	@Test
	public void testUpdate() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1

		List<TimeStampAppOtherShare> listTimeStampApp = ReflectApplicationHelper.createlstTimeStampOther(1, // no
				666, // 開始時刻
				1111);// 終了時刻)

		List<Integer> actualResult = ReflectBreakTime.reflect(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(157, 159));
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(666);
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1111);

	}

	/*
	 * テストしたい内容
	 * 
	 * 申請から「日別勤怠の休憩時間帯.休憩時間帯」を作成する。
	 * 
	 * 準備するデータ
	 * 
	 * 日別勤怠の休憩時間帯に休憩枠NOを存在しない 
	 * 
	 * 休憩種類 = 実績
	 */
	@Test
	public void testCreate() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1

		List<TimeStampAppOtherShare> listTimeStampApp = ReflectApplicationHelper.createlstTimeStampOther(2, // no
				666, // 開始時刻
				1111); // 終了時刻

		List<Integer> actualResult = ReflectBreakTime.reflect(dailyApp, listTimeStampApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(163, 165));
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(1).getBreakFrameNo().v()).isEqualTo(2);
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(1).getStartTime().v()).isEqualTo(666);
		assertThat(dailyApp.getBreakTime().get().getBreakTimeSheets().get(1).getEndTime().v()).isEqualTo(1111);

	}

}

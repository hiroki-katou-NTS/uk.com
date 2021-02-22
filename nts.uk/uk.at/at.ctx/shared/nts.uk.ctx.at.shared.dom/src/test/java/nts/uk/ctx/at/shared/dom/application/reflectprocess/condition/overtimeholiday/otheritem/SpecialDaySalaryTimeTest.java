package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;

public class SpecialDaySalaryTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →特定日加給NOを保存するとき特定日加給時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「特定日加給枠NO」＝1
	 * 
	 * 
	 */
	@Test
	public void test1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// 残業枠NO= 1

		val applicationTime = ReflectApplicationHelper.createAppSettingShare(1, AttendanceTypeShare.BONUSSPECIALDAYTIME,
				195);// 残業枠NO = 1

		SpecialDaySalaryTime.process(applicationTime, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getAutoCalRaisingSalarySettings())
						.extracting(x -> x.getBonusPayTimeItemNo(), x -> x.getBonusPayTime().v())
						.containsExactly(Tuple.tuple(1, 195));
	}

	/*
	 * テストしたい内容
	 * 
	 * →特定日加給枠NOを保存するとき特定日加給時間の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「特定日加給枠NO」＝1
	 * 
	 * 
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// 残業枠NO= 1

		val applicationTime = ReflectApplicationHelper.createAppSettingShare(2, AttendanceTypeShare.BONUSSPECIALDAYTIME,
				195);// 残業枠NO = 1

		SpecialDaySalaryTime.process(applicationTime, dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getAutoCalRaisingSalarySettings())
						.extracting(x -> x.getBonusPayTimeItemNo(), x -> x.getBonusPayTime().v())
						.containsExactly(Tuple.tuple(1, 0), Tuple.tuple(2, 195));
	}

}

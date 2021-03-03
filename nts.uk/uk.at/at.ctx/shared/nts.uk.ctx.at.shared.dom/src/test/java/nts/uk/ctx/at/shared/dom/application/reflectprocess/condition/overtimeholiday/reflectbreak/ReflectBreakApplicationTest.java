package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ReflectBreakApplicationTest {

	/*
	 * テストしたい内容
	 * 
	 * →休憩を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * → 「休憩を反映する」設定＝しない
	 * 
	 */
	@Test
	public void test1() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(1, 482, 1082));
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 休憩時間帯(480, 1020)

		ReflectBreakApplication.process(breakTimeOp, dailyApp, new BreakApplication(NotUseAtr.NOT_USE)); // 休憩・外出を申請反映する);

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(480);// 休憩時間帯
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1020);// 休憩時間帯
	}
	
	/*
	 * テストしたい内容
	 * 
	 * →休憩を反映する
	 * 
	 * 準備するデータ
	 * 
	 * → 「休憩を反映する」設定＝する
	 * 
	 */
	@Test
	public void test2() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(1, 482, 1082));
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 休憩時間帯(480, 1020)

		ReflectBreakApplication.process(breakTimeOp, dailyApp, new BreakApplication(NotUseAtr.USE)); // 休憩・外出を申請反映する);

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(482);// 休憩時間帯
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1082);// 休憩時間帯
	}

}

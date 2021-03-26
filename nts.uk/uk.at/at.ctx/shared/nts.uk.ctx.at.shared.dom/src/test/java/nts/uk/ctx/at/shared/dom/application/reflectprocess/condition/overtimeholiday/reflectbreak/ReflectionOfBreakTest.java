package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak.ReflectionOfBreak;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

public class ReflectionOfBreakTest {

	/*
	 * テストしたい内容
	 * 
	 * →休憩枠NOを存在するとき休憩を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →休憩枠NO＝１
	 * 
	 */
	@Test
	public void test1() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(1, 482, 1082));// NO1
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 休憩時間帯(480, 1020)

		ReflectionOfBreak.process(breakTimeOp, dailyApp);

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets())
				.extracting(x -> x.getBreakFrameNo().v(), x -> x.getStartTime().v(), x -> x.getEndTime().v())
				.contains(Tuple.tuple(1, 482, 1082));

	}

	/*
	 * テストしたい内容
	 * 
	 * →休憩枠NOを存在しないとき休憩を反映する
	 * 
	 * 準備するデータ
	 * 
	 * →休憩枠NO＝２
	 * 
	 */
	@Test
	public void test2() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(2, 482, 1082));// NO1
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);// 休憩時間帯(480, 1020)

		ReflectionOfBreak.process(breakTimeOp, dailyApp);

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets())
				.extracting(x -> x.getBreakFrameNo().v(), x -> x.getStartTime().v(), x -> x.getEndTime().v())
				.contains(Tuple.tuple(1, 480, 1020), Tuple.tuple(2, 482, 1082));

	}

}

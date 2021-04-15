package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;

@RunWith(JMockit.class)
public class ReflectStartEndWorkTest {
	
	@Injectable
	private ReflectStartEndWork.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →出退勤を反映する
	 * 
	 * 準備するデータ
	 * 
	 * → 事前事後区分 = 事前
	 */
	@Test
	public void test() {

		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = ReflectStartEndWork.reflect(require, "", dailyApp, timeZoneWithWorkNoLst,
				PrePostAtrShare.PREDICT);
		assertThat(actualResult).isEqualTo(Arrays.asList(3, 4));

	}

	/*
	 * テストしたい内容
	 * 
	 * →出退勤を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * → 事前事後区分 = 事後
	 */
	@Test
	public void testNot() {
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.SCHEDULE,
				2);

		List<Integer> actualResult = ReflectStartEndWork.reflect(require, "", dailyApp, timeZoneWithWorkNoLst,
				PrePostAtrShare.POSTERIOR);
		assertThat(actualResult).isEmpty();
	}
}

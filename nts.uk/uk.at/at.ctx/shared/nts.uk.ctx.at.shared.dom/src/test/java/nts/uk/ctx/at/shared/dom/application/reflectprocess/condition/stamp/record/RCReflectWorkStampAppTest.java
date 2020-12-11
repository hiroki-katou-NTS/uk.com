package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class RCReflectWorkStampAppTest {

	@Injectable
	private RCReflectWorkStampApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 始業終業の反映がある
	 * 
	 * 準備するデータ
	 * 
	 * 事前事後区分＝事前
	 */
	@Test
	public void test() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1
		AppStampShare application = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.PREDICT);
		List<Integer> actualResult = new ArrayList<Integer>();
		actualResult.addAll(RCReflectWorkStampApp.reflect(require, application, dailyApp, null));
		assertThat(actualResult).isEqualTo(Arrays.asList(3, 4));
	}

	/*
	 * テストしたい内容
	 * 
	 * 応援の反映がある
	 * 
	 * 準備するデータ
	 * 
	 * 事前事後区分＝事後 出退勤を反映する
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1
		AppStampShare application = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);
		List<Integer> actualResult = new ArrayList<Integer>();
		ReflectAppStamp reflectApp = reflectTimeLeav(NotUseAtr.USE);
		actualResult.addAll(RCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp));
		assertThat(actualResult).isEqualTo(Arrays.asList(30, 31));
	}

	private ReflectAppStamp reflectTimeLeav(NotUseAtr atr) {
		return new ReflectAppStamp("", atr, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}
}

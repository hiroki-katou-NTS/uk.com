package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class SCReflectWorkStampAppTest {

	@Injectable
	private SCReflectWorkStampApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 打刻申請の反映がない
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請の設定のすべて＝しない
	 */
	@Test
	public void testNoReflectAll() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1

		AppStampShare application = createAppStamp();

		ReflectAppStamp reflectApp = noReflect();//// 応援開始、終了を反映する

		Collection<Integer> actualResult = SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp);

		assertThat(actualResult).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * 打刻申請の反映がある
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請の設定＝する
	 */
	@Test
	public void testReflect() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// no = 1

		AppStampShare application = createAppStamp();

		// 出退勤を反映する
		ReflectAppStamp reflectApp = reflectTimeLeav(NotUseAtr.USE);

		List<Integer> actualResult = new ArrayList<Integer>();
		actualResult.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp));

		assertThat(actualResult).isEqualTo(Arrays.asList(30, 31));

		// 育児時間帯を反映する
		ReflectAppStamp reflectApp1 = reflectChildCare(NotUseAtr.USE);

		List<Integer> actualResult1 = new ArrayList<Integer>();
		application = ReflectApplicationHelper.createAppStamp(TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, TimeZoneStampClassificationShare.PARENT);
		actualResult1.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp1));

		assertThat(actualResult1).isEqualTo(Arrays.asList(759, 760, 763, 764));

		// 臨時出退勤を反映する
		ReflectAppStamp reflectApp2 = reflectTemporary(NotUseAtr.USE);

		List<Integer> actualResult2 = new ArrayList<Integer>();
		application = ReflectApplicationHelper.createAppStamp(TimeStampAppEnumShare.EXTRAORDINARY, TimeZoneStampClassificationShare.PARENT);
		actualResult2.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp2));

		assertThat(actualResult2).isEqualTo(Arrays.asList(50, 51));

		// 介護時間帯を反映する
		ReflectAppStamp reflectApp4 = reflectCare(NotUseAtr.USE);

		List<Integer> actualResult4 = new ArrayList<Integer>();
		application = ReflectApplicationHelper.createAppStamp(null, TimeZoneStampClassificationShare.NURSE);
		actualResult4.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp4));

		assertThat(actualResult4).isEqualTo(Arrays.asList(759, 760, 763, 764));

		// 外出時間帯を反映する
		ReflectAppStamp reflectApp5 = reflectGoOut(NotUseAtr.USE);

		List<Integer> actualResult5 = new ArrayList<Integer>();
		application = ReflectApplicationHelper.createAppStamp(TimeStampAppEnumShare.GOOUT_RETURNING, null);
		actualResult5.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp5));

		assertThat(actualResult5).isEqualTo(Arrays.asList(87, 88));

		// 休憩時間帯を反映する
		ReflectAppStamp reflectApp6 = reflectBreakTime(NotUseAtr.USE);

		List<Integer> actualResult6 = new ArrayList<Integer>();
		application = ReflectApplicationHelper.createAppStamp(null, TimeZoneStampClassificationShare.BREAK);
		actualResult6.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp6));

		assertThat(actualResult6).isEqualTo(Arrays.asList(7, 8, 157, 159));

	}

	private ReflectAppStamp noReflect() {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, // 出退勤を反映する
				NotUseAtr.NOT_USE, // 育児時間帯を反映する
				NotUseAtr.NOT_USE, // 臨時出退勤を反映する
				NotUseAtr.NOT_USE, // 応援開始、終了を反映する
				NotUseAtr.NOT_USE, // 介護時間帯を反映する
				NotUseAtr.NOT_USE, // 外出時間帯を反映する
				NotUseAtr.NOT_USE);// 休憩時間帯を反映する

	}

	private ReflectAppStamp reflectTimeLeav(NotUseAtr atr) {
		return new ReflectAppStamp("", atr, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}

	private ReflectAppStamp reflectChildCare(NotUseAtr atr) {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, atr, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}

	private ReflectAppStamp reflectTemporary(NotUseAtr atr) {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, atr, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}

//	private ReflectAppStamp reflectSupport(NotUseAtr atr) {
//		return new ReflectAppStamp("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, atr, NotUseAtr.NOT_USE,
//				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
//	}

	private ReflectAppStamp reflectCare(NotUseAtr atr) {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, atr,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}

	private ReflectAppStamp reflectGoOut(NotUseAtr atr) {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, atr, NotUseAtr.NOT_USE);
	}

	private ReflectAppStamp reflectBreakTime(NotUseAtr atr) {
		return new ReflectAppStamp("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, atr);
	}

	private AppStampShare createAppStamp() {
		return ReflectApplicationHelper.createAppStamp(TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, TimeZoneStampClassificationShare.BREAK);
	}


}

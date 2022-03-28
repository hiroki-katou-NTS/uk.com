package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.stamp.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *応援の反映
 */
@RunWith(JMockit.class)
public class StampAppReflectTest {

	@Injectable
	private StampAppReflect.Require require;

	/*
	 * テストしたい内容
	 * 
	 * 応援開始・終了の反映がない
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請の反映.応援開始・終了 = しない
	 */
	@Test
	public void testNoReflect() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// no = 1

		List<TimeStampAppShare> lstStampAppShare = ReflectApplicationHelper.createlstTimeStamp(
				StartEndClassificationShare.START, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo");// 勤務場所
		AppStampShare application = new AppStampShare(lstStampAppShare, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION,
						PrePostAtrShare.PREDICT));

		StampAppReflect reflectApp = reflect(NotUseAtr.NOT_USE);//// 応援開始、終了を反映する
		List<Integer> actualResult = reflectApp.reflectSupport(require, application, dailyApp);

		assertThat(actualResult).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * 応援開始・終了の反映がある
	 * 
	 * 準備するデータ
	 * 
	 * 打刻申請の反映.応援開始・終了 = する
	 */
	@Test
	public void testReflect() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// no = 1

		List<TimeStampAppShare> lstStampAppShare = ReflectApplicationHelper.createlstTimeStamp(
				TimeStampAppEnumShare.CHEERING, // 応援"
				StartEndClassificationShare.START, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo");// 勤務場所
		AppStampShare application = new AppStampShare(lstStampAppShare, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), ReflectApplicationHelper.createAppShare(ApplicationTypeShare.STAMP_APPLICATION,
						PrePostAtrShare.PREDICT));

		StampAppReflect reflectApp = reflect(NotUseAtr.USE);//// 応援開始、終了を反映する
		List<Integer> actualResult = reflectApp.reflectSupport(require, application, dailyApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(929, 922, 921));
	}

	private StampAppReflect reflect(NotUseAtr atr) {
		return new StampAppReflect("", NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, atr,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);

	}
}

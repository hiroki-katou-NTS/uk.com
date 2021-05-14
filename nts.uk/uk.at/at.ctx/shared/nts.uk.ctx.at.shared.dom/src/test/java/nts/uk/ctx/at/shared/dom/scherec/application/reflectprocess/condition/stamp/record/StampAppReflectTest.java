package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.stamp.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *打刻申請を反映する（勤務実績）
 */
@RunWith(JMockit.class)
public class StampAppReflectTest {

	@Injectable
	private StampAppReflect.Require require;

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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// no = 1
		
		AppStampShare application = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.PREDICT);
		application.getListTimeStampApp().addAll(ReflectApplicationHelper.createlstTimeStamp(
				StartEndClassificationShare.END, // 開始終了区分
				666, // 時刻
				1, // 応援勤務枠No
				"lo"));// 勤務場所)
		List<Integer> actualResult = new ArrayList<Integer>();
		actualResult.addAll(new StampAppReflect().reflectRecord(require, application, dailyApp));
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
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// no = 1
		AppStampShare application = ReflectApplicationHelper.createAppStamp(PrePostAtrShare.POSTERIOR);
		List<Integer> actualResult = new ArrayList<Integer>();
		StampAppReflect reflectApp = reflectTimeLeav(NotUseAtr.USE);
		actualResult.addAll(reflectApp.reflectRecord(require, application, dailyApp));
		assertThat(actualResult).isEqualTo(Arrays.asList(30, 31));
	}

	private StampAppReflect reflectTimeLeav(NotUseAtr atr) {
		return new StampAppReflect("", atr, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
	}
}

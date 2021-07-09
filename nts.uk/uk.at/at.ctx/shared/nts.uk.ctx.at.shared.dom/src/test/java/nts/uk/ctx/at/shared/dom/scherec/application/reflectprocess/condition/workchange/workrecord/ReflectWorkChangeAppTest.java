package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.workchange.workrecord;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import mockit.Injectable;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *勤務変更申請の反映（勤務実績）
 */
public class ReflectWorkChangeAppTest {

	@Injectable
	private ReflectWorkChangeApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →出退勤を反映する
	 * 
	 * 準備するデータ
	 * 
	 * → 「出退勤を反映するか」 = true;
	 */
	@Test
	public void testReflectAtt() {

		// 日別勤怠(申請反映用Work)
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD,
				2);

		// 勤務変更申請の反映
		ReflectWorkChangeApp reflectWorkChange = new ReflectWorkChangeApp("", NotUseAtr.USE);// 出退勤を反映するか＝
																												// true;
		List<Integer> actualResult = new ArrayList<Integer>();
		actualResult.addAll(reflectWorkChange.reflectRecord(require, createAppChange(ScheduleRecordClassifi.RECORD),
				dailyApp));

		assertThat(actualResult).isEmpty();

	}

	/*
	 * テストしたい内容
	 * 
	 * →出退勤を反映しない
	 * 
	 * 準備するデータ
	 * 
	 * → 「出退勤を反映するか」 = false;
	 */
	@Test
	public void testNoReflectAtt() {
		// 日別勤怠(申請反映用Work)
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createDailyRecord(ScheduleRecordClassifi.RECORD,
				2);

		// 勤務変更申請の反映
		ReflectWorkChangeApp reflectWorkChange = new ReflectWorkChangeApp("", NotUseAtr.NOT_USE);// 出退勤を反映するか
																													// =
																													// false;
		List<Integer> actualResult = new ArrayList<Integer>();
		actualResult.addAll(reflectWorkChange.reflectRecord(require, createAppChange(ScheduleRecordClassifi.RECORD),
				dailyApp));

		assertThat(actualResult).isEmpty();

	}

	public static AppWorkChangeShare createAppChange(ScheduleRecordClassifi classifi) {
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<>();
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(1, 1, 1));
		timeZoneWithWorkNoLst.add(new TimeZoneWithWorkNo(2, 2, 2));

		return new AppWorkChangeShare(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, Optional.empty(), Optional.empty(),
				timeZoneWithWorkNoLst, ReflectApplicationHelper.createAppShare(PrePostAtrShare.POSTERIOR));
	}

}

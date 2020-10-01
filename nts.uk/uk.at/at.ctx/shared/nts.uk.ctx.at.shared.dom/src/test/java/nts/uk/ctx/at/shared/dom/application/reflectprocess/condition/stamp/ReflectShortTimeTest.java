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
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;

@RunWith(JMockit.class)
public class ReflectShortTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →申請から「日別勤怠の短時間勤務時間帯.時間帯」を更新する。 →
	 * 
	 * 準備するデータ
	 * 
	 * →日別勤怠の短時間勤務時間帯.時間帯に勤務NOを存在する
	 * 
	 * →打刻申請時間帯分類 = 介護 or 育児
	 */

	@Test
	public void testUpdate() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1

		// case 介護
		List<Integer> actualResult = ReflectShortTime.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStampOther(1, // 打刻NO= 1
						600, // 開始時刻
						1200, // 終了時刻
						TimeZoneStampClassificationShare.NURSE));// 打刻申請時間帯分類 = 介護

		assertThat(actualResult).isEqualTo(Arrays.asList(759, 760));

		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(0).getStartTime().v()).isEqualTo(600);// 開始
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(0).getEndTime().v()).isEqualTo(1200); // 終了
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(0).getChildCareAttr())
				.isEqualTo(ChildCareAttribute.CARE);// 育児介護区分

		// case 育児
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1
		List<Integer> actualResult2 = ReflectShortTime.reflect(dailyApp2,
				ReflectApplicationHelper.createlstTimeStampOther(1, // 打刻NO= 1
						600, // 開始時刻
						1200, // 終了時刻
						TimeZoneStampClassificationShare.PARENT));// 打刻申請時間帯分類 = 介護

		assertThat(actualResult2).isEqualTo(Arrays.asList(763, 764));

		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(0).getStartTime().v()).isEqualTo(600);// 開始
		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(0).getEndTime().v()).isEqualTo(1200); // 終了
		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(0).getChildCareAttr())
				.isEqualTo(ChildCareAttribute.CHILD_CARE);// 育児介護区分

	}

	/*
	 * テストしたい内容
	 * 
	 * →申請から「日別勤怠の短時間勤務時間帯.時間帯.出勤」を作成する。
	 * 
	 * 準備するデータ
	 * 
	 * →日別勤怠の短時間勤務時間帯.時間帯に勤務NOを存在しない
	 * 
	 * →打刻申請時間帯分類 = 介護or育児
	 */
	@Test
	public void testCreate() {

		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1

		List<Integer> actualResult = ReflectShortTime.reflect(dailyApp,
				ReflectApplicationHelper.createlstTimeStampOther(2, // 打刻NO= 2
						600, // 開始時刻
						1200, // 終了時刻
						TimeZoneStampClassificationShare.NURSE));// 打刻申請時間帯分類 = 介護

		assertThat(actualResult).isEqualTo(Arrays.asList(761, 762));
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(1).getStartTime().v()).isEqualTo(600);// 開始
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(1).getEndTime().v()).isEqualTo(1200); // 終了
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(1).getChildCareAttr())
				.isEqualTo(ChildCareAttribute.CARE);// 育児介護区分
		assertThat(dailyApp.getShortTime().get().getShortWorkingTimeSheets().get(1).getShortWorkTimeFrameNo().v())
				.isEqualTo(2);// 短時間勤務枠NO

		// case 育児
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper
				.createRCWithTimeLeav(ScheduleRecordClassifi.SCHEDULE, 1, true);// 打刻NO= 1
		List<Integer> actualResult2 = ReflectShortTime.reflect(dailyApp2,
				ReflectApplicationHelper.createlstTimeStampOther(2, // 打刻NO= 2
						600, // 開始時刻
						1200, // 終了時刻
						TimeZoneStampClassificationShare.PARENT));// 打刻申請時間帯分類 = 介護

		assertThat(actualResult2).isEqualTo(Arrays.asList(765, 766));

		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(1).getStartTime().v()).isEqualTo(600);// 開始
		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(1).getEndTime().v()).isEqualTo(1200); // 終了
		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(1).getChildCareAttr())
				.isEqualTo(ChildCareAttribute.CHILD_CARE);// 育児介護区分
		assertThat(dailyApp2.getShortTime().get().getShortWorkingTimeSheets().get(1).getShortWorkTimeFrameNo().v())
				.isEqualTo(2);// 短時間勤務枠NO

	}

}

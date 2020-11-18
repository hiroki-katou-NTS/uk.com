package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;

@RunWith(JMockit.class)
public class CancelSupportStartEndTest {

	/*
	 * テストしたい内容
	 * 
	 * 日別勤怠の応援作業時間帯.時間帯の値を削除
	 * 
	 * 
	 * 準備するデータ
	 * 
	 * 開始終了区分が開始or終了
	 */
	@Test
	public void testAtt() {

		// 開始終了区分が開始
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// 勤務no = 1

		List<DestinationTimeAppShare> listDestinationTimeApp = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, StartEndClassificationShare.START, // 開始
				1);// // 応援勤務枠No

		List<Integer> actualResult = CancelSupportStartEnd.process(dailyApp, listDestinationTimeApp);

		assertThat(actualResult).isEqualTo(Arrays.asList(929, 921));

		assertThat(dailyApp.getOuenTimeSheet().get(0).getTimeSheet().getStart().get().getTimeWithDay())
				.isEqualTo(Optional.empty());// 時刻

		assertThat(dailyApp.getOuenTimeSheet().get(0).getTimeSheet().getStart().get().getReasonTimeChange()
				.getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);// 時刻変更手段

		assertThat(dailyApp.getOuenTimeSheet().get(0).getWorkContent().getWorkplace().getWorkLocationCD())
				.isNull();// 勤務場所コード

		/************************************************************************************************/
		// 開始終了区分が終了
		DailyRecordOfApplication dailyApp2 = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1, true);// 勤務no = 1

		List<DestinationTimeAppShare> listDestinationTimeApp2 = ReflectApplicationHelper.createlstDisTimeStamp(
				TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT, StartEndClassificationShare.END, // 開始
				1);// // 応援勤務枠No

		List<Integer> actualResult2 = CancelSupportStartEnd.process(dailyApp2, listDestinationTimeApp2);

		assertThat(actualResult2).isEqualTo(Arrays.asList(930, 921));

		assertThat(dailyApp2.getOuenTimeSheet().get(0).getTimeSheet().getEnd().get().getTimeWithDay())
				.isEqualTo(Optional.empty());// 時刻

		assertThat(dailyApp2.getOuenTimeSheet().get(0).getTimeSheet().getEnd().get().getReasonTimeChange()
				.getTimeChangeMeans()).isEqualTo(TimeChangeMeans.APPLICATION);// 時刻変更手段

		assertThat(dailyApp2.getOuenTimeSheet().get(0).getWorkContent().getWorkplace().getWorkLocationCD())
				.isNull();// 勤務場所コード
	}

}

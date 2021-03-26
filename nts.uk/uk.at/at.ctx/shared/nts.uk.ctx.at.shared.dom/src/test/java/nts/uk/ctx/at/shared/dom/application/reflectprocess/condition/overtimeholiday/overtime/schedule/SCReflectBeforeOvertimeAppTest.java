package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.OvertimeAppAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.overtime.schedule.SCReflectBeforeOvertimeApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;


@RunWith(JMockit.class)
public class SCReflectBeforeOvertimeAppTest {

	@Injectable
	private SCReflectBeforeOvertimeApp.Require require;

	/*
	 * テストしたい内容
	 * 
	 * →勤務情報と休憩に反映する
	 * 
	 * 準備するデータ
	 * 
	 * →「勤務情報、出退勤を反映する」設定＝する
	 * 
	 * → 「休憩を反映する」設定＝する
	 * 
	 */
	@Test
	public void test1() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		List<Integer> lstResult = SCReflectBeforeOvertimeApp.process(require, createOverTimeApp(), dailyApp,
				createReflectSetting(NotUseAtr.USE));

		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTimeCode().v()).isEqualTo("003");// 就業時間帯コード
		assertThat(dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).isEqualTo("003");// 勤務種類コード

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(482);// 休憩時間帯
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1082);// 休憩時間帯

		assertThat(lstResult).isEqualTo(Arrays.asList(28, 1292, 1293, 29));

	}

	/*
	 * テストしたい内容
	 * 
	 * →休憩に反映するだけ
	 * 
	 * 準備するデータ
	 * 
	 * →「勤務情報、出退勤を反映する」設定＝しない
	 * 
	 * → 「休憩を反映する」設定＝する
	 * 
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.SCHEDULE, 1);
		List<Integer> lstResult = SCReflectBeforeOvertimeApp.process(require, createOverTimeApp(), dailyApp,
				createReflectSetting(NotUseAtr.NOT_USE));

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(482);// 休憩時間帯
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1082);// 休憩時間帯

		assertThat(lstResult).isEmpty();

	}

	private AppOverTimeShare createOverTimeApp() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(1, 482, 1082));
		Optional<WorkInformation> workInfoOp = Optional.of(new WorkInformation("003", "003"));
		return new AppOverTimeShare(OvertimeAppAtrShare.NORMAL_OVERTIME,
				new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), Optional.empty(), new ArrayList<>(),
						new ArrayList<>()),
				breakTimeOp, // 休憩時間帯
				new ArrayList<>(), //
				workInfoOp, // 勤務情報
				Optional.empty());
	}

	private BeforeOtWorkAppReflect createReflectSetting(NotUseAtr reflectWork) {
		return new BeforeOtWorkAppReflect(new BreakApplication(NotUseAtr.USE), // 休憩・外出を申請反映する
				reflectWork, // 勤務情報、出退勤を反映する
				NotUseAtr.NOT_USE);// 残業時間を実績項目へ反映する
	}
}

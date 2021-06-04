package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.overtime.schedule;

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
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.BeforeOtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *事前残業申請の反映（勤務予定）
 */
@RunWith(JMockit.class)
public class BeforeOtWorkAppReflectTest {

	@Injectable
	private BeforeOtWorkAppReflect.Require require;

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
		List<Integer> lstResult = createReflectSetting(NotUseAtr.USE).processSC(require, "", createOverTimeApp(), dailyApp
				);

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
		List<Integer> lstResult = createReflectSetting(NotUseAtr.NOT_USE).processSC(require, "", createOverTimeApp(),
				dailyApp);

		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getStartTime().v()).isEqualTo(482);// 休憩時間帯
		assertThat(dailyApp.getBreakTime().getBreakTimeSheets().get(0).getEndTime().v()).isEqualTo(1082);// 休憩時間帯

		assertThat(lstResult).isEmpty();

	}

	private AppOverTimeShare createOverTimeApp() {
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();
		breakTimeOp.add(new TimeZoneWithWorkNo(1, 482, 1082));
		Optional<WorkInformation> workInfoOp = Optional.of(new WorkInformation("003", "003"));
		return new AppOverTimeShare(
				new ApplicationTimeShare(new ArrayList<>(), Optional.empty(), Optional.empty(), new ArrayList<>(),
						new ArrayList<>()),
				breakTimeOp, // 休憩時間帯
				new ArrayList<>(), //
				workInfoOp // 勤務情報
				);
	}

	private BeforeOtWorkAppReflect createReflectSetting(NotUseAtr reflectWork) {
		return new BeforeOtWorkAppReflect(new BreakApplication(NotUseAtr.USE), // 休憩・外出を申請反映する
				reflectWork, // 勤務情報、出退勤を反映する
				NotUseAtr.NOT_USE);// 残業時間を実績項目へ反映する
	}
}

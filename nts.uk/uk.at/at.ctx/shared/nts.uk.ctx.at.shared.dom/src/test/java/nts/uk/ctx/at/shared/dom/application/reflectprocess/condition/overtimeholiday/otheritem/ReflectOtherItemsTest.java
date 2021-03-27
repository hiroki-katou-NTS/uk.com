package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AttendanceTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         その他項目の反映
 */
public class ReflectOtherItemsTest {

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →乖離理由の反映
	 * 
	 * →①[乖離理由を反映する] = する →反映する
	 * 
	 * →②[乖離理由を反映する] = しない →反映しない
	 * 
	 * 準備するデータ
	 * 
	 * →①[乖離理由を反映する] = する
	 * 
	 * →②[乖離理由を反映する] = しない
	 */
	@Test
	public void test1() {
		val overTimeApp = ReflectApplicationHelper.createOverTimeReason("ReasonName", "ReasonCode", 1);
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);//
		// ①[乖離理由を反映する] = する →反映する
		OthersReflect othersReflect = new OthersReflect(NotUseAtr.USE, NotUseAtr.NOT_USE);

		othersReflect.process(overTimeApp.getApplicationTime(), dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime()
				.getDivergenceTime()).extracting(x -> x.getDivReason().get().v(), x -> x.getDivResonCode().get().v())
						.contains(Tuple.tuple("ReasonName", "ReasonCode"));

		// ②[乖離理由を反映する] = しない →反映しない
		othersReflect = new OthersReflect(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);//

		othersReflect.process(overTimeApp.getApplicationTime(), dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime()
				.getDivergenceTime())
						.extracting(x -> x.getDivTimeId(), x -> x.getDivReason().map(y -> y.v()).orElse(null),
								x -> x.getDivResonCode().map(y -> y.v()).orElse(null))
						.contains(Tuple.tuple(1, null, null));

	}

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →加給時間の反映
	 * 
	 * →①[加給時間を反映する] = する →反映する
	 * 
	 * →②[加給時間を反映する] = しない →反映しない
	 * 
	 * 準備するデータ
	 * 
	 * →①[加給時間を反映する] = する
	 * 
	 * →②[加給時間を反映する] = しない
	 */
	@Test
	public void test2() {
		val overTimeApp = ReflectApplicationHelper.createOverTime(AttendanceTypeShare.BONUSPAYTIME, 195);// 加給時間
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper
				.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);//

		// ①[加給時間を反映する] = する →反映する
		OthersReflect othersReflect = new OthersReflect(NotUseAtr.NOT_USE, NotUseAtr.USE);

		othersReflect.process(overTimeApp.getApplicationTime(), dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes())
						.extracting(x -> x.getBonusPayTimeItemNo(), x -> x.getBonusPayTime().v())
						.containsExactly(Tuple.tuple(1, 195));

		// [加給時間を反映する] = しない →反映しない
		dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD, 1);//

		// ①[加給時間を反映する] = する →反映する
		othersReflect = new OthersReflect(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);

		othersReflect.process(overTimeApp.getApplicationTime(), dailyApp);

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes())
						.extracting(x -> x.getBonusPayTimeItemNo(), x -> x.getBonusPayTime().v())
						.containsExactly(Tuple.tuple(1, 0));

	}

}

package nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.condition.overtimeholiday.otheritem.reason;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

/**
 * @author thanh_nx
 *
 *         乖離理由の反映
 */
public class OthersReflectTest {

	/*
	 * テストしたい内容
	 * 
	 * →乖離時間NOを保存するとき乖離理由の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「乖離時間NO」＝1
	 * 
	 * 
	 */
	@Test
	public void test() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// 「乖離時間NO」＝1, 乖離理由 = エンプティー
		val overTimeApp = ReflectApplicationHelper.createOverTimeReason("ReasonName", "ReasonCode", 1);// 「乖離時間NO」＝1

		NtsAssert.Invoke.staticMethod(OthersReflect.class, "processReasonDissociation", dailyApp,
				overTimeApp.getApplicationTime().getReasonDissociation());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime()
				.getDivergenceTime())
						.extracting(x -> x.getDivTimeId(), x -> x.getDivReason().get().v(),
								x -> x.getDivResonCode().get().v())
						.contains(Tuple.tuple(1, "ReasonName", "ReasonCode"));

	}

	/*
	 * テストしたい内容
	 * 
	 * →乖離時間NOを保存するとき乖離理由の反映
	 * 
	 * 準備するデータ
	 * 
	 * → 「乖離時間NO」＝２
	 * 
	 * 
	 */
	@Test
	public void test2() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeavFull(ScheduleRecordClassifi.RECORD,
				1);// 「乖離時間NO」＝1, 乖離理由 = エンプティー
		val overTimeApp = ReflectApplicationHelper.createOverTimeReason("ReasonName", "ReasonCode", 2);// 「乖離時間NO」＝2

		NtsAssert.Invoke.staticMethod(OthersReflect.class, "processReasonDissociation", dailyApp,
				overTimeApp.getApplicationTime().getReasonDissociation());

		assertThat(dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime()
				.getDivergenceTime())
						.extracting(x -> x.getDivTimeId(), x -> x.getDivReason().map(y -> y.v()).orElse(null),
								x -> x.getDivResonCode().map(y -> y.v()).orElse(null))
						.contains(Tuple.tuple(1, null, null), Tuple.tuple(2, "ReasonName", "ReasonCode"));

	}
}

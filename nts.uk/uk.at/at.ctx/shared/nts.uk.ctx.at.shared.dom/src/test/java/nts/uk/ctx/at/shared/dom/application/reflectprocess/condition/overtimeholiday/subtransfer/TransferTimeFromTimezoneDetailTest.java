package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.MaximumTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.SubstituteTransferProcess;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.TransferResultFrame;

/**
 * @author thanh_nx
 *
 *         時間帯から時間の振替
 */
public class TransferTimeFromTimezoneDetailTest {

	/*
	 * テストしたい内容
	 * 
	 * →時間帯から時間の振替
	 * 
	 * 準備するデータ
	 * 
	 * →input. 振替可能時間 <= input.最大時間とinput. 時間外労働時間.時間 <= input.振替可能時間
	 */
	@Test
	public void test1() {

		val timeAfterReflectApp = new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444));

		TransferResultFrame result = NtsAssert.Invoke.staticMethod(SubstituteTransferProcess.class,
				"processTimeZoneDetail", 700, 800, timeAfterReflectApp);
		assertThat(result.getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getNo()).isEqualTo(1);
		assertThat(result.getMaximumTime().getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getTransferTime().v()).isEqualTo(1110);

	}

	/*
	 * テストしたい内容
	 * 
	 * →時間帯から時間の振替
	 * 
	 * 準備するデータ
	 * 
	 * →input. 振替可能時間 <= input.最大時間とinput. 時間外労働時間.時間 >input.振替可能時間
	 */
	@Test
	public void test2() {

		val timeAfterReflectApp = new MaximumTime(1, new AttendanceTime(900), new AttendanceTime(444));
		TransferResultFrame result = NtsAssert.Invoke.staticMethod(SubstituteTransferProcess.class,
				"processTimeZoneDetail", 700, 800, timeAfterReflectApp);
		assertThat(result.getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getNo()).isEqualTo(1);
		assertThat(result.getMaximumTime().getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getTransferTime().v()).isEqualTo(1144);

	}

	/*
	 * テストしたい内容
	 * 
	 * →時間帯から時間の振替
	 * 
	 * 準備するデータ
	 * 
	 * →input. 振替可能時間 ＞ input.最大時間とinput. 時間外労働時間.時間 <= input.最大時間
	 */
	@Test
	public void test3() {

		val timeAfterReflectApp = new MaximumTime(1, new AttendanceTime(600), new AttendanceTime(444));
		TransferResultFrame result = NtsAssert.Invoke.staticMethod(SubstituteTransferProcess.class,
				"processTimeZoneDetail", 800, 700, timeAfterReflectApp);
		assertThat(result.getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getNo()).isEqualTo(1);
		assertThat(result.getMaximumTime().getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getTransferTime().v()).isEqualTo(1044);

	}

	/*
	 * テストしたい内容
	 * 
	 * →時間帯から時間の振替
	 * 
	 * 準備するデータ
	 * 
	 * →input. 振替可能時間 > input.最大時間とinput. 時間外労働時間.時間 >input.最大時間
	 */
	@Test
	public void test4() {

		val timeAfterReflectApp = new MaximumTime(1, new AttendanceTime(900), new AttendanceTime(444));
		TransferResultFrame result = NtsAssert.Invoke.staticMethod(SubstituteTransferProcess.class,
				"processTimeZoneDetail", 800, 700, timeAfterReflectApp);
		assertThat(result.getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getNo()).isEqualTo(1);
		assertThat(result.getMaximumTime().getTime().v()).isEqualTo(0);
		assertThat(result.getMaximumTime().getTransferTime().v()).isEqualTo(1144);

	}
}

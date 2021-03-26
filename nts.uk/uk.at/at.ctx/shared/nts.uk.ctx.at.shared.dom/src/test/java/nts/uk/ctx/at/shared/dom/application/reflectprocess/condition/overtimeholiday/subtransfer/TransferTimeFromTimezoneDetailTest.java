package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.subtransfer.MaximumTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.subtransfer.TransferTimeFromTimezoneDetail;

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
		Pair<Integer, MaximumTime> result = TransferTimeFromTimezoneDetail.process(700, 800, timeAfterReflectApp);
		assertThat(result.getLeft()).isEqualTo(-410);
		assertThat(result.getRight().getNo()).isEqualTo(1);
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(1110);

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
		Pair<Integer, MaximumTime> result = TransferTimeFromTimezoneDetail.process(700, 800, timeAfterReflectApp);
		assertThat(result.getLeft()).isEqualTo(-444);
		assertThat(result.getRight().getNo()).isEqualTo(1);
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(1144);

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
		Pair<Integer, MaximumTime> result = TransferTimeFromTimezoneDetail.process(800, 700, timeAfterReflectApp);
		assertThat(result.getLeft()).isEqualTo(-244);
		assertThat(result.getRight().getNo()).isEqualTo(1);
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(1044);

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
		Pair<Integer, MaximumTime> result = TransferTimeFromTimezoneDetail.process(800, 700, timeAfterReflectApp);
		assertThat(result.getLeft()).isEqualTo(-344);
		assertThat(result.getRight().getNo()).isEqualTo(1);
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(1144);

	}
}

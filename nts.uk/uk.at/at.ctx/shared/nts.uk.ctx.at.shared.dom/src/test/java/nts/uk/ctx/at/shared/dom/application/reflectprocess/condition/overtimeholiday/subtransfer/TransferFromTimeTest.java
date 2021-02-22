package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@RunWith(JMockit.class)
public class TransferFromTimeTest {

	/*
	 * テストしたい内容
	 * 
	 * →振替可能時間= 0の場合、時間外労働時間（振替用）値のままです
	 * 
	 * 準備するデータ
	 * 
	 * →未割り当ての時間を算出<=0
	 * 
	 */
	@Test
	public void test() {

		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(999)));

		val result = TransferFromTime.process(0, timeAfterReflectApp);

		assertThat(result).extracting(x -> x.getNo(), x -> x.getTransferTime().v(), //
				x -> x.getTime().v())//
				.contains(Tuple.tuple(1, 999, 666));
	}

	/*
	 * テストしたい内容
	 * 
	 * →振替可能時間> 0の場合、時間外労働時間（振替用）値を更新する
	 * 
	 * 準備するデータ
	 * 
	 * →未割り当ての時間を算出<=0
	 * 
	 */
	@Test
	public void test2() {
		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));

		val result = TransferFromTime.process(111, timeAfterReflectApp);

		assertThat(result).extracting(x -> x.getNo(), x -> x.getTransferTime().v(), //
				x -> x.getTime().v())//
				.contains(Tuple.tuple(1, 555, 111));//666-111, 666-111-444
	}

}

package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

public class TimeToTimeTransferTest {

	/*
	 * テストしたい内容
	 * 
	 * →「時間外労働時間.時間<= input. 振替可能時間」のとき時間外労働時間.振替時間 += 時間外労働時間.時間
	 * 
	 * 準備するデータ
	 * 
	 * →「時間外労働時間.時間<= input. 振替可能時間」
	 */
	@Test
	public void test1() {
		val timeReflectApp = new MaximumTime(1, new AttendanceTime(10), //時間
				new AttendanceTime(9));//振替時間
		
		val result = TimeToTimeTransfer.process(11, timeReflectApp);
		
		assertThat(result.getLeft()).isEqualTo(-8);
		
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(19);
	}

	/*
	 * テストしたい内容
	 * 
	 * →「時間外労働時間.時間＞ input. 振替可能時間」のとき時間外労働時間.振替時間 += 振替可能時間
	 * 
	 * 準備するデータ
	 * 
	 * →「時間外労働時間.時間>input. 振替可能時間」
	 */
	@Test
	public void test2() {
		val timeReflectApp = new MaximumTime(1, new AttendanceTime(10), //時間
				new AttendanceTime(9));//振替時間
		
		val result = TimeToTimeTransfer.process(8, timeReflectApp);
		
		assertThat(result.getLeft()).isEqualTo(-9);
		
		assertThat(result.getRight().getTime().v()).isEqualTo(0);
		
		assertThat(result.getRight().getTransferTime().v()).isEqualTo(17);
	}

}

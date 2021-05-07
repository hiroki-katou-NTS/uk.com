package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

@RunWith(JMockit.class)
public class ChildCareNurseUsedNumberTest {

	@Injectable
	private ChildCareNurseUsedNumber.RequireM3 require;

	/**
	 * 子の看護介護使用数
	 * 1.時間使用数を日数に積み上げ
	 */
	@Test
	// パターン１：積み上げた結果、日数と時間両方ある場合
	// 契約時間　8:00
	// 使用時間　1020（=17:00）
	// 期待値：2日と1:00
	public void usedDayfromUsedTime1() {
		LaborContractTime contractTime = new LaborContractTime(480);//契約時間=8時間

		val childCare = usedNumber(0, 60*8*2+60);//積み上げ前の使用時間：例）1020=17時間、960=16時間

		val stack = childCare.usedDayfromUsedTime(Optional.ofNullable(contractTime));

		val expect = usedNumber(2.0, 60); // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}

	@Test
	// パターン２：積み上げた結果、日数と時間両方ある場合（日数加算の確認）
	// 契約時間 8:00
	// 使用日数 1.5日、使用時間1020分（=17:00）
	// 期待値：3.5日と1:00
	public void usedDayfromUsedTime2() {
		LaborContractTime contractTime = new LaborContractTime(480);//契約時間=8時間

		val childCare = usedNumber(1.5, 60*8*2+60);//積み上げ前の使用時間：例）1020=17時間、960=16時間

		val stack = childCare.usedDayfromUsedTime(Optional.ofNullable(contractTime));

		val expect = usedNumber(3.5, 60); // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}

	@Test
	// パターン３：契約時間1日に満たない場合
	// 契約時間 7:30
	// 使用時間 449（=7:29）
	// 期待値：0日と7:29
	public void usedDayfromUsedTime3() {
		LaborContractTime contractTime = new LaborContractTime(450);//契約時間=7.5時間

		val childCare = usedNumber(0.0, 449); // 積み上げ前の使用時間（日数、時間）

		val stack = childCare.usedDayfromUsedTime(Optional.ofNullable(contractTime));

		val expect = usedNumber(0.0, 449); // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}

	@Test
	// パターン４：時間が0:00になる場合（契約時間＝使用時間）
	// 契約時間8:00
	// 使用時間480（=8:00）
	// 期待値：1.0日と0:00
	public void usedDayfromUsedTime4() {

		LaborContractTime contractTime = new LaborContractTime(480);//契約時間=8時間

		val childCare = usedNumber(0, 60*8); // 積み上げ前の使用時間（日数、時間）

		val stack = childCare.usedDayfromUsedTime(Optional.ofNullable(contractTime));

		val expect = usedNumber(1.0, 0);  // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}

	// 子の看護介護使用数
	private ChildCareNurseUsedNumber usedNumber(double usedDay, Integer usedTimes) {
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(usedDay),
				usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes)));
	}
	@Test
	// パターン５：契約時間が0:00の場合
	// 契約時間 0:00
	// 使用日数 1.5日、使用時間1020分（=17:00）
	// 期待値：1.5日と17:00
	public void usedDayfromUsedTime5() {

		LaborContractTime contractTime = new LaborContractTime(0);//契約時間=0時間

		val childCare = usedNumber(1.5, 60*8*2+60);//積み上げ前の使用時間：例）1020=17時間、960=16時間

		val stack = childCare.usedDayfromUsedTime(Optional.ofNullable(contractTime));
		val expect = usedNumber(1.5, 1020); // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}

/*************************
	@Test
	// パターン６：契約時間が取得できない場合
	// 契約時間 取得できない
	// 使用日数 1.5日、使用時間1020分（=17:00）
	// 期待値：エラー
	public void usedDayfromUsedTime6() {

		Optional<LaborContractTime> contractTime = Optional.empty();

		val childCare = usedNumber(1.5, 60*8*2+60);//積み上げ前の使用時間：例）1020=17時間、960=16時間

		val stack = childCare.usedDayfromUsedTime(contractTime);
		val expect = usedNumber(1.5, 1020); // 期待値：使用日数、使用時間

		assertThat(stack.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(stack.getUsedTimes().get()).isEqualTo(expect.getUsedTimes().get());
	}
*********************/

}

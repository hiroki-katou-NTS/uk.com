package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;

@RunWith(JMockit.class)
public class ChildCareNurseRemainingNumberTest {
	/**
	 * 残数を使い過ぎていないか
	 */
	// パターン１：日数の確認
	@Test
	public void testCheckOverUpperLimit1() {
		val childCare = remNum(0.0, 0); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isTrue();
	}
	// パターン２：日数の確認
	@Test
	public void testCheckOverUpperLimit2() {
		val childCare = remNum(-0.5, 0); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isFalse(); //使い過ぎ
	}
	// パターン３：時間の確認
	@Test
	public void testCheckOverUpperLimit3() {
		val childCare = remNum(0.0, 0); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isTrue();
	}
	// パターン４：時間の確認
	@Test
	public void testCheckOverUpperLimit4() {
		val childCare = remNum(0.0, -1); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isFalse(); //使い過ぎ
	}
	// パターン５：日数と時間の確認
	@Test
	public void testCheckOverUpperLimit5() {
		val childCare = remNum(0.0, 0); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isTrue();
	}
	// パターン６：日数と時間の確認
	@Test
	public void testCheckOverUpperLimit6() {
		val childCare = remNum(-0.5, -1); // 残数（日数、時間）
		val remNum = childCare.checkOverUpperLimit();

		assertThat(remNum).isFalse(); //使い過ぎ
	}

	// 子の看護介護残数
	private ChildCareNurseRemainingNumber remNum(double usedDays, int usedTime) {
		return ChildCareNurseRemainingNumber.of(
				new DayNumberOfRemain(usedDays),
				Optional.of(new TimeOfRemain(usedTime)));
	}
}

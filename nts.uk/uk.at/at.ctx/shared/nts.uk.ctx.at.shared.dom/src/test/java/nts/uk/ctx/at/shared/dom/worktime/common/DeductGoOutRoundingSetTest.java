package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;

public class DeductGoOutRoundingSetTest {

	@Test
	public void getters() {
		DeductGoOutRoundingSet target = GoOutSetHelper.createDeductGoOutRoundingSet(TimeRoundingSetting.oneMinDown(), TimeRoundingSetting.oneMinDown());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getRoundingSetTest_deduct() {
		DeductGoOutRoundingSet target = GoOutSetHelper.createDeductGoOutRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //控除：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //計上：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(DeductionAtr.Deduction, TimeRoundingSetting.oneMinDown()); //控除
		
		//result == 5分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getRoundingSetTest_appro() {
		DeductGoOutRoundingSet target = GoOutSetHelper.createDeductGoOutRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //控除：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //計上：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown()); //計上
		//result == 15分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_15MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
}

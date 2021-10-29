package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class GoOutTypeRoundingSetTest {

	@Test
	public void getters() {
		GoOutTypeRoundingSet target = GoOutSetHelper.createGoOutTypeRoundingSet(TimeRoundingSetting.oneMinDown(), TimeRoundingSetting.oneMinDown());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getRoundingSetTest_private() {
		GoOutTypeRoundingSet target = GoOutSetHelper.createGoOutTypeRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //公用有償：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //私用組合：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown()); //私用
		
		//result == 15分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_15MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
	
	@Test
	public void getRoundingSetTest_public() {
		GoOutTypeRoundingSet target = GoOutSetHelper.createGoOutTypeRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //公用有償：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //私用組合：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(GoingOutReason.PUBLIC, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown()); //公用
		
		//result == 5分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getRoundingSetTest_compensation() {
		GoOutTypeRoundingSet target = GoOutSetHelper.createGoOutTypeRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //公用有償：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //私用組合：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(GoingOutReason.COMPENSATION, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown()); //有償
		
		//result == 5分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
	
	@Test
	public void getRoundingSetTest_union() {
		GoOutTypeRoundingSet target = GoOutSetHelper.createGoOutTypeRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //公用有償：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //私用組合：15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(GoingOutReason.UNION, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown()); //組合
		
		//result == 15分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_15MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
}

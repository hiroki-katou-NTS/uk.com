package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class StampBreakCalculationTest {

	@Test
	public void getters() {
		StampBreakCalculation target = new StampBreakCalculation(true, true);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void isUseAsRest_privateTrue() {
		StampBreakCalculation target = new StampBreakCalculation(true, false);
		assertThat(target.isUseAsRest(GoingOutReason.PRIVATE)).isTrue();
	}
	
	@Test
	public void isUseAsRest_privateFalse() {
		StampBreakCalculation target = new StampBreakCalculation(false, true);
		assertThat(target.isUseAsRest(GoingOutReason.PRIVATE)).isFalse();
	}

	@Test
	public void isUseAsRest_unionTrue() {
		StampBreakCalculation target = new StampBreakCalculation(false, true);
		assertThat(target.isUseAsRest(GoingOutReason.UNION)).isTrue();
	}
	
	@Test
	public void isUseAsRest_unionFalse() {
		StampBreakCalculation target = new StampBreakCalculation(true, false);
		assertThat(target.isUseAsRest(GoingOutReason.UNION)).isFalse();
	}
	
	@Test
	public void isUseAsRest_compensation() {
		StampBreakCalculation target = new StampBreakCalculation(true, true);
		assertThat(target.isUseAsRest(GoingOutReason.COMPENSATION)).isFalse();
	}
}

package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class BentoDetailsAmountTotalTest {

	@Test
	public void calculate() {
		int frameNo = 1;
		int quantity = 5;
		int amount1 = 20;
		int amount2 = 10;
		
		BentoDetailsAmountTotal target = BentoDetailsAmountTotal.calculate(frameNo, quantity, amount1, amount2);
		
		assertThat(target.getAmount1()).isEqualTo(5 * 20);
		assertThat(target.getAmount2()).isEqualTo(5 * 10);
	}
	
	@Test
	public void getters() {
		BentoDetailsAmountTotal target = BentoDetailsAmountTotal.calculate(1, 1, 1, 1);
		NtsAssert.invokeGetters(target);
	}

}

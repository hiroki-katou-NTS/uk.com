package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BentoDetailsAmountTotalTest {

	@Test
	public void calculate() {
		int frameNo = 1;
		int quantity = 5;
		int amount1 = 20;
		int amount2 = 10;
		
		BentoDetailsAmountTotal total = BentoDetailsAmountTotal.calculate(frameNo, quantity, amount1, amount2);
		
		assertThat(total.getAmount1()).isEqualTo(5 * 20);
		assertThat(total.getAmount2()).isEqualTo(5 * 10);
	} 

}

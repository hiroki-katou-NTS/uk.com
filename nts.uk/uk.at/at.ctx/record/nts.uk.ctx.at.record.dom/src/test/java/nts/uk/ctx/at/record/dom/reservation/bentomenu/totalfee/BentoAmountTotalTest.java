package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper;

public class BentoAmountTotalTest {

	@Test
	public void createNew() {
		List<BentoDetailsAmountTotal> lst = BentoInstanceHelper.getDetailsAmountTotalLst(
				new BentoDetailsAmountTotal(1, 40, 10),
				new BentoDetailsAmountTotal(2, 30, 5)
		);
		BentoAmountTotal bentoAmountTotal = BentoAmountTotal.createNew(lst);
		
	 	assertThat(bentoAmountTotal.getTotalAmount1()).isEqualTo(40 + 30);
	 	assertThat(bentoAmountTotal.getTotalAmount2()).isEqualTo(10 + 5);
	}

}

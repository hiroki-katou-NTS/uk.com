package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.bento;
import static nts.uk.ctx.at.record.dom.reservation.BentoInstanceHelper.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public class BentoAmountTotalTest {

	@Test
	public void createNew() {
		BentoAmountTotal bentoAmountTotal = BentoAmountTotal.createNew(
			Arrays.asList(
				new BentoDetailsAmountTotal(1, 40, 10),
				new BentoDetailsAmountTotal(2, 30, 5)
		));
		
	 	assertThat(bentoAmountTotal.getTotalAmount1()).isEqualTo(40 + 30);
	 	assertThat(bentoAmountTotal.getTotalAmount2()).isEqualTo(10 + 5);
	}
	
	@Test
	public void calculateTotalAmount() {
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);
		
	 	BentoMenu bentoMenu = menu(
	 			bento(1, 20, 10),
	 			bento(2, 100, 30));
	 	
	 	assertAmounts(
	 			bentoMenu.calculateTotalAmount(bentoDetails),
	 			5 * 20 + 3 * 100,
	 			5 * 10 + 3 * 30);
	}
	
	@Test
	public void calculateTotalAmount_invalidFrame() {
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);
		
	 	BentoMenu bentoMenu = menu(bento(1, 20, 10));
	 	
	 	assertThatThrownBy(() -> 
			bentoMenu.calculateTotalAmount(bentoDetails)
		).as("fail at day").isInstanceOf(RuntimeException.class);
	}

	private static void assertAmounts(BentoAmountTotal amount, int expectAmount1, int expectAmount2) {
		assertThat(amount.getTotalAmount1()).isEqualTo(expectAmount1);
		assertThat(amount.getTotalAmount2()).isEqualTo(expectAmount2);
	}

}

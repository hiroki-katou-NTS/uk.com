package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

public class BentoAmountTotalTest {

	@Test
	public void createNew() {
		
		BentoAmountTotal target = BentoAmountTotal.createNew(
			Arrays.asList(
				new BentoDetailsAmountTotal(1, 40, 10), // frameNo, amount1, amount2
				new BentoDetailsAmountTotal(2, 30, 5)
		));
		
	 	assertThat(target.getTotalAmount1()).isEqualTo(40 + 30);
	 	assertThat(target.getTotalAmount2()).isEqualTo(10 + 5);
	}
	
	@Test
	public void calculateTotalAmount() {

	 	BentoMenu bentoMenu = new BentoMenu(
	 			"historyID",
	 			Arrays.asList(
	 					Helper.Menu.Item.bentoAmount(1, 20, 10),  // frameNo, amount1, amount2
	 					Helper.Menu.Item.bentoAmount(2, 100, 30)),
	 			Helper.ClosingTime.UNLIMITED);
	 	
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);  // frameNo, quantity
		bentoDetails.put(2, 3);
	 	
	 	BentoAmountTotal target = bentoMenu.calculateTotalAmount(bentoDetails);
		assertThat(target.getTotalAmount1()).isEqualTo(5 * 20 + 3 * 100);
		assertThat(target.getTotalAmount2()).isEqualTo(5 * 10 + 3 * 30);
	}
	
	@Test
	public void calculateTotalAmount_invalidFrame() {

		// only frame 1
	 	BentoMenu bentoMenu = new BentoMenu(
	 			"historyID",
	 			Arrays.asList(Helper.Menu.Item.bentoAmount(1, 20, 10)),
	 			Helper.ClosingTime.UNLIMITED);
	 	
		Map<Integer, Integer> bentoDetails = new HashMap<>();
		bentoDetails.put(1, 5);
		bentoDetails.put(2, 3);  // frame 2 is invalid
	 	
		NtsAssert.systemError(() -> {
	 		
			bentoMenu.calculateTotalAmount(bentoDetails);
			
	 	});
	}

	@Test
	public void getters() {
		BentoAmountTotal target = BentoAmountTotal.createNew(
				Arrays.asList(new BentoDetailsAmountTotal(1, 40, 10)));
		
		NtsAssert.invokeGetters(target);
	}
}

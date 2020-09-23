package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletCombinationsHelper.CombinationHelper;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

/**
 * 
 * @author sonnh1
 *
 */
public class CombinationsTest {

	@Test
	public void create_combinations_order0_fail() {
		NtsAssert.businessException("Msg_1626", () -> {
			new Combinations(
					0, 
					new ShiftMasterCode("0000001")); // dummy
		});
	}
	 
	@Test
	public void create_combinations_order32_fail() {
		NtsAssert.businessException("Msg_1626", () -> {
			new Combinations(
					32, 
					new ShiftMasterCode("0000001")); // dummy
		});
	}
 
	@Test
	public void create_combinations_order1_success() {
		Combinations target = new Combinations(
				1, 
				new ShiftMasterCode("0000001"));
		
		assertThat(target)
			.extracting(d -> d.getOrder(), d-> d.getShiftCode().v())
			.containsExactly(1, "0000001");
	}
	
	@Test
	public void create_combinations_order31_success() {
		Combinations target = new Combinations(
				31, 
				new ShiftMasterCode("0000001"));
		
		assertThat(target)
			.extracting(d -> d.getOrder(), d-> d.getShiftCode().v())
			.containsExactly(31, "0000001");
	}

	@Test
	public void getters() {
		Combinations target = CombinationHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}

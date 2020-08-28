package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class StampButtonTest {

	@Test
	public void getters() {
	
		StampButton button = StampButtonHelper.getDefult();
		NtsAssert.invokeGetters(button);
	}

}

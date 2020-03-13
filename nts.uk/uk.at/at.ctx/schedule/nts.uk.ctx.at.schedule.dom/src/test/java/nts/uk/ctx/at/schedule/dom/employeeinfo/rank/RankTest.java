package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author sonnh1
 *
 */
public class RankTest {

	@Test
	public void getter() {
		Rank target = new Rank(
				"000000000000-0001", //dummy
				new RankCode("01"), //dummy
				new RankSymbol("abc")); //dummy
		NtsAssert.invokeGetters(target);
	}

}

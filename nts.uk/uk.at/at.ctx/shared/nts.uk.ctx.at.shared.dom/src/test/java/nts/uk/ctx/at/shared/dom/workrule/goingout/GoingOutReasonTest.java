package nts.uk.ctx.at.shared.dom.workrule.goingout;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
/**
 * 
 * @author tutk
 *
 */
public class GoingOutReasonTest {

	@Test
	public void getters() {
		GoingOutReason goingOutReason = GoingOutReason.valueOf(1);//dummy
		NtsAssert.invokeGetters(goingOutReason);
	}
	
	@Test
	public void test() {
		GoingOutReason goingOutReason = GoingOutReason.valueOf(1);//dummy
		assertThat(goingOutReason).isEqualTo(GoingOutReason.PUBLIC);
		goingOutReason = GoingOutReason.valueOf(10);
		assertThat(goingOutReason).isEqualTo(null);
	}

}

package nts.uk.ctx.sys.gateway.dom.outage;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;

public class PlannedOutageByTenantTest {

	@Test
	public void getter() {
		NtsAssert.invokeGetters(new PlannedOutageByTenant(null, null));
	}

	@Test
	public void setter(@Mocked PlannedOutageState state) {
		new PlannedOutageByTenant(null, null).setState(state);
	}

	@Test
	public void setter_null() {
		NtsAssert.systemError(
				ex -> ex.getClass() == NullPointerException.class,
				() -> {
					new PlannedOutageByTenant(null, null).setState(null);
				});
	}

}

package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
public class RelieveTest {

	@Test
	public void getters() {
		Relieve data = StampHelper.getRelieveDefault();
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void testRelieve() {
		AuthcMethod authcMethod = AuthcMethod.valueOf(1);//dummy
		StampMeans stampMeans = StampMeans.valueOf(0);//dummy
		Relieve data = new Relieve(authcMethod, stampMeans);
		NtsAssert.invokeGetters(data);
	}

}

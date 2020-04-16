package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class AuthcMethodTest {

	@Test
	public void getters() {
		AuthcMethod authcMethod = AuthcMethod.valueOf(1);
		NtsAssert.invokeGetters(authcMethod);
	}
	
	@Test
	public void test() {
		AuthcMethod authcMethod = AuthcMethod.valueOf(1);
		assertThat(authcMethod).isEqualTo(AuthcMethod.IC_CARD_AUTHC);
		authcMethod = AuthcMethod.valueOf(10);
		assertThat(authcMethod).isEqualTo(null);
	}

}

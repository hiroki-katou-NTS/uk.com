package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * @author nws_namnv2
 *
 */
public class SupportCardEditTest {
	
	@Test
	public void getters() {
		SupportCardEdit supportCardEdit = SupportCardEditHelper.getDataDefault();
		NtsAssert.invokeGetters(supportCardEdit);
	}

}

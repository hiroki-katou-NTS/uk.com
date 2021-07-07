package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;


@RunWith(JMockit.class)
public class CreateStampInfoTest {

	@Test
	public void getters() {
		CreateStampInfo createStampInfo = CreateStampInfoHelper.getCreateStampInfoDefault();
		NtsAssert.invokeGetters(createStampInfo);
	}

}

package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.ArrayList;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class CommonSettingsStampInputTest {

	@Test
	public void getters() {
	
		CommonSettingsStampInput commonSettingsStampInput = new CommonSettingsStampInput("companyId",
				new ArrayList<String>(),
				true, new MapAddress("Tokyo"));
		
		NtsAssert.invokeGetters(commonSettingsStampInput);
	
	}

}

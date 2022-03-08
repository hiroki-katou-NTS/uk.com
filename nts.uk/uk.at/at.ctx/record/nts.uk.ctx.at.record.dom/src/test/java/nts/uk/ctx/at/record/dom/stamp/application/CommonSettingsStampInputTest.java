package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class CommonSettingsStampInputTest {

	@Test
	public void getters() {
	
		CommonSettingsStampInput commonSettingsStampInput = new CommonSettingsStampInput("companyId",
				true, Optional.of(new MapAddress("Tokyo")),null);
		
		NtsAssert.invokeGetters(commonSettingsStampInput);
	
	}

}

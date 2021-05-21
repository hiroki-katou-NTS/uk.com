package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class CommonSettingsStampInputTest {

	@Test
	public void getters() {
	
		CommonSettingsStampInput commonSettingsStampInput = new CommonSettingsStampInput("companyId",
				true, Optional.of(new MapAddress("Tokyo")), NotUseAtr.USE);
		
		NtsAssert.invokeGetters(commonSettingsStampInput);
	
	}

}

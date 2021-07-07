package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 
 * @author chungnt
 *
 */

public class SettingDateTimeColorOfStampScreenTest {

	@Test
	public void getters() {
		SettingDateTimeColorOfStampScreen settingDateTimeColorOfStampScreen = new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY"));
		
		NtsAssert.invokeGetters(settingDateTimeColorOfStampScreen);
	}

}

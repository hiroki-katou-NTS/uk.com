package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 
 * @author chungnt
 *
 */

public class DisplaySettingsStampScreenTest {

	@Test
	public void test() {
		DisplaySettingsStampScreen displaySettingsStampScreen = new DisplaySettingsStampScreen(new CorrectionInterval(1),
				new SettingDateTimeColorOfStampScreen(new ColorCode("DUMMY")),
				new ResultDisplayTime(1));
		
		NtsAssert.invokeGetters(displaySettingsStampScreen);
	}

}

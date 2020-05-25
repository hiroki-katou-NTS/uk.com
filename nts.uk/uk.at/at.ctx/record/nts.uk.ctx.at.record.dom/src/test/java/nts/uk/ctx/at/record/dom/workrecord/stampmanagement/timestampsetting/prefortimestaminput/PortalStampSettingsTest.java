package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class PortalStampSettingsTest {

	@Test
	public void getter() {
		PortalStampSettings stampSettings = PortalStampSettingsHelper.getDefault();
		NtsAssert.invokeGetters(stampSettings);
	}
	
	@Test
	public void testDetailButtonSettings() {
		PortalStampSettings stampSettings = PortalStampSettingsHelper.getDefault();
		
		Optional<ButtonSettings> optButtonSettings = stampSettings.getDetailButtonSettings(new ButtonPositionNo(1));
		
		assertThat(optButtonSettings.get().getUsrArt().value).isEqualTo(1);
	}

}

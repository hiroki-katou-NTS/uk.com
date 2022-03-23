package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LegalOTSettingTest {

	@Test
	public void isOutsideLegalTest_true() {
		LegalOTSetting target = LegalOTSetting.OUTSIDE_LEGAL_TIME;
		assertThat(target.isOutsideLegal()).isEqualTo(true);
	}

	@Test
	public void isOutsideLegalTest_false() {
		LegalOTSetting target = LegalOTSetting.LEGAL_INTERNAL_TIME;
		assertThat(target.isOutsideLegal()).isEqualTo(false);
	}
}

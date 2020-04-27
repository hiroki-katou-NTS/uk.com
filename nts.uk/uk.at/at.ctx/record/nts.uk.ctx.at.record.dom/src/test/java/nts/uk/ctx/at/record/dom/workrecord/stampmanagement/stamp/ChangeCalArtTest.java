package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
/**
 * 
 * @author tutk
 *
 */
public class ChangeCalArtTest {
	@Test
	public void getters() {
		ChangeCalArt changeCalArt = ChangeCalArt.valueOf(1);//dummy
		NtsAssert.invokeGetters(changeCalArt);
	}
	@Test
	public void test() {
		ChangeCalArt changeCalArt = ChangeCalArt.valueOf(1);//dummy
		assertThat(changeCalArt).isEqualTo(ChangeCalArt.EARLY_APPEARANCE);
		changeCalArt = ChangeCalArt.valueOf(20);
		assertThat(changeCalArt).isEqualTo(null);
	}

}

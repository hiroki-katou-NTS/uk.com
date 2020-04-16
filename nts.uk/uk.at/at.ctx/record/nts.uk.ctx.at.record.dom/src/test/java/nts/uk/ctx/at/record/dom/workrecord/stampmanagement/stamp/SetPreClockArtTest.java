package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.SetPreClockArt;
/**
 * 
 * @author tutk
 *
 */
public class SetPreClockArtTest {
	@Test
	public void getters() {
		SetPreClockArt setPreClockArt = SetPreClockArt.valueOf(1);//dummy
		NtsAssert.invokeGetters(setPreClockArt);
	}
	@Test
	public void test() {
		SetPreClockArt setPreClockArt = SetPreClockArt.valueOf(1);//dummy
		assertThat(setPreClockArt).isEqualTo(SetPreClockArt.DIRECT);
		setPreClockArt = SetPreClockArt.valueOf(10);
		assertThat(setPreClockArt).isEqualTo(null);
	}
}

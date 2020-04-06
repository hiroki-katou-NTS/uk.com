package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeClockArt;
/**
 * 
 * @author tutk
 *
 */
public class ChangeClockArtTest {
	@Test
	public void getters() {
		ChangeClockArt changeClockArt = ChangeClockArt.valueOf(1);//dummy
		NtsAssert.invokeGetters(changeClockArt);
	}
	@Test
	public void test() {
		ChangeClockArt changeClockArt = ChangeClockArt.valueOf(1);//dummy
		assertThat(changeClockArt).isEqualTo(ChangeClockArt.WORKING_OUT);
		changeClockArt = ChangeClockArt.valueOf(20);
		assertThat(changeClockArt).isEqualTo(null);
	}
	
	/**
	 * changeClockArt == ChangeClockArt.WORKING_OUT
	 */
	@Test
	public void testCheckWorkingOut_1() {
		ChangeClockArt changeClockArt = ChangeClockArt.valueOf(1);//dummy
		assertThat(changeClockArt.checkWorkingOut()).isTrue();
	}
	
	/**
	 * changeClockArt == ChangeClockArt.WORKING_OUT
	 */
	@Test
	public void testCheckWorkingOut_2() {
		ChangeClockArt changeClockArt = ChangeClockArt.valueOf(5);//dummy
		assertThat(changeClockArt.checkWorkingOut()).isFalse();
	}

}

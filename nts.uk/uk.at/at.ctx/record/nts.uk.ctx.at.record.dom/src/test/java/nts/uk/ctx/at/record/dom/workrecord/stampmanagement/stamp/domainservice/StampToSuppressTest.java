package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
public class StampToSuppressTest {

	@Test
	public void getters() {
		StampToSuppress stampToSuppress = DomainServiceHeplper.getStampToSuppressDefault();
		NtsAssert.invokeGetters(stampToSuppress);
	}
	@Test
	public void testStampToSuppress_C0() {
		boolean goingToWork = false;
		boolean departure = false;
		boolean goOut = false;
		boolean turnBack = false;
		StampToSuppress stampToSuppress = new StampToSuppress(goingToWork, departure, goOut, turnBack);
		NtsAssert.invokeGetters(stampToSuppress);
	}
	@Test
	public void testStampToSuppress_C1() {
		StampToSuppress stampToSuppress = StampToSuppress.allStampFalse();
		assertThat(stampToSuppress.isGoingToWork()).isFalse();
		assertThat(stampToSuppress.isDeparture()).isFalse();
		assertThat(stampToSuppress.isGoOut()).isFalse();
		assertThat(stampToSuppress.isTurnBack()).isFalse();
	}
	@Test
	public void testStampToSuppress_C2() {
		StampToSuppress stampToSuppress = StampToSuppress.highlightAttendance();
		assertThat(stampToSuppress.isGoingToWork()).isFalse();
		assertThat(stampToSuppress.isDeparture()).isTrue();
		assertThat(stampToSuppress.isGoOut()).isTrue();
		assertThat(stampToSuppress.isTurnBack()).isFalse();
	}

}

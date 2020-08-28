package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
public class StampMeansTest {
	@Test
	public void getters() {
		StampMeans stampMeans = StampMeans.valueOf(0);
		
		NtsAssert.invokeGetters(stampMeans);
	}
	
	@Test
	public void test() {
		StampMeans stampMeans = StampMeans.valueOf(0);
		assertThat(stampMeans).isEqualTo(StampMeans.NAME_SELECTION);
		
		stampMeans = StampMeans.valueOf(10);
		assertThat(stampMeans).isEqualTo(null);
	}
	
	@Test
	public void testCheckIndivition() {
		StampMeans stampMeans = StampMeans.valueOf(3);
		assertThat(stampMeans.checkIndivition()).isTrue();
		
		stampMeans = StampMeans.valueOf(0);
		assertThat(stampMeans.checkIndivition()).isFalse();
		
		stampMeans = StampMeans.valueOf(1);
		assertThat(stampMeans.checkIndivition()).isFalse();
		
		stampMeans = StampMeans.valueOf(2);
		assertThat(stampMeans.checkIndivition()).isFalse();
		
		stampMeans = StampMeans.valueOf(4);
		assertThat(stampMeans.checkIndivition()).isTrue();
		
		stampMeans = StampMeans.valueOf(5);
		assertThat(stampMeans.checkIndivition()).isTrue();
		
		stampMeans = StampMeans.valueOf(6);
		assertThat(stampMeans.checkIndivition()).isFalse();
		
		stampMeans = StampMeans.valueOf(7);
		assertThat(stampMeans.checkIndivition()).isFalse();
		
		stampMeans = StampMeans.valueOf(8);
		assertThat(stampMeans.checkIndivition()).isFalse();
	}
	
	@Test
	public void testcheckAutoCreateStamp() {
		StampMeans stampMeans = StampMeans.valueOf(0);
		assertThat(stampMeans.checkAutoCreateStamp()).isTrue();
		
		stampMeans = StampMeans.valueOf(1);
		assertThat(stampMeans.checkAutoCreateStamp()).isTrue();
		
		stampMeans = StampMeans.valueOf(2);
		assertThat(stampMeans.checkAutoCreateStamp()).isFalse();
		
		stampMeans = StampMeans.valueOf(3);
		assertThat(stampMeans.checkAutoCreateStamp()).isTrue();
		
		stampMeans = StampMeans.valueOf(4);
		assertThat(stampMeans.checkAutoCreateStamp()).isTrue();
		
		stampMeans = StampMeans.valueOf(5);
		assertThat(stampMeans.checkAutoCreateStamp()).isTrue();
		
		stampMeans = StampMeans.valueOf(6);
		assertThat(stampMeans.checkAutoCreateStamp()).isFalse();
		
		stampMeans = StampMeans.valueOf(7);
		assertThat(stampMeans.checkAutoCreateStamp()).isFalse();
		
		stampMeans = StampMeans.valueOf(8);
		assertThat(stampMeans.checkAutoCreateStamp()).isFalse();
	}


}

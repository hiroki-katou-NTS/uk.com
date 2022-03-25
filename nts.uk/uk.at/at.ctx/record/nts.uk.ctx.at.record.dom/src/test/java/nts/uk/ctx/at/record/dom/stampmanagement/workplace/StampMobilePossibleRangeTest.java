package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.location.GeoCoordinate;

public class StampMobilePossibleRangeTest {
	@Test
	public void getters() {
		Optional<StampMobilePossibleRange> stampRange = WorkLocationHelper.getDefault().getStampRange();
		NtsAssert.invokeGetters(stampRange);
	}

	@Test
	public void testCheckWithinStampRange_1() {
		StampMobilePossibleRange stampRanger = new StampMobilePossibleRange(RadiusAtr.M_100, new GeoCoordinate(100, 200));
		new MockUp<GeoCoordinate>() {
			@Mock
			public double getDistanceAsMeter(GeoCoordinate geoCoordinate) {
				return 99;
			}
		};
		
		boolean check = stampRanger.checkWithinStampRange(new GeoCoordinate(50, 100));
		assertThat(check).isTrue();
	}
	
	@Test
	public void testCheckWithinStampRange_2() {
		StampMobilePossibleRange stampRanger = new StampMobilePossibleRange(RadiusAtr.M_100, new GeoCoordinate(100, 200));
		new MockUp<GeoCoordinate>() {
			@Mock
			public double getDistanceAsMeter(GeoCoordinate geoCoordinate) {
				return 100;
			}
		};
		
		boolean check = stampRanger.checkWithinStampRange(new GeoCoordinate(50, 100));
		assertThat(check).isTrue();
	}
	
	@Test
	public void testCheckWithinStampRange_3() {
		StampMobilePossibleRange stampRanger = new StampMobilePossibleRange(RadiusAtr.M_100, new GeoCoordinate(100, 200));
		new MockUp<GeoCoordinate>() {
			@Mock
			public double getDistanceAsMeter(GeoCoordinate geoCoordinate) {
				return 101;
			}
		};
		
		boolean check = stampRanger.checkWithinStampRange(new GeoCoordinate(50, 100));
		assertThat(check).isFalse();
	}

}

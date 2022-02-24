package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;

@RunWith(JMockit.class)
public class WorkLocationTest {

	@Injectable
	private WorkLocation.Require require;

	@Test
	public void getters() {

		WorkLocation workLocation = WorkLocationHelper.getDefault();
		NtsAssert.invokeGetters(workLocation);
	}

	@Test
	public void testCanStamptedByMobile_returnTrue() {
		WorkLocation workLocation = WorkLocationHelper.getDefault();
		new MockUp<WorkLocation>() {
			@Mock
			public boolean canStamptedByMobile(GeoCoordinate geoCoordinate) {
				return true;
			}
		};
		boolean check = workLocation.canStamptedByMobile(new GeoCoordinate(100, 200));
		assertThat(check).isTrue();
	}

	@Test
	public void testCanStamptedByMobile_returnFalse() {
		WorkLocation workLocation = WorkLocationHelper.getDefault();
		new MockUp<WorkLocation>() {
			@Mock
			public boolean canStamptedByMobile(GeoCoordinate geoCoordinate) {
				return false;
			}
		};
		boolean check = workLocation.canStamptedByMobile(new GeoCoordinate(100, 200));
		assertThat(check).isFalse();
	}

	// if (!this.regionCode.isPresent()) {
	// return 0;
	// }
	@Test
	public void testFindTimeDifference_1() {
		WorkLocation workLocation = WorkLocationHelper.getDefault();

		assertThat(workLocation.findTimeDifference(require, "ContractCode")).isEqualTo(0);
	}

	// !regionalTimeDifference.isPresent()
	@Test
	public void testFindTimeDifference_2() {
		WorkLocation workLocation = WorkLocationHelper.getHaveGegionCode();

		new Expectations() {
			{
				require.get("ContractCode", 10);
			}
		};

		assertThat(workLocation.findTimeDifference(require, "ContractCode")).isEqualTo(0);
	}

	// regionalTimeDifference.isPresent()
	@Test
	public void testFindTimeDifference_3() {
		WorkLocation workLocation = WorkLocationHelper.getHaveGegionCode();

		new Expectations() {
			{
				require.get("ContractCode", 12);
				result = Optional.of(new RegionalTimeDifference(12, "dummy", 10));
			}
		};

		assertThat(workLocation.findTimeDifference(require, "ContractCode")).isEqualTo(10);
	}
}

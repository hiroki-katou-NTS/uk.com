package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SortTypeTest {

	@Test
	public void getters() {
		SortType sortOrder = SortType.CLASSIFY;
		NtsAssert.invokeGetters(sortOrder);
	}

	@Test
	public void test() {
		SortType sortOrder = SortType.valueOf(0);
		assertThat(sortOrder).isEqualTo(SortType.SCHEDULE_TEAM);
		sortOrder = SortType.valueOf(1);
		assertThat(sortOrder).isEqualTo(SortType.RANK);
		sortOrder = SortType.valueOf(2);
		assertThat(sortOrder).isEqualTo(SortType.LISENCE_ATR);
		sortOrder = SortType.valueOf(3);
		assertThat(sortOrder).isEqualTo(SortType.POSITION);
		sortOrder = SortType.valueOf(4);
		assertThat(sortOrder).isEqualTo(SortType.CLASSIFY);
	}

}

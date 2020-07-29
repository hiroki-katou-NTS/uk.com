package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SortOrderTest {

	@Test
	public void getters() {
		SortOrder sortOrder = SortOrder.CLASSIFY;
		NtsAssert.invokeGetters(sortOrder);
	}

	@Test
	public void test() {
		SortOrder sortOrder = SortOrder.valueOf(0);
		assertThat(sortOrder).isEqualTo(SortOrder.SCHEDULE_TEAM);
		sortOrder = SortOrder.valueOf(1);
		assertThat(sortOrder).isEqualTo(SortOrder.RANK);
		sortOrder = SortOrder.valueOf(2);
		assertThat(sortOrder).isEqualTo(SortOrder.LISENCE_ATR);
		sortOrder = SortOrder.valueOf(3);
		assertThat(sortOrder).isEqualTo(SortOrder.POSITION);
		sortOrder = SortOrder.valueOf(4);
		assertThat(sortOrder).isEqualTo(SortOrder.CLASSIFY);
	}

}

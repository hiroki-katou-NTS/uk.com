package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SortOrderTest {

	@Test
	public void getters() {
		SortOrder type = SortOrder.SORT_ASC;
		NtsAssert.invokeGetters(type);
	}

	@Test
	public void test() {
		SortOrder type = SortOrder.valueOf(0);
		assertThat(type).isEqualTo(SortOrder.SORT_ASC);
		type = SortOrder.valueOf(1);
		assertThat(type).isEqualTo(SortOrder.SORT_DESC);
	}
}

package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class SortTypeTest {

	@Test
	public void getters() {
		SortType type = SortType.SORT_ASC;
		NtsAssert.invokeGetters(type);
	}

	@Test
	public void test() {
		SortType type = SortType.valueOf(0);
		assertThat(type).isEqualTo(SortType.SORT_ASC);
		type = SortType.valueOf(1);
		assertThat(type).isEqualTo(SortType.SORT_DESC);
	}
}

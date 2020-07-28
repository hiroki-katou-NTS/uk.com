package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class OrderedListTest {

	@Test
	public void getters() {
		OrderedList orderedList = new OrderedList(SortType.SORT_ASC, SortOrder.CLASSIFY);
		NtsAssert.invokeGetters(orderedList);
	}

}

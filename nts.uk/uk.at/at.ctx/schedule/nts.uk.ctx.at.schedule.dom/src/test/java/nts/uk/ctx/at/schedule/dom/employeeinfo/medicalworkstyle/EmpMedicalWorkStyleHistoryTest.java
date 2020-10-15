package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;


@RunWith(JMockit.class)
public class EmpMedicalWorkStyleHistoryTest {

	@Test
	public void getter() {
		EmpMedicalWorkStyleHistory result = new EmpMedicalWorkStyleHistory("empID-0001", // dummy
				EmpMedicalWorkHelper.getListDateHis());
		NtsAssert.invokeGetters(result);
	}

	@Test
	public void test_SystemError() {
		NtsAssert.systemError(() -> {
			EmpMedicalWorkStyleHistory.get("11", // dummy
					Collections.emptyList());});
	}

	@Test
	public void checkListDateHisItem() {
		List<DateHistoryItem> result = new ArrayList<DateHistoryItem>();
		
		DatePeriod date1 = new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(2020, 1, 2));		
		DatePeriod date2 = new DatePeriod(GeneralDate.ymd(2020, 1, 3), GeneralDate.ymd(9999, 12, 31));		

		DateHistoryItem item1 = new DateHistoryItem("historyId01",
				date1);
		DateHistoryItem item2 = new DateHistoryItem("historyId02",
				date2);
		result.add(item1);
		result.add(item2);
		EmpMedicalWorkStyleHistory history = EmpMedicalWorkStyleHistory.get("EMPLOYEEID01", // dummy
				result);
		assertThat(history.getEmpID().equals("EMPLOYEEID01")).isTrue();
		assertThat(history.getListDateHistoryItem()).extracting(x -> x.span(), x -> x.identifier())
				.containsExactly(tuple(date1, "historyId01"), tuple(date2,"historyId02"));
	}

	@Test
	public void test_ReturnListDateHistoryItem() {
		List<DateHistoryItem> result = new ArrayList<DateHistoryItem>();
		
		DatePeriod date1 = new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(2020, 1, 2));		
		DatePeriod date2 = new DatePeriod(GeneralDate.ymd(2020, 1, 3), GeneralDate.ymd(9999, 12, 31));		

		DateHistoryItem item1 = new DateHistoryItem("historyId01",
				date1);
		DateHistoryItem item2 = new DateHistoryItem("historyId02",
				date2);
		result.add(item1);
		result.add(item2);
		EmpMedicalWorkStyleHistory history = EmpMedicalWorkStyleHistory.get("EMPLOYEEID01", // dummy
				result);
		List<DateHistoryItem> actual = history.items();
		
		assertThat(actual.equals(result)).isTrue();
	}

}

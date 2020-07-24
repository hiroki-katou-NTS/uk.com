package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HieuLt
 *
 */
@RunWith(JMockit.class)
public class EmpMedicalWorkStyleHistoryTest {

	@Test
	public void getter() {
		EmpMedicalWorkStyleHistory result = new EmpMedicalWorkStyleHistory("empID-0001", // dummy
				EmpMedicalWorkHelper.getListDateHis());
		NtsAssert.invokeGetters(result);
	}

	@Test
	public void test_Buss() {
		NtsAssert.systemError(() -> {
			EmpMedicalWorkStyleHistory.get("11", // dummy
					Collections.emptyList());
		});
	}

	@Test
	public void checkListDateHisItem() {
		List<DateHistoryItem> result = new ArrayList<DateHistoryItem>();
		DateHistoryItem item1 = new DateHistoryItem("historyId01",
				new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(2020, 1, 2)));
		result.add(item1);
		EmpMedicalWorkStyleHistory history = EmpMedicalWorkStyleHistory.get("EMPLOYEEID01", // dummy
				result);
		assertThat(history.getEmpID().equals("EMPLOYEEID01")).isTrue();
		assertThat(history.getListDateHistoryItem()).extracting(x -> x.span(), x -> x.identifier())
				.containsExactly(tuple(new DatePeriod(history.getListDateHistoryItem().get(0).span().start(),
						history.getListDateHistoryItem().get(0).span().end()), "historyId01"));
	}

}

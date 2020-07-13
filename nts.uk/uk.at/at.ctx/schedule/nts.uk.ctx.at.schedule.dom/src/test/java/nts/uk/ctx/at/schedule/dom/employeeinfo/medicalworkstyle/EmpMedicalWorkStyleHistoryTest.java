package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HieuLt
 *
 */
public class EmpMedicalWorkStyleHistoryTest {


	
	
	
	public static List<DateHistoryItem> getListDateHis(){
		List<DateHistoryItem>  result = new ArrayList<DateHistoryItem>();
		DateHistoryItem item1 = new DateHistoryItem("historyId01", new DatePeriod(GeneralDate.ymd(2020, 1, 1) ,GeneralDate.ymd(2020, 1, 2))) ;
		DateHistoryItem item2 = new DateHistoryItem("historyId02", new DatePeriod(GeneralDate.ymd(2020, 1, 3) ,GeneralDate.ymd(2020, 1, 5))) ;
		DateHistoryItem item3 = new DateHistoryItem("historyId03", new DatePeriod(GeneralDate.ymd(2020, 1, 4) ,GeneralDate.ymd(2020, 1, 9))) ;
		result.add(item1);
		result.add(item2);
		result.add(item3);
		return result;
		
	}	
	
	@Test
	public void getter(){
		EmpMedicalWorkStyleHistory result = new EmpMedicalWorkStyleHistory("empID-0001", getListDateHis());
		NtsAssert.invokeGetters(result);
	}
	
	@Test
	public void checkListDateHisItemEmpty(){
		NtsAssert.businessException("Msg-XXX", () -> {
			new EmpMedicalWorkStyleHistory("EMPLOYEEID01",
					Collections.emptyList()	); 
		});
	}
	
}

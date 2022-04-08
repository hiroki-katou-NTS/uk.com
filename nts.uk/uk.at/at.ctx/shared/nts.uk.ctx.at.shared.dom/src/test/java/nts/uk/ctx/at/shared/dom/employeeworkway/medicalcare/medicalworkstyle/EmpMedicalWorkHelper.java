package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

public class EmpMedicalWorkHelper {
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
}

package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.ArrayList;
import java.util.List;

public class Helper {

	public static String companyId = "cid";
	public static String workplaceId = "wid";
	public static DatePeriod period = DatePeriod.daysFirstToLastIn(YearMonth.of(202009));

	public static Approver36AgrByCompany createApprover36AgrByCompany() {
		return new Approver36AgrByCompany(
				companyId,
				period,
				createApproverIds(5),
				createConfirmerIds(5)
		);
	}

	public static Approver36AgrByWorkplace createApprover36AgrByWorkplace() {
		return new Approver36AgrByWorkplace(
				companyId,
				workplaceId,
				period,
				createApproverIds(5),
				createConfirmerIds(5)
		);
	}

	public static List<String> createApproverIds(int size){
		return createStringListWithSize("approver", size);
	}

	public static List<String> createConfirmerIds(int size){
		return createStringListWithSize("confirmer", size);
	}

	private static List<String> createStringListWithSize(String sample, int size){
		val stringList = new ArrayList<String>();
		for (int i =0 ;i < size; i++){
			stringList.add(sample + i);
		}
		return stringList;
	}
}

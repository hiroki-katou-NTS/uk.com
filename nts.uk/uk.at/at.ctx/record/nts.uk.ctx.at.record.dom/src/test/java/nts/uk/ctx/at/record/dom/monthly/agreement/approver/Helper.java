package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khai.dh
 */
public class Helper {

	public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
	public static String cid = "cid";
	public static String workplaceId = "wid";
	public static DatePeriod period = DatePeriod.daysFirstToLastIn(YearMonth.of(202009));
	public static DatePeriod periodLast = DatePeriod.daysFirstToLastIn(YearMonth.of(202008));

	public static Approver36AgrByCompany createApprover36AgrByCompany() {
		return Approver36AgrByCompany.create(
				cid,
				period,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static Approver36AgrByCompany createApprover36AgrByCompanyLast() {
		return Approver36AgrByCompany.create(
				cid,
				periodLast,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static Approver36AgrByCompany createApprover36AgrByCompanyWithPeriod(String start, String end) {
		val periodFromParam = new DatePeriod(
				GeneralDate.fromString(start, DATE_FORMAT_YYYYMMDD),
				GeneralDate.fromString(end, DATE_FORMAT_YYYYMMDD)
		);
		return Approver36AgrByCompany.create(
				cid,
				periodFromParam,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static Approver36AgrByWorkplace createApprover36AgrByWorkplace() {
		return Approver36AgrByWorkplace.create(
				workplaceId,
				period,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static List<String> createApproverList(int size){
		return createStringListWithSize("approver", size);
	}

	public static List<String> createConfirmerList(int size){
		return createStringListWithSize("confirmer", size);
	}

	private static List<String> createStringListWithSize(String sample, int size){
		val stringList = new ArrayList<String>();
		for (int i =0 ;i < size; i++){
			stringList.add(sample + i);
		}
		return stringList;
	}

	public static GeneralDate createDate(String strDate){
		return GeneralDate.fromString(strDate, "yyyy/MM/dd");
	}
}

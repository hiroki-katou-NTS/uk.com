package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author khai.dh
 */
public class Helper {

	public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
	public static String cid = "cid";
	public static String workplaceId = "wid";
	public static DatePeriod period = DatePeriod.daysFirstToLastIn(YearMonth.of(202009));

	public static Approver36AgrByCompany createApprover36AgrByCompany() {
		return new Approver36AgrByCompany(
				cid,
				period,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static Approver36AgrByCompany createApprover36AgrByCompanyWithPeriod(String start, String end) {
		val periodFromParam = new DatePeriod(
				GeneralDate.fromString(start, DATE_FORMAT_YYYYMMDD),
				GeneralDate.fromString(end, DATE_FORMAT_YYYYMMDD)
		);
		return new Approver36AgrByCompany(
				cid,
				periodFromParam,
				createApproverList(5),
				createConfirmerList(5)
		);
	}

	public static Approver36AgrByWorkplace createApprover36AgrByWorkplace() {
		return new Approver36AgrByWorkplace(
				cid,
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
}

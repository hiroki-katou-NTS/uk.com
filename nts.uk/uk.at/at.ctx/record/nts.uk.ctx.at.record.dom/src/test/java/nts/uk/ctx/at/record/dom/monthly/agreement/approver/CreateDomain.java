package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
/**
 * Clone Hepper.
 */
public class CreateDomain {
    public static String cid = "cid";
    public static String workplaceId = "wid";
    public static DatePeriod period = DatePeriod.daysFirstToLastIn(YearMonth.of(202009));
    public static DatePeriod periodLast = DatePeriod.daysFirstToLastIn(YearMonth.of(202008));
    public static String employeeId = "eplId";
    public static String codeAndName = "codeName";
    public static Approver36AgrByWorkplace createApprover36AgrByWorkplace() {

        return Approver36AgrByWorkplace.create(
                workplaceId,
                period,
                createApproverList(5),
                createConfirmerList(5)
        );
    }
    public static Approver36AgrByWorkplace createApprover36AgrByWorkplaceLast() {

        return Approver36AgrByWorkplace.create(
                workplaceId,
                periodLast,
                createApproverList(5),
                createConfirmerList(5)
        );
    }
    public static List<String> createApproverList(int size) {
        return createStringListWithSize("approver", size);
    }

    public static List<String> createConfirmerList(int size) {
        return createStringListWithSize("confirmer", size);
    }

    public static List<String> createStringListWithSize(String sample, int size) {
        val stringList = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            stringList.add(sample + i);
        }
        return stringList;
    }
    public static SWkpHistRcImported createSWkpHistRcImported(){
        return new SWkpHistRcImported(period,employeeId,workplaceId,codeAndName,codeAndName,codeAndName);

    }
}

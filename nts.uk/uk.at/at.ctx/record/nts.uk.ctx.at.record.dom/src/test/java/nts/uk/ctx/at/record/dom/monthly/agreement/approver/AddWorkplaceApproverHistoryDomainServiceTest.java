package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class AddWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    AddWorkplaceApproverHistoryDomainService.Requeire requeire;
    private static String workplaceId = "wid";
    private   static List<String> approverList =  Arrays.asList("abc","cba","efg");
    private   static List<String> confirmerList =  Arrays.asList("abc","cba","efg");
    @Test
    public void test_01() {
        Approver36AgrByWorkplace itemToBeAdd = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today(),GeneralDate.max()),
                approverList,
                confirmerList

        );
        Approver36AgrByWorkplace itemUpdate = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today().addDays(-5),GeneralDate.max()),
                approverList,
                confirmerList

        );
        new Expectations() {{
            requeire.getLatestHistory(workplaceId, GeneralDate.max());
            result = Optional.of(itemUpdate);
        }};
        NtsAssert.atomTask(
                () -> AddWorkplaceApproverHistoryDomainService.addNewWorkplaceApproverHistory(requeire, itemToBeAdd),
                any -> requeire.addHistory(any.get()),
                any -> requeire.changeLatestHistory(any.get(),itemUpdate.getPeriod().start())
        );

    }
    @Test
    public void test_02() {

        Approver36AgrByWorkplace itemToBeAdd = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today(),GeneralDate.max()),
                approverList,
                confirmerList

        );
        new Expectations() {{
            requeire.getLatestHistory(workplaceId, GeneralDate.max());
            result = Optional.empty();
        }};

        NtsAssert.atomTask(
                () -> AddWorkplaceApproverHistoryDomainService.addNewWorkplaceApproverHistory(requeire, itemToBeAdd),
                any -> requeire.addHistory(any.get())
        );

    }

}

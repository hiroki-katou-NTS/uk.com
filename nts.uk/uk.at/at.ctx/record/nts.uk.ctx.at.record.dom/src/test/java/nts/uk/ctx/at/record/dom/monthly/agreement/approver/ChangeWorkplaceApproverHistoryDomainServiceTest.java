package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * @author chinh.hm
 */
@RunWith(JMockit.class)
public class ChangeWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    ChangeWorkplaceApproverHistoryDomainService.Require requeire;
    private static String workplaceId = "wid";
    private   static List<String> approverList =  Arrays.asList("abc","cba","efg");
    private   static List<String> confirmerList =  Arrays.asList("abc","cba","efg");
    @Test
    public void test_01(){
        GeneralDate referenceDate = GeneralDate.today();
        Approver36AgrByWorkplace historyItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(referenceDate,GeneralDate.max()),
                approverList,
                confirmerList

        );
        Approver36AgrByWorkplace preVHistoryItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(referenceDate.addDays(-5),referenceDate.addDays(-1)),
                approverList,
                confirmerList

        );

        new Expectations() {{
            requeire.getPrevHistory(workplaceId, referenceDate.addDays(-1) );
            result = Optional.of(preVHistoryItem);
        }};

        AtomTask persist = ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem );
        new Verifications() {{
            requeire.changeHistory((Approver36AgrByWorkplace)any,GeneralDate.today());
            times = 0;
        }};
        persist.run();
    }
    @Test
    public void test_02(){
        GeneralDate referenceDate = GeneralDate.today();
        Approver36AgrByWorkplace historyItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(referenceDate,GeneralDate.max()),
                approverList,
                confirmerList

        );
        new Expectations(){
            {
                requeire.getPrevHistory(workplaceId,referenceDate.addDays(-1));
                result = Optional.empty();
            }
        };

        NtsAssert.atomTask(()->
                        ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem),
                        any->requeire.changeHistory(historyItem,GeneralDate.today())

        );

    }
}

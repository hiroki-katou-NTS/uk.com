package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;
/**
 * @author chinh.hm
 */
@RunWith(JMockit.class)
public class ChangeWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    ChangeWorkplaceApproverHistoryDomainService.Require requeire;

    @Test
    public void test_01(){
        val referenceDate = GeneralDate.today();
        val historyItem = Approver36AgrByWorkplace.create(
                CreateDomain.workplaceId,
                new DatePeriod(referenceDate,GeneralDate.max()),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        val preVHistoryItem = Approver36AgrByWorkplace.create(
                CreateDomain.workplaceId,
                new DatePeriod(referenceDate.addDays(-5),referenceDate.addDays(-1)),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );

        new Expectations() {{
            requeire.getPrevHistory(CreateDomain.workplaceId, referenceDate.addDays(-1) );
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
        val referenceDate = GeneralDate.today();
        val historyItem = Approver36AgrByWorkplace.create(
                CreateDomain.workplaceId,
                new DatePeriod(referenceDate,GeneralDate.max()),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        new Expectations(){
            {
                requeire.getPrevHistory(CreateDomain.workplaceId,referenceDate.addDays(-1));
                result = Optional.empty();
            }
        };

        NtsAssert.atomTask(()->
                        ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem),
                        any->requeire.changeHistory(historyItem,GeneralDate.today())

        );

    }
}

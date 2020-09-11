package nts.uk.ctx.at.record.dom.workplaceapproverhistory;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class ChangeWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    ChangeWorkplaceApproverHistoryDomainService.Require requeire;

    @Test
    public void test_01(){
        val referenceDate = GeneralDate.today();
        val historyItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
                CreateDomain.workplaceId,
                new DatePeriod(referenceDate,GeneralDate.max()),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        val preVHistoryItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
                CreateDomain.workplaceId,
                new DatePeriod(referenceDate.addDays(-5),referenceDate.addDays(-1)),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );

        new Expectations() {{
            requeire.getPrevHistory(CreateDomain.workplaceId, referenceDate.addDays(-1) );
            result = Optional.of(preVHistoryItem);
        }};
        val service = new ChangeWorkplaceApproverHistoryDomainService();
        AtomTask persist = service.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem );
        new Verifications() {{
            requeire.changeHistory((Approver36AgrByWorkplace)any);
            times = 0;
        }};

        persist.run();
        AtomTask persistLast = service.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem );
        new Verifications() {{
            requeire.changeHistory((Approver36AgrByWorkplace)any);
            times = 2;
        }};

        persistLast.run();
    }
    @Test
    public void test_02(){
        val referenceDate = GeneralDate.today();
        val historyItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
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
        val service = new ChangeWorkplaceApproverHistoryDomainService();
        NtsAssert.atomTask(()->
                        service.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem),
                        any->requeire.changeHistory(historyItem)

        );

    }
}

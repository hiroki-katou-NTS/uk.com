package nts.uk.ctx.at.record.dom.workplaceapproverhistory;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class DeleteWorkplaceApproverHistoryDomainServiceTest {


    @Injectable
    DeleteWorkplaceApproverHistoryDomainService.Require require;

    @Test
    public void test_01(){
        val deleteItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
                CreateDomain.workplaceId,
                new DatePeriod(GeneralDate.today().addDays(-5),GeneralDate.today()),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        val preVHistoryItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
                CreateDomain.workplaceId,
                new DatePeriod(deleteItem.getPeriod().start().addDays(-5),deleteItem.getPeriod().start().addDays(-1)),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        new Expectations(){{
            require.getLastHistory(deleteItem.getWorkplaceId(),deleteItem.getPeriod().end().addDays(-1));
            result = Optional.of(preVHistoryItem);
        }};

        NtsAssert.atomTask(
                () -> DeleteWorkplaceApproverHistoryDomainService.changeHistory(require, deleteItem),
                any -> require.deleteHistory(any.get()),
                any -> require.changeLatestHistory(any.get())
        );
    }
    @Test
    public void test_02(){
        val deleteItem = new Approver36AgrByWorkplace(
                CreateDomain.cid,
                CreateDomain.workplaceId,
                new DatePeriod(GeneralDate.today().addDays(-5),GeneralDate.today()),
                CreateDomain.createApproverList(5),
                CreateDomain.createConfirmerList(5)

        );
        new Expectations(){{
            require.getLastHistory(deleteItem.getWorkplaceId(),deleteItem.getPeriod().end().addDays(-1));
            result = Optional.empty();
        }};
        NtsAssert.atomTask(
                () -> DeleteWorkplaceApproverHistoryDomainService.changeHistory(require, deleteItem),
                any -> require.deleteHistory(any.get())
        );
    }
}

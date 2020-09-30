package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class AddWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    AddWorkplaceApproverHistoryDomainService.Requeire requeire;

    @Test
    public void test_01() {

        val itemToBeAdd =  CreateDomain.createApprover36AgrByWorkplace();
        new Expectations() {{
            requeire.getLatestHistory(CreateDomain.workplaceId, GeneralDate.max());
            result = Optional.of(itemToBeAdd);
        }};
        NtsAssert.atomTask(
                () -> AddWorkplaceApproverHistoryDomainService.addNewWorkplaceApproverHistory(requeire, itemToBeAdd),
                any -> requeire.addHistory(any.get()),
                any -> requeire.changeLatestHistory(any.get())
        );

    }
    @Test
    public void test_02() {

        val itemToBeAdd =  CreateDomain.createApprover36AgrByWorkplace();
        new Expectations() {{
            requeire.getLatestHistory(CreateDomain.workplaceId, GeneralDate.max());
            result = Optional.empty();
        }};

        NtsAssert.atomTask(
                () -> AddWorkplaceApproverHistoryDomainService.addNewWorkplaceApproverHistory(requeire, itemToBeAdd),
                any -> requeire.addHistory(any.get())
        );

    }

}

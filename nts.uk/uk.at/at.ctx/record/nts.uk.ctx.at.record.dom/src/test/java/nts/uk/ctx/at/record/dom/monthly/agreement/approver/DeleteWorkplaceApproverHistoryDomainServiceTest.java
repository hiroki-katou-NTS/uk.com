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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JMockit.class)
public class DeleteWorkplaceApproverHistoryDomainServiceTest {


    @Injectable
    DeleteWorkplaceApproverHistoryDomainService.Require require;
    private static String workplaceId = "wid";
    private   static List<String> approverList =  Arrays.asList("abc","cba","efg");
    private   static List<String> confirmerList =  Arrays.asList("abc","cba","efg");
    @Test
    public void test_01(){
        val deleteItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today(),GeneralDate.max()),
                approverList,
                confirmerList

        );
        val preVHistoryItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(deleteItem.getPeriod().start().addDays(-5),deleteItem.getPeriod().start().addDays(-1)),
                approverList,
                confirmerList

        );
        new Expectations(){{
            require.getLastHistory(workplaceId,deleteItem.getPeriod().start().addDays(-1));
            result = Optional.of(preVHistoryItem);
        }};

        NtsAssert.atomTask(
                () -> DeleteWorkplaceApproverHistoryDomainService.changeHistory(require, deleteItem),
                any -> require.deleteHistory(any.get()),
                any -> require.changeLatestHistory(any.get(),preVHistoryItem.getPeriod().start())
        );
    }
    @Test
    public void test_02(){
        val deleteItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today(),GeneralDate.max()),
                approverList,
                confirmerList

        );
        new Expectations(){{
            require.getLastHistory(deleteItem.getWorkplaceId(),deleteItem.getPeriod().start().addDays(-1));
            result = Optional.empty();
        }};
        NtsAssert.atomTask(
                () -> DeleteWorkplaceApproverHistoryDomainService.changeHistory(require, deleteItem),
                any -> require.deleteHistory(any.get())
        );
    }
    @Test
    public void test_03(){
        val deleteItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(GeneralDate.today(),GeneralDate.max()),
                approverList,
                confirmerList

        );
        val preVHistoryItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(deleteItem.getPeriod().start().addDays(-5),deleteItem.getPeriod().start().addDays(-1)),
                approverList,
                confirmerList

        );
        new Expectations(){{
            require.getLastHistory(workplaceId,deleteItem.getPeriod().start().addDays(-1));
            result = Optional.of(preVHistoryItem);
        }};

        NtsAssert.atomTask(
                () -> DeleteWorkplaceApproverHistoryDomainService.changeHistory(require, deleteItem),
                any -> require.deleteHistory(any.get())
        );
        assertThat(preVHistoryItem.getPeriod().end()).isEqualTo(GeneralDate.max() );
    }
}

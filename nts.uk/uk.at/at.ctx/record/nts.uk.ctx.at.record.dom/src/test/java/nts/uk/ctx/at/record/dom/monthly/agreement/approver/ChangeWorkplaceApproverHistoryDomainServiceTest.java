package nts.uk.ctx.at.record.dom.monthly.agreement.approver;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chinh.hm
 */
@RunWith(JMockit.class)
public class ChangeWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    ChangeWorkplaceApproverHistoryDomainService.Require requeire;

    private static String workplaceId = "000000000000-0003";
    private static List<String> approverList =  Arrays.asList("7AB520B7-CF55-4068-8DEF-C4DF52C35C2E","7AD2CE3D-3AE8-4D90-B556-867596A830B7","7AB52E44-06B2-476D-B04C-0D35F712C00F");
    private static List<String> confirmerList =  Arrays.asList("7AB52E44-06B2-476D-B04C-0D35F712C00F","7AD2CE3D-3AE8-4D90-B556-867596A830B7","7AB52E44-06B2-476D-B04C-0D35F712C00F");

    @Test
    public void test_01(){
        GeneralDate referenceDate = GeneralDate.today();
        Approver36AgrByWorkplace historyItem = Approver36AgrByWorkplace.create(
                workplaceId,
                new DatePeriod(referenceDate.addDays(2),GeneralDate.max()),
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

        NtsAssert.atomTask(()->
                ChangeWorkplaceApproverHistoryDomainService.changeWorkplaceApproverHistory(requeire,referenceDate,historyItem),
                any->requeire.changeHistory(historyItem,referenceDate),
                any->requeire.changeHistory(preVHistoryItem,referenceDate.addDays(-5))
        );
        assertThat(preVHistoryItem.getPeriod().end()).isEqualTo(historyItem.getPeriod().start().addDays(-1));
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

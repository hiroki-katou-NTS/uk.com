package nts.uk.ctx.at.record.dom.workplaceapproverhistory;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JMockit.class)
public class GetWorkplaceApproverHistoryDomainServiceTest {
    @Injectable
    GetWorkplaceApproverHistoryDomainService.Require require;
    @Test
    public void test_01_R_Q_30_empty(){
        val wplHistoryofEmployees = CreateDomain.createSWkpHistRcImported();
        val employId = "eplId";
        val baseDate = GeneralDate.today();

        new Expectations() {{
            require.getYourWorkplace(CreateDomain.employeeId,baseDate);
            result = Optional.empty();

        }};
        val service = new GetWorkplaceApproverHistoryDomainService();
        assertThat(service.getWorkplaceApproverHistory(require,employId)).isEqualTo(Optional.empty());

    }
    public void test_01_R_Q_30_not_empty(){
        val wplHistoryofEmployees = CreateDomain.createSWkpHistRcImported();
        val employId = "eplId";
        val baseDate = GeneralDate.today();

        new Expectations() {{
            require.getYourWorkplace(CreateDomain.employeeId,baseDate);
            result = Optional.of(wplHistoryofEmployees);

        }};
        val service = new GetWorkplaceApproverHistoryDomainService();
        assertThat(service.getWorkplaceApproverHistory(require,employId)).isEqualTo(Optional.empty());

    }
}
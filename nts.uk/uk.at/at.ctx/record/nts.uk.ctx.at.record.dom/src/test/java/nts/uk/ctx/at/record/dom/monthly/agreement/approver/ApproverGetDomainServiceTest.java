package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ApproverGetDomainServiceTest {

	@Injectable
	private ApproverGetDomainService.Require require;

	@Injectable
	private ByWorkplaceApproverGetDomainService wkpApprService;

	@Injectable
	private ApproverGetDomainService.WkpApprRequire wkpApprRequire;

	@Test
	public void test01() {
		val empId = "empId";
		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
		new Expectations() {{
			require.getWorkplaceApprover(empId);
			result = Optional.of(approverItem);
		}};

		val service = new ApproverGetDomainService();
		assertThat(service.getApprover(require, empId).get()).isEqualTo(approverItem);
	}

	@Test
	public void test02() {
		val empId = "empId";
		val baseDate = GeneralDate.today();
		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
		new Expectations() {{
			require.getWorkplaceApprover(empId);
			result = Optional.empty();

			require.getApproverHistoryItem(baseDate);
			result = Optional.of(approverItem);
		}};

		val service = new ApproverGetDomainService();
		assertThat(service.getApprover(require, empId).get()).isEqualTo(approverItem);
	}

	@Test
	public void test03() {
		val empId = "empId";
		val baseDate = GeneralDate.today();
		new Expectations() {{
			require.getWorkplaceApprover(empId);
			result = Optional.empty();

			require.getApproverHistoryItem(baseDate);
			result = Optional.empty();
		}};

		val service = new ApproverGetDomainService();
		assertThat(service.getApprover(require, empId)).isEqualTo(Optional.empty());
	}
}

package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class GettingApproverDomainServiceTest {

	@Injectable
	private GettingApproverDomainService.Require require;

	@Test
	public void test01() {
		String empId = "dummyEmp";
		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(GetWorkplaceApproveHistoryDomainService.Require require,
															  String empId) {
				return Optional.of(approverItem);
			}
		};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result.get()).isEqualTo(approverItem);
	}

	@Test
	public void test02() {
		String empId = "dummyEmp";
		val approverItem = new ApproverItem(Helper.createApproverList(1), Helper.createConfirmerList(1));

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(GetWorkplaceApproveHistoryDomainService.Require require,
															  String empId) {
				return Optional.empty();
			}
		};

		val baseDate = GeneralDate.today();
		new Expectations(){{
			require.getApproverHistoryItem(baseDate);
			result = Optional.of(approverItem);
		}};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result.get()).isEqualTo(approverItem);
	}

	@Test
	public void test03() {
		String empId = "dummyEmp";
		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(GetWorkplaceApproveHistoryDomainService.Require require,
															  String empId) {
				return Optional.empty();
			}
		};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result).isNotPresent();
	}
}
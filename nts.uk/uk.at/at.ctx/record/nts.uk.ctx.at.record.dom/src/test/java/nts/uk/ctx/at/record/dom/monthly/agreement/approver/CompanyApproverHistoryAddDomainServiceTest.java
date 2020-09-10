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
public class CompanyApproverHistoryAddDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryAddDomainService.Require require;

	@Test
	public void test01() {
		val domain = Helper.createApprover36AgrByCompany();
		new Expectations() {{
			require.getLatestHistory(GeneralDate.max());
			result = Optional.of(domain);
		}};

		val service = new CompanyApproverHistoryAddDomainService();
		NtsAssert.atomTask(
				() -> service.addApproverHistory(require, domain),
				any -> require.addHistory(any.get()),
				any -> require.changeLatestHistory(any.get())
		);
	}

	@Test
	public void test02() {
		val domain = Helper.createApprover36AgrByCompany();
		new Expectations() {{
			require.getLatestHistory(GeneralDate.max());
			result = Optional.empty();
		}};

		val service = new CompanyApproverHistoryAddDomainService();
		NtsAssert.atomTask(
				() -> service.addApproverHistory(require, domain),
				any -> require.addHistory(any.get())
		);
	}
}

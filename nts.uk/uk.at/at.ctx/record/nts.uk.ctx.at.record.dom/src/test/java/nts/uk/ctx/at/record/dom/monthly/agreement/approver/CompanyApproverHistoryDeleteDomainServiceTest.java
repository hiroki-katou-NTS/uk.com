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
public class CompanyApproverHistoryDeleteDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryDeleteDomainService.Require require;

	@Test
	public void test01() {
		val domain = Helper.createApprover36AgrByCompany();
		new Expectations() {{
			require.getPrevHistory(domain.getPeriod().end().addDays(-1));

			result = Optional.of(domain);
		}};

		val service = new CompanyApproverHistoryDeleteDomainService();
		service.deleteApproverHistory(require, domain);
		NtsAssert.atomTask(
				() -> service.deleteApproverHistory(require, domain),
				any -> require.deleteHistory(any.get()),
				any -> require.changeHistory(any.get())
		);
	}

	@Test
	public void test02() {
		val domain = Helper.createApprover36AgrByCompany();
		val service = new CompanyApproverHistoryDeleteDomainService();

		new Expectations() {{
			require.getPrevHistory(domain.getPeriod().end().addDays(-1));
			result = Optional.empty();
		}};

		NtsAssert.atomTask(
				() -> service.deleteApproverHistory(require, domain),
				any -> require.deleteHistory(any.get())
		);
	}
}

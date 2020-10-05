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

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class CompanyApproverHistoryAddDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryAddDomainService.Require require;

	@Test
	public void test01() {
		val domain = Helper.createApprover36AgrByCompany();
		val update = Helper.createApprover36AgrByCompanyLast();
		new Expectations() {{
			require.getLatestHistory(GeneralDate.max());
			result = Optional.of(update);
		}};

		val service = new CompanyApproverHistoryAddDomainService();
		NtsAssert.atomTask(
				() -> service.addApproverHistory(require, domain),
				any -> require.addHistory(any.get()),
				any -> require.changeLatestHistory(any.get(),update.getPeriod().start())
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

package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
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
		val domain = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/11"), Helper.createDate("2020/09/15")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

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

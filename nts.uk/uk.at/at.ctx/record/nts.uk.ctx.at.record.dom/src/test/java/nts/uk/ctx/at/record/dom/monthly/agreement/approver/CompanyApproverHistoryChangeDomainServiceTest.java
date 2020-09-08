package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.AtomTaskAssert;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.function.Consumer;

import java.util.Optional;

@RunWith(JMockit.class)
public class CompanyApproverHistoryChangeDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryChangeDomainService.Require require;

	@Test
	public void test01() {
		val histToChange 	= Helper.createApprover36AgrByCompanyWithPeriod("2020/09/11", "2020/09/15");
		val prevHist 		= Helper.createApprover36AgrByCompanyWithPeriod("2020/09/01", "2020/09/05");
		val modPrevHist 	= Helper.createApprover36AgrByCompanyWithPeriod("2020/09/01", "2020/09/10");
		val startDateBeforeChange = GeneralDate.fromString("2020/09/06", Helper.DATE_FORMAT_YYYYMMDD);

		new Expectations() {{
			require.getPrevHistory(startDateBeforeChange.addDays(-1));
			result = Optional.of(prevHist);
		}};

 		val service = new CompanyApproverHistoryChangeDomainService();

		NtsAssert.atomTask(
				() -> service.changeApproverHistory(require, startDateBeforeChange, histToChange),
				any1 -> require.changeHistory(any1.get()),
				any2 -> require.changeHistory(any2.get())
		);
	}

	@Test
	public void test02() {
		val histToChange 	= Helper.createApprover36AgrByCompanyWithPeriod("2020/09/11", "2020/09/15");
		val startDateBeforeChange = GeneralDate.fromString("2020/09/06", Helper.DATE_FORMAT_YYYYMMDD);

		new Expectations() {{
			require.getPrevHistory(startDateBeforeChange.addDays(-1));
			result = Optional.empty();
		}};

		val service = new CompanyApproverHistoryChangeDomainService();
		NtsAssert.atomTask(
				() -> service.changeApproverHistory(require, startDateBeforeChange, histToChange),
				any -> require.changeHistory(histToChange)
		);
	}
}

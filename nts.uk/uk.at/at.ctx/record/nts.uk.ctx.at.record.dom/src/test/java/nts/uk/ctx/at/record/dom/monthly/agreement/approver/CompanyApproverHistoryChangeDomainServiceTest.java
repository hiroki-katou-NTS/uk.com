package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class CompanyApproverHistoryChangeDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryChangeDomainService.Require require;

	/**
	 * getPrevHistory OK
	 */
	@Test
	public void test01() {
		val histToChange = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/11"), Helper.createDate("2020/09/15")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		val prevHist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), Helper.createDate("2020/09/05")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		val startDateBeforeChange = Helper.createDate("2020/09/06");

		new Expectations() {{
			require.getPrevHistory(startDateBeforeChange.addDays(-1));
			result = Optional.of(prevHist);
		}};

		AtomTask atomTask = CompanyApproverHistoryChangeDomainService
				.changeApproverHistory(require, startDateBeforeChange, histToChange);

		NtsAssert.atomTask(
				() -> atomTask,
				any -> require.changeHistory(histToChange, startDateBeforeChange),
				any -> require.changeHistory(prevHist, Helper.createDate("2020/09/01"))
		);

		assertThat(prevHist.getPeriod().end()).isEqualTo(Helper.createDate("2020/09/10"));
	}

	/**
	 * getPrevHistory returns empty
	 */
	@Test
	public void test02() {
		val histToChange = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/11"), Helper.createDate("2020/09/15")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		val startDateBeforeChange = Helper.createDate("2020/09/06");

		new Expectations() {{
			require.getPrevHistory(startDateBeforeChange.addDays(-1));
			result = Optional.empty();
		}};

		AtomTask atomTask = CompanyApproverHistoryChangeDomainService
				.changeApproverHistory(require, startDateBeforeChange, histToChange);

		NtsAssert.atomTask(
				() -> atomTask,
				any -> require.changeHistory(histToChange, startDateBeforeChange)
		);
	}
}

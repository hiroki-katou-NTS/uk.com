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
public class CompanyApproverHistoryDeleteDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryDeleteDomainService.Require require;

	/**
	 * getPrevHistory OK
	 */
	@Test
	public void test01() {
		val hist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), GeneralDate.max()),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		val prevHist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/08/01"), Helper.createDate("2020/08/31")),
				Arrays.asList("approver02"),
				Arrays.asList("confirmer02")
		);

		new Expectations() {{
			require.getPrevHistory(hist.getPeriod().start().addDays(-1));

			result = Optional.of(prevHist);
		}};

		AtomTask atomTask = CompanyApproverHistoryDeleteDomainService.deleteApproverHistory(require, hist);
		NtsAssert.atomTask(
				()-> atomTask,
				any -> require.deleteHistory(hist),
				any -> require.changeHistory(prevHist, prevHist.getPeriod().start())
		);

		assertThat(prevHist.getPeriod().end()).isEqualTo(GeneralDate.max());
	}

	@Test
	/**
	 * getPrevHistory returns empty
	 */
	public void test02() {
		val hist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), GeneralDate.max()),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		new Expectations() {{
			require.getPrevHistory(hist.getPeriod().start().addDays(-1));

			result = Optional.empty();
		}};

		AtomTask atomTask = CompanyApproverHistoryDeleteDomainService.deleteApproverHistory(require, hist);
		NtsAssert.atomTask(
				()-> atomTask,
				any -> require.deleteHistory(hist)
		);
	}
}

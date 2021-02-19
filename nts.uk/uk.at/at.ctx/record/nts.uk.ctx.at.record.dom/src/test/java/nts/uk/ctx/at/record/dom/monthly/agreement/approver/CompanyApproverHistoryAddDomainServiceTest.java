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
public class CompanyApproverHistoryAddDomainServiceTest {

	@Injectable
	private CompanyApproverHistoryAddDomainService.Require require;

	/**
	 * R1 returns normal result
	 */
	@Test
	public void test01() {
		val histToAdd = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), Helper.createDate("2020/09/15")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		val latestHist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/08/01"), GeneralDate.max()),
				Arrays.asList("approver02"),
				Arrays.asList("confirmer02")
		);

		new Expectations() {{
			require.getLatestHistory(GeneralDate.max());
			result = Optional.of(latestHist);
		}};

		AtomTask atomTask = CompanyApproverHistoryAddDomainService.addApproverHistory(require, histToAdd);

		NtsAssert.atomTask(
				() -> atomTask,
				any -> require.addHistory(histToAdd),
				any -> require.changeLatestHistory(latestHist, latestHist.getPeriod().start())
		);

		assertThat(latestHist.getPeriod().end()).isEqualTo(Helper.createDate("2020/08/31"));
	}

	/**
	 * R1 returns empty result
	 */
	@Test
	public void test02() {
		val histToAdd = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/02"), Helper.createDate("2020/09/16")),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		new Expectations() {{
			require.getLatestHistory(GeneralDate.max());
			result = Optional.empty();
		}};

		AtomTask atomTask = CompanyApproverHistoryAddDomainService.addApproverHistory(require, histToAdd);

		NtsAssert.atomTask(
				() -> atomTask,
				any -> require.addHistory(histToAdd)
		);
	}
}

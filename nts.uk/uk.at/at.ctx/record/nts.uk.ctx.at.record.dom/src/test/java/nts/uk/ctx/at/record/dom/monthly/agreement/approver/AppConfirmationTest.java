package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class AppConfirmationTest {

	@Injectable
	private AppConfirmation.Require require;

	/**
	 * R1 returns empty
	 * <p>
	 * Expected: BusinessException with Msg_1262
	 */
	@Test
	public void test01() {
		String aplId = "dummyApplicantId";
		String cfmrId = "dummyConfirmerId";
		val cfSts = ConfirmationStatus.UNCONFIRMED;

		NtsAssert.businessException("Msg_1262", () -> AppConfirmation.change(require, aplId, cfmrId, cfSts));
	}

	/**
	 * R1 returns normal result
	 * <p>
	 * Expected: R2 should be called
	 */
	@Test
	public void test02() {
		String aplId = "dummyApplicantId";
		String cfmrId = "dummyConfirmerId";
		val cfSts = ConfirmationStatus.UNCONFIRMED;
		val dummyApp = createDummyApp();

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		NtsAssert.atomTask(
				() -> AppConfirmation.change(require, aplId, cfmrId, cfSts),
				any -> require.updateApp(any.get()) // R2
		);
	}

	private static SpecialProvisionsOfAgreement createDummyApp() {
		OneMonthTime oneMonthTime = new OneMonthTime(
				new OneMonthErrorAlarmTime(),
				new YearMonth(0)
		);

		OneYearTime oneYearTime = new OneYearTime(
				new OneYearErrorAlarmTime(),
				new Year(0)
		);

		ApplicationTime applicationTime = new ApplicationTime(
				TypeAgreementApplication.ONE_MONTH,
				Optional.of(oneMonthTime),
				Optional.of(oneYearTime));

		List<String> listConfirmSID = new ArrayList<String>(){{
			add("confirmerSID");
		}};

		return SpecialProvisionsOfAgreement.create(
				"enteredPersonSID",
				"dummyApplicantId",
				applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),
				new ArrayList<>()
				, listConfirmSID,
				new ScreenDisplayInfo());
	}
}

package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
		String cfmId = "dummyConfirmerId";
		val cfSts = ConfirmationStatus.UNCONFIRMED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.empty();
		}};

		NtsAssert.businessException("Msg_1262", () -> AppConfirmation.change(require, aplId, cfmId, cfSts));
	}

	/**
	 * R1 returns normal result
	 * <p>
	 * Expected: R2 should be called
	 */
	@Test
	public void test02() {
		String aplId = "dummyApplicantId";
		String cfId = "cfm02";
		val cfSts = ConfirmationStatus.CONFIRMED;
		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"dummyApplicantId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_MONTH,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				new ArrayList<>(),
				Arrays.asList("cfm01","cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val yesterday = GeneralDate.today().addDays(-1);
		dummyApp.getConfirmationStatusDetails().forEach(item -> {
			item.setConfirmDate(Optional.of(yesterday));
		});

		NtsAssert.atomTask(
				() -> AppConfirmation.change(require, aplId, cfId, cfSts),
				any -> require.updateApp(any.get()) // R2
		);

		assertThat(dummyApp.getConfirmationStatusDetails())
				.extracting(d -> d.getConfirmerSID(), d -> d.getConfirmationStatus(), d -> d.getConfirmDate())
				.containsExactly(
						tuple("cfm01", ConfirmationStatus.UNCONFIRMED, Optional.of(yesterday)),
						tuple("cfm02", ConfirmationStatus.CONFIRMED, Optional.of(GeneralDate.today())),
						tuple("cfm03", ConfirmationStatus.UNCONFIRMED, Optional.of(yesterday))
				);
	}
}

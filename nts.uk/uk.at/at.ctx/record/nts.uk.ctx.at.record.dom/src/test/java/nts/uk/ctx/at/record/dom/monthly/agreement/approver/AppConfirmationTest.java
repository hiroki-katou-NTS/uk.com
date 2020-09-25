package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.ErrorOneYear;
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
				new ErrorTimeInMonth(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)),
				new YearMonth(0)
		);

		OneYearTime oneYearTime = new OneYearTime(
				new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
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

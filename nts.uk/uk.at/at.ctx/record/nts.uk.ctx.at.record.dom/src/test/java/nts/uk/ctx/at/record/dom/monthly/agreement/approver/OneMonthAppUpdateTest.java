package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class OneMonthAppUpdateTest {

	@Injectable
	private OneMonthAppUpdate.Require require;

	/**
	 * R1 returns empty
	 * <p>
	 * Expected: BusinessException with Msg_1262
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneMonthTime oneMonthTime = new AgreementOneMonthTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");

		NtsAssert.businessException("Msg_1262", () -> OneMonthAppUpdate.update(require, cid, aplId, oneMonthTime, reason));
	}

	/**
	 * R1 returns normal result
	 * checkErrorTimeExceeded returns true
	 * <p>
	 * Expected: result with type ResultType MONTHLY_LIMIT_EXCEEDED should be returned.
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneMonthTime oneMonthTime = new AgreementOneMonthTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		val dummyApp = createDummyApp();

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val setting = new BasicAgreementSetting(new AgreementOneMonth(), null, null, null);
		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSetting getBasicSet(
					AgreementDomainService.RequireM3 require,
					String companyId,
					String employeeId,
					GeneralDate criteriaDate,
					WorkingSystem workingSystem) {

				return setting;
			}
		};

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(true, new AgreementOneMonthTime(0));

		new MockUp<AgreementOneMonth>() {
			@Mock
			public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {

				return errCheckResult;
			}
		};

		val actual = OneMonthAppUpdate.update(require, cid, aplId, oneMonthTime, reason);
		assertThat(actual.getResultType()).isEqualTo(ResultType.MONTHLY_LIMIT_EXCEEDED);
	}

	/**
	 * R1 returns normal result
	 * checkErrorTimeExceeded returns false
	 * <p>
	 * Expected:
	 * result with type ResultType NO_ERROR should be returned.
	 * R2 should be called
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneMonthTime oneMonthTime = new AgreementOneMonthTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		val dummyApp = createDummyApp();

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val setting = new BasicAgreementSetting(new AgreementOneMonth(), null, null, null);
		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSetting getBasicSet(
					AgreementDomainService.RequireM3 require,
					String companyId,
					String employeeId,
					GeneralDate criteriaDate,
					WorkingSystem workingSystem) {

				return setting;
			}
		};

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));

		new MockUp<AgreementOneMonth>() {
			@Mock
			public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {

				return errCheckResult;
			}
		};

		val actual = OneMonthAppUpdate.update(require, cid, aplId, oneMonthTime, reason);
		assertThat(actual.getResultType()).isEqualTo(ResultType.NO_ERROR);
		NtsAssert.atomTask(
				()-> actual.getAtomTask().get(),
				any -> require.updateApp(any.get())
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

		List<String> listConfirmSID = new ArrayList<String>() {{
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
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
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
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
public class AnnualAppUpdateTest {

	@Injectable
	private AnnualAppUpdate.Require require;

	/**
	 * R1 returns empty
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneYearTime oneYearTime = new AgreementOneYearTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");

		new Expectations() {{
			require.getApp(aplId);
			result = Optional.empty();
		}};

		NtsAssert.businessException("Msg_1262", () -> AnnualAppUpdate.update(require, cid, aplId, oneYearTime, reason));
	}

	/**
	 * R1 returns normal result
	 * checkErrorTimeExceeded returns true
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneYearTime oneYearTime = new AgreementOneYearTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		val dummyApp = createDummyApp();

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

    	val setting = new BasicAgreementSetting(null, new AgreementOneYear(), null, null);
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

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneYearTime>(true, new AgreementOneYearTime(0));
		new MockUp<AgreementOneYear>() {
			@Mock
			public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
				return errCheckResult;
			}
		};

		val actual = AnnualAppUpdate.update(require, cid, aplId, oneYearTime, reason);
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getAtomTask()).isNotPresent();
		assertThat(actual.getErrorInfo().get(0).getErrorClassification()).isEqualTo(ErrorClassification.OVERTIME_LIMIT_ONE_YEAR);
	}

	/**
	 * R1 returns normal result
	 * checkErrorTimeExceeded returns false
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		AgreementOneYearTime oneYearTime = new AgreementOneYearTime(0);
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		val dummyApp = createDummyApp();

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val setting = new BasicAgreementSetting(null, new AgreementOneYear(), null, null);
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

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneYearTime>(false, new AgreementOneYearTime(0));
		new MockUp<AgreementOneYear>() {
			@Mock
			public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
				return errCheckResult;
			}
		};

		val actual = AnnualAppUpdate.update(require, cid, aplId, oneYearTime, reason);

		assertThat(actual.getEmpId()).isEqualTo(aplId);
		NtsAssert.atomTask(
				() -> actual.getAtomTask().get(),
				any-> require.updateApp(any.get())
		);
		assertThat(actual.getErrorInfo()).isEmpty();
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
				TypeAgreementApplication.ONE_YEAR,
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

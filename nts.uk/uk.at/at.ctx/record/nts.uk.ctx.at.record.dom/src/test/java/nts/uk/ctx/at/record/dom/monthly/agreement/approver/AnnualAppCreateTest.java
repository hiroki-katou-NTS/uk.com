package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ErrorClassification;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ScreenDisplayInfo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class AnnualAppCreateTest {

	@Injectable
	private AnnualAppCreate.Require require;

	/**
	 * GettingApproverDomainService#getApprover returns empty result
	 * AgreementOneYear#checkErrorTimeExceeded returns false
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(2), new AgreementOneYearTime(1), reason);

		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.empty();
			}
		};

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

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getAtomTask()).isEmpty();
		assertThat(actual.getErrorInfo())
				.extracting(
						d -> d.getErrorClassification(),
						d -> d.getMaximumTimeMonth(),
						d -> d.getMaximumTimeYear(),
						d -> d.getExceedUpperLimit())
				.containsExactly(
						tuple(
								ErrorClassification.APPROVER_NOT_SET,
								Optional.empty(),
								Optional.empty(),
								Optional.empty()
						)
				);
	}

	/**
	 * GettingApproverDomainService#getApprover returns normal result
	 * AgreementOneYear#checkErrorTimeExceeded returns false
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(2), new AgreementOneYearTime(1), reason);

		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

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

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		NtsAssert.atomTask(
				() -> actual.getAtomTask().get(),
				any -> require.addApp(any.get()) // R2
		);
		assertThat(actual.getErrorInfo()).isEmpty();
	}

	/**
	 * GettingApproverDomainService#getApprover returns empty result
	 * AgreementOneYear#checkErrorTimeExceeded returns true
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(2), new AgreementOneYearTime(1), reason);

		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.empty();
			}
		};

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

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getAtomTask()).isEmpty();
		assertThat(actual.getErrorInfo())
				.extracting(
						d -> d.getErrorClassification(),
						d -> d.getMaximumTimeMonth(),
						d -> d.getMaximumTimeYear(),
						d -> d.getExceedUpperLimit())
				.containsExactly(
						tuple(
								ErrorClassification.APPROVER_NOT_SET,
								Optional.empty(),
								Optional.empty(),
								Optional.empty()
						),
						tuple(
								ErrorClassification.OVERTIME_LIMIT_ONE_YEAR,
								Optional.empty(),
								Optional.of(new AgreementOneYearTime(0)),
								Optional.empty()
						)

				);
	}

	/**
	 * GettingApproverDomainService#getApprover returns normal result
	 * AgreementOneYear#checkErrorTimeExceeded returns true
	 */
	@Test
	public void test04() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(2), new AgreementOneYearTime(1), reason);

		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

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

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getAtomTask()).isEmpty();
		assertThat(actual.getErrorInfo())
				.extracting(
						d -> d.getErrorClassification(),
						d -> d.getMaximumTimeMonth(),
						d -> d.getMaximumTimeYear(),
						d -> d.getExceedUpperLimit())
				.containsExactly(
						tuple(
								ErrorClassification.OVERTIME_LIMIT_ONE_YEAR,
								Optional.empty(),
								Optional.of(new AgreementOneYearTime(0)),
								Optional.empty()
						)

				);
	}

}

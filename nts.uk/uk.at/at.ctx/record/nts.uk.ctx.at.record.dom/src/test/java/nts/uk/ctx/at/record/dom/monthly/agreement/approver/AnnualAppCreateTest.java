package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ScreenDisplayInfo;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementsOneYear;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.standardtime.BasicAgreementSettings;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class AnnualAppCreateTest {

	@Injectable
	private AnnualAppCreate.Require require;

	/**
	 * 承認者を取得する#取得する returns empty result
	 * <p>
	 * Expected: AppCreationResult with Applicant Id 'dummyApplicantId' and ResultType APPROVER_NOT_SET
	 * should be returned
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(1), new AgreementOneYearTime(2), reason);

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getResultType()).isEqualTo(ResultType.APPROVER_NOT_SET);
	}

	/**
	 * 承認者を取得する#取得する returns empty result
	 * checkErrorTimeExceeded returns true
	 * <p>
	 * Expected: AppCreationResult with Applicant Id 'dummyApplicantId' and ResultType YEARLY_LIMIT_EXCEEDED
	 * should be returned
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(1), new AgreementOneYearTime(2), reason);

		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

		val oneYear = new AgreementsOneYear(
				new nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime(
						new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
						new AgreementOneYearTime(0)
				),
				new nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime(
						new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
						new AgreementOneYearTime(0)
				)
		);

		val setting = BasicAgreementSettings.of(new BasicAgreementSetting(null, oneYear, null, null), null);
		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSettings getBasicSet(
					AgreementDomainService.RequireM3 require,
					String companyId,
					String employeeId,
					GeneralDate criteriaDate,
					WorkingSystem workingSystem) {

				return setting;
			}
		};

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneYearTime>(true, new AgreementOneYearTime(0));
		new MockUp<AgreementsOneYear>() {
			@Mock
			public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
				return errCheckResult;
			}
		};

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getResultType()).isEqualTo(ResultType.YEARLY_LIMIT_EXCEEDED);
	}

	/**
	 * 承認者を取得する#取得する returns empty result
	 * checkErrorTimeExceeded returns false
	 *
	 * <p>
	 * Expected: AppCreationResult with Applicant Id 'dummyApplicantId' and ResultType NO_ERROR
	 * should be returned
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		AnnualAppContent appContent = new AnnualAppContent("dummyApplicantId", new Year(202009),
				new AgreementOneYearTime(1), new AgreementOneYearTime(2), reason);

		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

		val oneYear = new AgreementsOneYear(
				new nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime(
						new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
						new AgreementOneYearTime(0)
				),
				new nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime(
						new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
						new AgreementOneYearTime(0)
				)
		);

		val setting = BasicAgreementSettings.of(new BasicAgreementSetting(null, oneYear, null, null), null);
		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSettings getBasicSet(
					AgreementDomainService.RequireM3 require,
					String companyId,
					String employeeId,
					GeneralDate criteriaDate,
					WorkingSystem workingSystem) {

				return setting;
			}
		};

		val errCheckResult = new ImmutablePair<Boolean, AgreementOneYearTime>(false, new AgreementOneYearTime(0));
		new MockUp<AgreementsOneYear>() {
			@Mock
			public Pair<Boolean, AgreementOneYearTime> checkErrorTimeExceeded(AgreementOneYearTime applicationTime) {
				return errCheckResult;
			}
		};

		val actual = AnnualAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		assertThat(actual.getResultType()).isEqualTo(ResultType.NO_ERROR);
	}

}

package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class OneMonthAppCreateTest {

	@Injectable
	private OneMonthAppCreate.Require require;

	/**
	 * GettingApproverDomainService#getApprover returns empty result
	 * CheckErrorApplicationMonthService#check returns empty result
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.empty();
			}
		};

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

		List<ExcessErrorContent> emptyErrorInfo = new ArrayList<>();
		new MockUp<CheckErrorApplicationMonthService>() {
			@Mock
			public List<ExcessErrorContent> check(
					CheckErrorApplicationMonthService.Require require,
					MonthlyAppContent monthlyAppContent){

				return emptyErrorInfo;
			}
		};

		AppCreationResult actual = OneMonthAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
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
	 * CheckErrorApplicationMonthService#check returns empty result
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

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

		List<ExcessErrorContent> emptyErrorInfo = new ArrayList<>();
		new MockUp<CheckErrorApplicationMonthService>() {
			@Mock
			public List<ExcessErrorContent> check(
					CheckErrorApplicationMonthService.Require require,
					MonthlyAppContent monthlyAppContent){

				return emptyErrorInfo;
			}
		};

		AppCreationResult actual = OneMonthAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
		assertThat(actual.getEmpId()).isEqualTo(aplId);
		NtsAssert.atomTask(
				()-> actual.getAtomTask().get(),
				any -> require.addApp(any.get())
		);
		assertThat(actual.getErrorInfo()).isEmpty();
	}

	/**
	 * GettingApproverDomainService#getApprover returns normal result
	 * CheckErrorApplicationMonthService#check returns error
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));
		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.of(approverItem);
			}
		};

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

		List<ExcessErrorContent> errorInfo = new ArrayList<>();
		ExcessErrorContent error1 = ExcessErrorContent.create(
				ErrorClassification.ONE_MONTH_MAX_TIME,
				Optional.of(new AgreementOneMonthTime(1)),
				Optional.of(new AgreementOneYearTime(2)),
				Optional.of(AgreementOverMaxTimes.ONCE)
		);
		ExcessErrorContent error2 = ExcessErrorContent.create(
				ErrorClassification.TWO_MONTH_MAX_TIME,
				Optional.of(new AgreementOneMonthTime(3)),
				Optional.of(new AgreementOneYearTime(4)),
				Optional.of(AgreementOverMaxTimes.TWICE)
		);
		errorInfo.add(error1);
		errorInfo.add(error2);

		new MockUp<CheckErrorApplicationMonthService>() {
			@Mock
			public List<ExcessErrorContent> check(
					CheckErrorApplicationMonthService.Require require,
					MonthlyAppContent monthlyAppContent){

				return errorInfo;
			}
		};

		AppCreationResult actual = OneMonthAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
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
								ErrorClassification.ONE_MONTH_MAX_TIME,
								Optional.of(new AgreementOneMonthTime(1)),
								Optional.of(new AgreementOneYearTime(2)),
								Optional.of(AgreementOverMaxTimes.ONCE)
						),
						tuple(
								ErrorClassification.TWO_MONTH_MAX_TIME,
								Optional.of(new AgreementOneMonthTime(3)),
								Optional.of(new AgreementOneYearTime(4)),
								Optional.of(AgreementOverMaxTimes.TWICE)

						)
				);
	}

	/**
	 * GettingApproverDomainService#getApprover returns empty result
	 * CheckErrorApplicationMonthService#check returns error
	 */
	@Test
	public void test04() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		new MockUp<GettingApproverDomainService>() {
			@Mock
			public Optional<ApproverItem> getApprover(GettingApproverDomainService.Require require, String employeeId) {
				return Optional.empty();
			}
		};

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

		List<ExcessErrorContent> errorInfo = new ArrayList<>();
		ExcessErrorContent error1 = ExcessErrorContent.create(
				ErrorClassification.ONE_MONTH_MAX_TIME,
				Optional.of(new AgreementOneMonthTime(1)),
				Optional.of(new AgreementOneYearTime(2)),
				Optional.of(AgreementOverMaxTimes.ONCE)
		);

		errorInfo.add(error1);

		new MockUp<CheckErrorApplicationMonthService>() {
			@Mock
			public List<ExcessErrorContent> check(
					CheckErrorApplicationMonthService.Require require,
					MonthlyAppContent monthlyAppContent){

				return errorInfo;
			}
		};

		AppCreationResult actual = OneMonthAppCreate.create(require, cid, aplId, appContent, new ScreenDisplayInfo());
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
								ErrorClassification.ONE_MONTH_MAX_TIME,
								Optional.of(new AgreementOneMonthTime(1)),
								Optional.of(new AgreementOneYearTime(2)),
								Optional.of(AgreementOverMaxTimes.ONCE)

						)
				);
	}

}

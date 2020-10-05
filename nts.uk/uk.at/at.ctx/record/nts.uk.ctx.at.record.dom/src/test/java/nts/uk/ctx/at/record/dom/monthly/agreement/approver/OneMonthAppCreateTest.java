package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class OneMonthAppCreateTest {

	@Injectable
	private OneMonthAppCreate.Require require;

	/**
	 * 承認者を取得する#取得する returns empty result
	 */
	@Test
	public void test01() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

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
		assertThat(actual.getErrorInfo().get(0).getErrorClassification()).isEqualTo(ErrorClassification.APPROVER_NOT_SET);
	}

	/**
	 * 承認者を取得する#取得する returns empty result
	 1ヶ月申請の超過エラーをチェックする returns empty
	 */
	@Test
	public void test02() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
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
		assertThat(actual.getErrorInfo()).isEmpty();
	}

	/**
	 * 承認者を取得する#取得する returns empty result
	 * 1ヶ月申請の超過エラーをチェックする returns error
	 */
	@Test
	public void test03() {
		String cid = "dummyCid";
		String aplId = "dummyApplicantId";
		ReasonsForAgreement reason = new ReasonsForAgreement("reason");
		MonthlyAppContent appContent = new MonthlyAppContent("dummyApplicantId", new YearMonth(202009),
				new AgreementOneMonthTime(2), Optional.of(new AgreementOneMonthTime(1)), reason);

		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
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
		ExcessErrorContent error = ExcessErrorContent.create(
				ErrorClassification.ONE_MONTH_MAX_TIME,
				Optional.of(new AgreementOneMonthTime(1)),
				Optional.of(new AgreementOneYearTime(1)),
				Optional.of(AgreementOverMaxTimes.TWELVE_TIMES)
		);
		errorInfo.add(error);

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
		assertThat(actual.getErrorInfo().get(0).getErrorClassification()).isEqualTo(ErrorClassification.ONE_MONTH_MAX_TIME);
	}

}

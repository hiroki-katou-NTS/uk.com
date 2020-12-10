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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class AppApprovalTest {

	@Injectable
	private AppApproval.Require require;

	/**
	 * R1 returns empty
	 * <p>
	 * Expected: BusinessException with Msg_1262
	 */
	@Test
	public void test01() {
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.APPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.empty();
		}};

		NtsAssert.businessException("Msg_1262", () -> AppApproval.change(require, aplId, aprId, apprSts, cmt));
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is UNAPPROVED
	 * <p>
	 * Expected: R4 should be called
	 */
	@Test
	public void test02() {
		String aplId = "aplId";
		String aprId = "dummyApproverId";
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.UNAPPROVED;

		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"aplId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_MONTH,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				Arrays.asList("apr01", "apr02", "apr03"), // approver sid list
				Arrays.asList("cfm01", "cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val yesterday = GeneralDate.today().addDays(-1);
		dummyApp.getApprovalStatusDetails().setApprovalDate(Optional.of(yesterday));
		val aprStsDetail = ApprovalStatusDetails.create(
				ApprovalStatus.APPROVED,
				Optional.of("apr"),
				Optional.of(new AgreementApprovalComments("comment2")),
				Optional.of(yesterday));
		dummyApp.setApprovalStatusDetails(aprStsDetail);

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()) // R4
		);

		assertThat(dummyApp.getApprovalStatusDetails().getApprovalStatus()).isEqualTo(ApprovalStatus.UNAPPROVED);
		assertThat(dummyApp.getApprovalStatusDetails().getApproveSID().get()).isEqualTo(aprId);
		assertThat(dummyApp.getApprovalStatusDetails().getApprovalComment()).isEqualTo(cmt);
		assertThat(dummyApp.getApprovalStatusDetails().getApprovalDate().get()).isEqualTo(GeneralDate.today());
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is APPROVED
	 * Type of Agreement is ONE_MONTH
	 * R2 returns empty result
	 * <p>
	 * Expected: R4, R5 should be called
	 */
	@Test
	public void test03() {
		String aplId = "aplId";
		String aprId = "dummyApproverId";
		val apprSts = ApprovalStatus.APPROVED;
		val cmt = Optional.of(new AgreementApprovalComments("comment"));

		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"aplId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_MONTH,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				Arrays.asList("apr01", "apr02", "apr03"), // approver sid list
				Arrays.asList("cfm01", "cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		// R2
		new Expectations() {{
			val yearMonth = dummyApp.getApplicationTime().getOneMonthTime().get().getYearMonth();
			require.getYearMonthSetting(aplId, yearMonth);
			result = Optional.empty();
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()), // R4
				any -> require.addYearMonthSetting(any.get()) // R5
		);
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is APPROVED
	 * Type of Agreement is ONE_MONTH
	 * R2 returns normal result
	 * <p>
	 * Expected:	R4, R6 should be called
	 */
	@Test
	public void test04() {
		String aplId = "aplId";
		String aprId = "dummyApproverId";
		val apprSts = ApprovalStatus.APPROVED;
		val cmt = Optional.of(new AgreementApprovalComments("comment"));

		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"aplId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_MONTH,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				Arrays.asList("apr01", "apr02", "apr03"), // approver sid list
				Arrays.asList("cfm01", "cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		// R2
		new Expectations() {{
			val exMonthSetting = new AgreementMonthSetting(aplId, new YearMonth(0), new OneMonthErrorAlarmTime());
			val yearMonth = dummyApp.getApplicationTime().getOneMonthTime().get().getYearMonth();
			require.getYearMonthSetting(aplId, yearMonth);
			result = Optional.of(exMonthSetting);
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()), // R4
				any -> require.updateYearMonthSetting(any.get()) // R6
		);
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is APPROVED
	 * Type of Agreement is ONE_YEAR
	 * R3 returns empty result
	 * <p>
	 * Expected:	R4, R7 should be called
	 */
	@Test
	public void test05() {
		String aplId = "aplId";
		String aprId = "dummyApproverId";
		val apprSts = ApprovalStatus.APPROVED;
		val cmt = Optional.of(new AgreementApprovalComments("comment"));

		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"aplId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_YEAR,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				Arrays.asList("apr01", "apr02", "apr03"), // approver sid list
				Arrays.asList("cfm01", "cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		val year = dummyApp.getApplicationTime().getOneYearTime().get().getYear();

		// R3
		new Expectations() {{
			require.getYearSetting(aplId, year);
			result = Optional.empty();
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()), // R4
				any -> require.addYearSetting(any.get()) // R7
		);
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is APPROVED
	 * Type of Agreement is ONE_YEAR
	 * R3 returns normal result
	 * <p>
	 * Expected:	R4, R8 should be called
	 */
	@Test
	public void test06() {
		String aplId = "aplId";
		String aprId = "dummyApproverId";
		val apprSts = ApprovalStatus.APPROVED;
		val cmt = Optional.of(new AgreementApprovalComments("comment"));

		val dummyApp = SpecialProvisionsOfAgreement.create(
				"enteredSID",
				"aplId",
				new ApplicationTime(
						TypeAgreementApplication.ONE_YEAR,
						Optional.of(new OneMonthTime(new OneMonthErrorAlarmTime(), new YearMonth(0))),
						Optional.of(new OneYearTime(new OneYearErrorAlarmTime(), new Year(0)))),
				new ReasonsForAgreement("reason"),
				Arrays.asList("apr01", "apr02", "apr03"), // approver sid list
				Arrays.asList("cfm01", "cfm02", "cfm03"), // confirmation sid list
				new ScreenDisplayInfo());

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		// R3
		new Expectations() {{
			val exYearSetting = new AgreementYearSetting(aplId, 0, new OneYearErrorAlarmTime());
			val year = dummyApp.getApplicationTime().getOneYearTime().get().getYear();
			require.getYearSetting(aplId, year);
			result = Optional.of(exYearSetting);
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()), // R4
				any -> require.updateYearSetting(any.get()) // R8
		);
	}
}

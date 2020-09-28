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
		val apprSts = ApprovalStatus.UNAPPROVED;

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
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val dummyApp = createDummyApp(TypeAgreementApplication.ONE_MONTH);
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.UNAPPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		new Expectations() {{
			dummyApp.approveApplication(aplId, apprSts, cmt);
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()) // R4
		);
	}

	/**
	 * R1 returns normal result
	 * ApprovalStatus is APPROVED
	 * Type of Agreement is ONE_MONTH
	 * R2 returns empty result
	 * <p>
	 * Expected:	R4, R5 should be called
	 */
	@Test
	public void test03() {
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val dummyApp = createDummyApp(TypeAgreementApplication.ONE_MONTH);
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.APPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		new Expectations() {{
			dummyApp.approveApplication(aplId, apprSts, cmt);
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
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val dummyApp = createDummyApp(TypeAgreementApplication.ONE_MONTH);
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.APPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		new Expectations() {{
			dummyApp.approveApplication(aplId, apprSts, cmt);
		}};

		val existingAgr36MonthSetting = new AgreementMonthSetting(
				aplId, new YearMonth(0), new ErrorOneMonth(0), new AlarmOneMonth(0));
		val yearMonth = dummyApp.getApplicationTime().getOneMonthTime().get().getYearMonth();

		// R2
		new Expectations() {{
			require.getYearMonthSetting(aplId, yearMonth);
			result = Optional.of(existingAgr36MonthSetting);
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
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val dummyApp = createDummyApp(TypeAgreementApplication.ONE_YEAR);
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.APPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		new Expectations() {{
			dummyApp.approveApplication(aplId, apprSts, cmt);
		}};

		val existingAgr36YearSetting = new AgreementYearSetting(aplId, 0, new ErrorOneYear(0), new AlarmOneYear(0));
		val year = dummyApp.getApplicationTime().getOneYearTime().get().getYear();

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
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val dummyApp = createDummyApp(TypeAgreementApplication.ONE_YEAR);
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.APPROVED;

		// R1
		new Expectations() {{
			require.getApp(aplId);
			result = Optional.of(dummyApp);
		}};

		new Expectations() {{
			dummyApp.approveApplication(aplId, apprSts, cmt);
		}};

		val existingAgr36YearSetting = new AgreementYearSetting(
				aplId, 0, new ErrorOneYear(0), new AlarmOneYear(0));
		val year = dummyApp.getApplicationTime().getOneYearTime().get().getYear();

		new Expectations() {{
			require.getYearSetting(aplId, year); // R3
			result = Optional.of(existingAgr36YearSetting);
		}};

		NtsAssert.atomTask(
				() -> AppApproval.change(require, aplId, aprId, apprSts, cmt),
				any -> require.updateApp(any.get()), // R4
				any -> require.updateYearSetting(any.get()) // R8
		);
	}

	private static SpecialProvisionsOfAgreement createDummyApp(TypeAgreementApplication type) {
		OneMonthTime oneMonthTime = new OneMonthTime(
				new ErrorTimeInMonth(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)),
				new YearMonth(0)
		);

		OneYearTime oneYearTime = new OneYearTime(
				new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)),
				new Year(0)
		);

		ApplicationTime applicationTime = new ApplicationTime(type, Optional.of(oneMonthTime), Optional.of(oneYearTime));

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

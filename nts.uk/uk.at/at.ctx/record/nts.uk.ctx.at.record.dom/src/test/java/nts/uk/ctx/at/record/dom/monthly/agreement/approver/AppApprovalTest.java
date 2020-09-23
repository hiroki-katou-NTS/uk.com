package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class AppApprovalTest {

	@Injectable
	private AppApproval.Require require;

	@Test
	public void test01__R1_Empty__BusinessException_Msg_1262() {
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.UNAPPROVED;

		//NtsAssert.businessException("Msg_1262", () -> AppApproval.change( require, aplId, aprId, apprSts, cmt));
	}

	@Test
	public void test02__R1_Empty__BusinessException_Msg_1262() {
		String aplId = "dummyApplicantId";
		String aprId = "dummyApproverId";
		//val dummyApp = Helper.createNewDomain();
		//val dummyApp = createNewDomain();

		OneMonthTime oneMonthTime = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)), new YearMonth(0));
		OneYearTime oneYearTime = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
//		val dummyApp = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
//				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.UNAPPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		List<ConfirmationStatusDetails> confirmationStatusDetails = new ArrayList<>();
		listConfirmSID.forEach(sid -> {
			confirmationStatusDetails.add(new ConfirmationStatusDetails(ConfirmationStatus.UNCONFIRMED, sid, Optional.empty()));
		});

		val dummyApp = new SpecialProvisionsOfAgreement(
				IdentifierUtil.randomUniqueId(),
				"enteredPersonSID",
				GeneralDate.today(),
				"applicantsSID",
				applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),
				new ArrayList<>(),
				approvalStatusDetails,
				confirmationStatusDetails,
				new ScreenDisplayInfo()
		);


		// val dummyApp = SpecialProvisionsOfAgreementTest.createNewDomain();
		val cmt = Optional.of(new AgreementApprovalComments("comment"));
		val apprSts = ApprovalStatus.UNAPPROVED;



		new Expectations(){{
			require.getApp(aplId);
			result = dummyApp;
		}};

//		NtsAssert.atomTask(
//				() -> AppApproval.change( require, aplId, aprId, apprSts, cmt),
//				any -> require.updateApp(any.get())
//		);
	}

	private static SpecialProvisionsOfAgreement createNewDomain() {
		OneMonthTime oneMonthTime = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)), new YearMonth(0));
		OneYearTime oneYearTime = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(0), new AgreementOneYearTime(0)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		return SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());
	}

}

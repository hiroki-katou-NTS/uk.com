package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class SpecialProvisionsOfAgreementTest {

	public static SpecialProvisionsOfAgreement createNewDomain() {
		OneMonthTime oneMonthTime = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)), new YearMonth(0));
		OneYearTime oneYearTime = new OneYearTime(new ErrorTimeInYear(new AgreementOneMonthTime(0), new AgreementOneMonthTime(0)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		return SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());
	}

	@Test
	public void getters() {
		val domain = createNewDomain();
		NtsAssert.invokeGetters(domain);
	}

	@Test
	public void calculateAlarmTimeTest() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(EnumAdaptor.valueOf(1,ApprovalStatus.class),
				Optional.of(new AgreementApprovalComments("approvalComment")),Optional.of(GeneralDate.today()),Optional.of("approverSID"));

		target.approveApplication(approvalStatusDetails.getApproveSID().get(),approvalStatusDetails.getApprovalStatus(),
				approvalStatusDetails.getApprovalComment());

		Assert.assertEquals(target.getApprovalStatusDetails().getApproveSID(), approvalStatusDetails.getApproveSID());
		Assert.assertEquals(target.getApprovalStatusDetails().getApprovalComment(), approvalStatusDetails.getApprovalComment());
		Assert.assertEquals(target.getApprovalStatusDetails().getApprovalDate(), approvalStatusDetails.getApprovalDate());
		Assert.assertEquals(target.getApprovalStatusDetails().getApprovalStatus(), approvalStatusDetails.getApprovalStatus());

	}

	@Test
	public void confirmApplicationTest() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ConfirmationStatusDetails confirmationStatusDetails = new ConfirmationStatusDetails(EnumAdaptor.valueOf(1,ConfirmationStatus.class),
				"confirmerSID",Optional.of(GeneralDate.today()));

		target.confirmApplication(confirmationStatusDetails.getConfirmerSID(),confirmationStatusDetails.getConfirmationStatus());

		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmDate(), Optional.of(GeneralDate.today()));
		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmationStatus(), confirmationStatusDetails.getConfirmationStatus());

	}

	@Test
	public void changeApplicationOneMonthTest_1() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		Assert.assertEquals(target.getReasonsForAgreement().v(), "Reason");
		Assert.assertEquals(target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth(), errorTimeInMonth);

	}

	@Test
	public void changeApplicationOneMonthTest_2() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		Assert.assertEquals(target.getReasonsForAgreement().v(), "Reason");
		Assert.assertEquals(target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth(), errorTimeInMonth);
		Assert.assertEquals(target.getApprovalStatusDetails().getApprovalStatus().value, ApprovalStatus.UNAPPROVED.value);
		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmationStatus().value, ConfirmationStatus.UNCONFIRMED.value);
		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmDate(), Optional.empty());
	}

	@Test
	public void changeApplicationYearTest_1() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		Assert.assertEquals(target.getReasonsForAgreement().v(), "Reason");
		Assert.assertEquals(target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear(), errorTimeInYear);

	}

	@Test
	public void changeApplicationYearTest_2() {
		SpecialProvisionsOfAgreement target = createNewDomain();

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		Assert.assertEquals(target.getReasonsForAgreement().v(), "Reason");
		Assert.assertEquals(target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear(), errorTimeInYear);
		Assert.assertEquals(target.getApprovalStatusDetails().getApprovalStatus().value, ApprovalStatus.UNAPPROVED.value);
		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmationStatus().value, ConfirmationStatus.UNCONFIRMED.value);
		Assert.assertEquals(target.getConfirmationStatusDetails().get(0).getConfirmDate(), Optional.empty());
	}


}

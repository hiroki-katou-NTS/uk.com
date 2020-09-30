package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class SpecialProvisionsOfAgreementTest {

	public static SpecialProvisionsOfAgreement createNewDomain() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		return SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());
	}

	@Test
	public void getters() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		NtsAssert.invokeGetters(target);
	}

	@Test
	public void calculateAlarmTimeTest() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(EnumAdaptor.valueOf(ApprovalStatus.UNAPPROVED.value,ApprovalStatus.class),
				Optional.of("approverSID"),Optional.of(new AgreementApprovalComments("approvalComment")),Optional.of(GeneralDate.today()));

		target.approveApplication(approvalStatusDetails.getApproveSID().get(),approvalStatusDetails.getApprovalStatus(),
				approvalStatusDetails.getApprovalComment());

		Assert.assertEquals(approvalStatusDetails.getApproveSID(),target.getApprovalStatusDetails().getApproveSID());
		Assert.assertEquals(approvalStatusDetails.getApprovalComment(),target.getApprovalStatusDetails().getApprovalComment());
		Assert.assertEquals(approvalStatusDetails.getApprovalDate(),target.getApprovalStatusDetails().getApprovalDate());
		Assert.assertEquals(approvalStatusDetails.getApprovalStatus(),target.getApprovalStatusDetails().getApprovalStatus());

	}

	@Test
	public void confirmApplicationTest() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ConfirmationStatusDetails confirmationStatusDetails = new ConfirmationStatusDetails(EnumAdaptor.valueOf(1,ConfirmationStatus.class),
				"confirmerSID",Optional.of(GeneralDate.today()));

		target.confirmApplication(confirmationStatusDetails.getConfirmerSID(),confirmationStatusDetails.getConfirmationStatus());

		Assert.assertEquals(Optional.of(GeneralDate.today()),target.getConfirmationStatusDetails().get(0).getConfirmDate());
		Assert.assertEquals(confirmationStatusDetails.getConfirmationStatus(),target.getConfirmationStatusDetails().get(0).getConfirmationStatus());

	}

	@Test
	public void changeApplicationOneMonthTest_1() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		OneMonthErrorAlarmTime errorTimeInMonth = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		Assert.assertEquals("Reason",target.getReasonsForAgreement().v());
		Assert.assertEquals(errorTimeInMonth,target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth());

	}

	@Test
	public void changeApplicationOneMonthTest_2() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		OneMonthErrorAlarmTime errorTimeInMonth = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		Assert.assertEquals("Reason",target.getReasonsForAgreement().v());
		Assert.assertEquals(errorTimeInMonth,target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth());
		Assert.assertEquals(ApprovalStatus.UNAPPROVED.value,target.getApprovalStatusDetails().getApprovalStatus().value);
		Assert.assertEquals(ConfirmationStatus.UNCONFIRMED.value,target.getConfirmationStatusDetails().get(0).getConfirmationStatus().value);
		Assert.assertEquals(Optional.empty(),target.getConfirmationStatusDetails().get(0).getConfirmDate());
	}

	@Test
	public void changeApplicationYearTest_1() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		OneYearErrorAlarmTime errorTimeInYear = new OneYearErrorAlarmTime(new AgreementOneYearTime(1), new AgreementOneYearTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		Assert.assertEquals("Reason",target.getReasonsForAgreement().v());
		Assert.assertEquals(errorTimeInYear,target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear());

	}

	@Test
	public void changeApplicationYearTest_2() {
		OneMonthTime oneMonthTime = OneMonthTime.create(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(new OneYearErrorAlarmTime(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(EnumAdaptor.valueOf(0, TypeAgreementApplication.class), Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		OneYearErrorAlarmTime errorTimeInYear = new OneYearErrorAlarmTime(new AgreementOneYearTime(1), new AgreementOneYearTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		Assert.assertEquals("Reason",target.getReasonsForAgreement().v());
		Assert.assertEquals(errorTimeInYear,target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear());
		Assert.assertEquals(ApprovalStatus.UNAPPROVED.value,target.getApprovalStatusDetails().getApprovalStatus().value);
		Assert.assertEquals(ConfirmationStatus.UNCONFIRMED.value,target.getConfirmationStatusDetails().get(0).getConfirmationStatus().value);
		Assert.assertEquals(Optional.empty(),target.getConfirmationStatusDetails().get(0).getConfirmDate());
	}


}

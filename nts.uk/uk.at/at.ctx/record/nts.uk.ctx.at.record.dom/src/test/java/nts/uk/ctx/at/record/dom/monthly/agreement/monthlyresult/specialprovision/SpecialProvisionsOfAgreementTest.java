package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;


public class SpecialProvisionsOfAgreementTest {

	@Test
	public void getters() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		NtsAssert.invokeGetters(target);
	}

	@Test
	public void createTest() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));
		ApprovalStatusDetails approvalStatusDetails = ApprovalStatusDetails.create(ApprovalStatus.UNAPPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		List<String> listConfirmSID = Arrays.asList("confirmSid1","confirmSid2");

		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),Arrays.asList(),listConfirmSID,new ScreenDisplayInfo());

		assertThat(target.getInputDate().toDate()).isEqualTo(GeneralDateTime.now().toDate());
		assertThat(target.getApprovalStatusDetails()).isEqualToComparingFieldByField(approvalStatusDetails);
		assertThat(target.getConfirmationStatusDetails().size()).isEqualTo(2);
		assertThat(target.getConfirmationStatusDetails())
				.extracting(
						d -> d.getConfirmerSID(),
						d -> d.getConfirmDate(),
						d -> d.getConfirmationStatus())
				.containsExactly(
						tuple("confirmSid1", Optional.empty(), ConfirmationStatus.UNCONFIRMED),
						tuple("confirmSid2", Optional.empty(), ConfirmationStatus.UNCONFIRMED)
				);

	}

	@Test
	public void calculateAlarmTimeTest() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.UNAPPROVED,
				Optional.of("approverSID"),Optional.of(new AgreementApprovalComments("approvalComment")),Optional.of(GeneralDate.today()));

		target.approveApplication(approvalStatusDetails.getApproveSID().get(),approvalStatusDetails.getApprovalStatus(),
				approvalStatusDetails.getApprovalComment());

		assertThat(target.getApprovalStatusDetails().getApproveSID()).isEqualTo(approvalStatusDetails.getApproveSID());
		assertThat(target.getApprovalStatusDetails().getApprovalComment()).isEqualTo(approvalStatusDetails.getApprovalComment());
		assertThat(target.getApprovalStatusDetails().getApprovalDate()).isEqualTo(approvalStatusDetails.getApprovalDate());
		assertThat(target.getApprovalStatusDetails().getApprovalStatus()).isEqualTo(approvalStatusDetails.getApprovalStatus());

	}

	@Test
	public void confirmApplicationTest() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ConfirmationStatusDetails confirmationStatusDetails = new ConfirmationStatusDetails("confirmerSID",ConfirmationStatus.CONFIRMED,
				Optional.of(GeneralDate.ymd(2020,10,19)));

		target.confirmApplication(confirmationStatusDetails.getConfirmerSID(),confirmationStatusDetails.getConfirmationStatus());

		assertThat(target.getConfirmationStatusDetails())
				.extracting(
						d -> d.getConfirmDate(),
						d -> d.getConfirmationStatus())
				.containsExactly(
						tuple(Optional.of(GeneralDate.today()),ConfirmationStatus.CONFIRMED)
				);

	}

	@Test
	public void changeApplicationOneMonthTest_1() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		OneMonthErrorAlarmTime errorTimeInMonth = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		assertThat(target.getReasonsForAgreement().v()).isEqualTo("Reason");
		assertThat(target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth()).isEqualTo(errorTimeInMonth);

	}

	@Test
	public void changeApplicationOneMonthTest_2() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		OneMonthErrorAlarmTime errorTimeInMonth = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(1), new AgreementOneMonthTime(1));

		target.changeApplicationOneMonth(errorTimeInMonth,new ReasonsForAgreement("Reason"));

		assertThat(target.getReasonsForAgreement().v()).isEqualTo("Reason");
		assertThat(target.getApplicationTime().getOneMonthTime().get().getErrorTimeInMonth()).isEqualTo(errorTimeInMonth);
		assertThat(target.getApprovalStatusDetails().getApprovalStatus().value).isEqualTo(ApprovalStatus.UNAPPROVED.value);

		assertThat(target.getConfirmationStatusDetails())
				.extracting(
						d -> d.getConfirmDate(),
						d -> d.getConfirmationStatus())
				.containsExactly(
						tuple(Optional.empty(),ConfirmationStatus.UNCONFIRMED)
				);

	}

	@Test
	public void changeApplicationYearTest_1() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		OneYearErrorAlarmTime errorTimeInYear = OneYearErrorAlarmTime.of(new AgreementOneYearTime(1), new AgreementOneYearTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		assertThat(target.getReasonsForAgreement().v()).isEqualTo("Reason");
		assertThat(target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear()).isEqualTo(errorTimeInYear);

	}

	@Test
	public void changeApplicationYearTest_2() {
		OneMonthTime oneMonthTime = OneMonthTime.create(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20)), new YearMonth(202009));
		OneYearTime oneYearTime = OneYearTime.create(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20)), new Year(2020));

		ApplicationTime applicationTime = new ApplicationTime(TypeAgreementApplication.ONE_MONTH, Optional.of(oneMonthTime), Optional.of(oneYearTime));

		List<String> listConfirmSID = Arrays.asList("confirmerSID");
		SpecialProvisionsOfAgreement target = SpecialProvisionsOfAgreement.create("enteredPersonSID","applicantsSID",applicationTime,
				new ReasonsForAgreement("reasonsForAgreement"),new ArrayList<>(),listConfirmSID,new ScreenDisplayInfo());

		ApprovalStatusDetails approvalStatusDetails = new ApprovalStatusDetails(ApprovalStatus.APPROVED, Optional.empty(), Optional.empty(), Optional.empty());
		target.setApprovalStatusDetails(approvalStatusDetails);
		OneYearErrorAlarmTime errorTimeInYear = OneYearErrorAlarmTime.of(new AgreementOneYearTime(1), new AgreementOneYearTime(1));

		target.changeApplicationYear(errorTimeInYear,new ReasonsForAgreement("Reason"));

		assertThat(target.getReasonsForAgreement().v()).isEqualTo("Reason");
		assertThat(target.getApplicationTime().getOneYearTime().get().getErrorTimeInYear()).isEqualTo(errorTimeInYear);
		assertThat(target.getApprovalStatusDetails().getApprovalStatus().value).isEqualTo(ApprovalStatus.UNAPPROVED.value);
		assertThat(target.getConfirmationStatusDetails())
				.extracting(
						d -> d.getConfirmDate(),
						d -> d.getConfirmationStatus())
				.containsExactly(
						tuple(Optional.empty(),ConfirmationStatus.UNCONFIRMED)
				);
	}


}

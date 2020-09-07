package nts.uk.ctx.at.schedule.dom.budget.performance.domainservice;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.budget.performance.domainservice.RegisterExtBudgetDailyService.Require;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgHelper;

@RunWith(JMockit.class)
public class RegisterExtBudgetDailyServiceTest {

	@Injectable
	private Require require;

	/**
	 * if 値.isEmpty
	 */
	@Test
	public void testSignUp_1() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.empty();

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd));
	}

	/**
	 * if 値 is not Empty if 値 : 外部予算実績金額(PrimitiveValue)
	 */
	@Test
	public void testSignUp_2() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.of(new ExtBudgetMoney(999));

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd), any -> require.insert(any.get()));
	}

	/**
	 * if 値 is not Empty if 値 : 外部予算実績単価(PrimitiveValue)
	 */
	@Test
	public void testSignUp_3() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.of(new ExtBudgetUnitPrice(999));

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd), any -> require.insert(any.get()));
	}

	/**
	 * if 値 is not Empty if 値 : 外部予算実績数値(PrimitiveValue)
	 */
	@Test
	public void testSignUp_4() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.of(new ExtBudgetNumericalVal(999));

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd), any -> require.insert(any.get()));
	}

	/**
	 * if 値 is not Empty if 値 : 外部予算実績人数(PrimitiveValue)
	 */
	@Test
	public void testSignUp_5() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.of(new ExtBudgetNumberPerson(999));

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd), any -> require.insert(any.get()));
	}

	/**
	 * if 値 is not Empty if 値 : 外部予算実績時間(PrimitiveValue)
	 */
	@Test
	public void testSignUp_6() {
		TargetOrgIdenInfor targetOrg = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetActItemCode itemCode = new ExtBudgetActItemCode("itemCode");
		GeneralDate ymd = GeneralDate.today();
		Optional<ExtBudgetActualValues> extBudgetActualValue = Optional.of(new ExtBudgetTime(999));

		NtsAssert.atomTask(
				() -> RegisterExtBudgetDailyService.signUp(require, targetOrg, itemCode, ymd, extBudgetActualValue),
				any -> require.delete(targetOrg, itemCode, ymd), any -> require.insert(any.get()));
	}

}

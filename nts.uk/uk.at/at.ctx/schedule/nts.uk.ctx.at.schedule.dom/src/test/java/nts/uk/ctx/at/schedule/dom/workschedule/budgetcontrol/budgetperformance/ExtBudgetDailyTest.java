package nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgHelper;

public class ExtBudgetDailyTest {

	@Test
	public void getters() {
		TargetOrgIdenInfor targetOrgIdenInfor = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExtBudgetDaily extBudgetDaily = new ExtBudgetDaily(targetOrgIdenInfor, new ExtBudgetActItemCode("001"),
				GeneralDate.today(), new ExtBudgetNumberPerson(123));
		NtsAssert.invokeGetters(extBudgetDaily);
	}

}

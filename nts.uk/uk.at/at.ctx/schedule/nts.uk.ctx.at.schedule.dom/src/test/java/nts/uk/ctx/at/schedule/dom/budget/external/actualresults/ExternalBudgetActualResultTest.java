package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgHelper;

public class ExternalBudgetActualResultTest {

	@Test
	public void getters() {
		ExternalBudgetActualResult extBudgetDaily = new ExternalBudgetActualResult(
				ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty()
			,	new ExternalBudgetCd("001")
			,	GeneralDate.today()
			,	new ExternalBudgetMoneyValue(123)
		);
		NtsAssert.invokeGetters(extBudgetDaily);
	}

}

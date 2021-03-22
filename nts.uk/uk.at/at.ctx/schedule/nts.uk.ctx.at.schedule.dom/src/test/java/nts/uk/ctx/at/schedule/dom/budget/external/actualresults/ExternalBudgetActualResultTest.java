package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumberPerson;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgHelper;

public class ExternalBudgetActualResultTest {

	@Test
	public void getters() {
		TargetOrgIdenInfor targetOrgIdenInfor = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		ExternalBudgetActualResult extBudgetDaily = new ExternalBudgetActualResult(targetOrgIdenInfor, new ExternalBudgetCd("001"),
				GeneralDate.today(), new ExtBudgetNumberPerson(123));
		NtsAssert.invokeGetters(extBudgetDaily);
	}

}

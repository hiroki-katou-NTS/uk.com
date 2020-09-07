package nts.uk.ctx.at.record.dom.workrecord.workrecord;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public class EmploymentConfirmedTest {

	@Test
	public void getters() {
		EmploymentConfirmed confirmed = new EmploymentConfirmed(new CompanyId("DUMMY"),
				new WorkplaceId("DUMMY"),
				ClosureId.ClosureFive,
				new YearMonth(2020),
				"DUMMY",
				GeneralDateTime.now());
		
		NtsAssert.invokeGetters(confirmed);
	}

}

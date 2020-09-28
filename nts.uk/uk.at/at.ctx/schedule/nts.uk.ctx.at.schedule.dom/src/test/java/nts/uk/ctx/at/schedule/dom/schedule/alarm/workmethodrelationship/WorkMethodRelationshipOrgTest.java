package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class WorkMethodRelationshipOrgTest {
	@Test
	public void getters() {
		val workMethodRelaOrg = new WorkMethodRelationshipOrg(TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				WorkMethodRelationshipHelper.DUMMY);
		NtsAssert.invokeGetters(workMethodRelaOrg);
	}
}

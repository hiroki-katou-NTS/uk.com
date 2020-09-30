package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class WorkMethodRelationshipComTest {
	@Test
	public void getters() {
		val WorkMethodRelationshipCom = new WorkMethodRelationshipCompany(WorkMethodRelationshipHelper.DUMMY);
		NtsAssert.invokeGetters(WorkMethodRelationshipCom);
	}
	
}

package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;

@RunWith(JMockit.class)
public class ApproverRegisterSetTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(new ApproverRegisterSet());
	}
	
	/**
	 * [C-1]社員単位だけ利用するで作成する
	 */
	@Test
	public void testCreateForEmployee() {
		
		// when
		ApproverRegisterSet domain = ApproverRegisterSet.createForEmployee();
		
		// then
		assertThat(domain.getCompanyUnit()).isEqualTo(UseClassification.DO_NOT_USE);
		assertThat(domain.getWorkplaceUnit()).isEqualTo(UseClassification.DO_NOT_USE);
		assertThat(domain.getEmployeeUnit()).isEqualTo(UseClassification.DO_USE);
	}
}

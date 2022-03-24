package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ApproverTest {

	/**
	 * Test [C-1] 一承認者で作成する
	 */
	@Test
	public void testCreateSimpleFromJavaType() {
		String expApproverId = "exp-approval-id";
		
		Approver domain = Approver.createSimpleFromJavaType(expApproverId);
		
		assertThat(domain.getConfirmPerson()).isEqualTo(ConfirmPerson.NOT_CONFIRM);
		assertThat(domain.getApproverOrder()).isEqualTo(1);
		assertThat(domain.getEmployeeId()).isEqualTo(expApproverId);
		assertThat(domain.getJobGCD()).isEmpty();
		assertThat(domain.getSpecWkpId()).isEmpty();
	}
}

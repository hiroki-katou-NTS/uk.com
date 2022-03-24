package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ApprovalPhaseTest {

	/**
	 * Test [C-1] 一承認者で作成する
	 */
	@Test
	public void testCreateSimpleFromJavaType() {
		String expApprovalId = "exp-approver-id";
		int expPhaseOrder = 1;
		String expApproverId = "exp-approval-id";
		Approver expApprover = Approver.createSimpleFromJavaType(expApproverId);
		
		ApprovalPhase domain = ApprovalPhase.createSimpleFromJavaType(expApprovalId, expPhaseOrder, expApproverId);
		
		assertThat(domain.getBrowsingPhase()).isEqualTo(0);
		assertThat(domain.getPhaseOrder()).isEqualTo(expPhaseOrder);
		assertThat(domain.getApprovalForm()).isEqualTo(ApprovalForm.EVERYONE_APPROVED);
		assertThat(domain.getApprovalId()).isEqualTo(expApprovalId);
		assertThat(domain.getApprovalAtr()).isEqualTo(ApprovalAtr.PERSON);
		assertThat(domain.getApprovers().size()).isEqualTo(1);
		assertThat(domain.getApprovers().get(0)).isEqualToComparingFieldByField(expApprover);
	}
}

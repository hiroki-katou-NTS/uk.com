package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.List;

import lombok.Data;

@Data
public class ApprovalPhaseStateDto {
	private Integer phaseOrder;
	private int approvalAtr;
	private int approvalForm;
	private List<ApprovalFrameDto> listApprovalFrame;
	
	public ApprovalPhaseStateDto(Integer phaseOrder, int approvalAtr, int approvalForm,
			List<ApprovalFrameDto> listApprovalFrame) {
		super();
		this.phaseOrder = phaseOrder;
		this.approvalAtr = approvalAtr;
		this.approvalForm = approvalForm;
		this.listApprovalFrame = listApprovalFrame;
	}
	
	
}

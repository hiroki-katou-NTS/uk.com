package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ApprovalFrameDto {
	private Integer frameOrder;
	private List<ApproverStateDto> listApprover;
	private int confirmAtr;
	private GeneralDate appDate;
	
	public ApprovalFrameDto(Integer frameOrder, List<ApproverStateDto> listApprover, int confirmAtr,
			GeneralDate appDate) {
		super();
		this.frameOrder = frameOrder;
		this.listApprover = listApprover;
		this.confirmAtr = confirmAtr;
		this.appDate = appDate;
	}
	
	
}

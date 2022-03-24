package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class ApprovalRootStateDto {
	private String rootStateID;
	private List<ApprovalPhaseStateDto> listApprovalPhaseState;
	private GeneralDate date;
	private int rootType;
	private String employeeID;
}

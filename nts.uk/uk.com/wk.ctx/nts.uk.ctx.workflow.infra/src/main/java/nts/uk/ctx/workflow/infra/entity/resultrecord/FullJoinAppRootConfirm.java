package nts.uk.ctx.workflow.infra.entity.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class FullJoinAppRootConfirm {
	private String rootID;
	private String companyID;
	private String employeeID;
	private GeneralDate recordDate;
	private Integer rootType;
	private Integer phaseOrder;
	private Integer appPhaseAtr;
	private Integer frameOrder;
	private String approverID;
	private String representerID;
	private GeneralDate approvalDate;
}

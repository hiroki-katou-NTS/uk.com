package nts.uk.ctx.workflow.pub.hrapprovalstate.input;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class ApprovalStateHrImport {
	/**インスタンスID*/
	private String rootStateID;
	/**対象日*/
	private GeneralDate appDate;
	/**対象者ID*/
	private String employeeID;
	/**反映前flg*/
	@Setter
	private boolean reflectFlag;
	/**承認フェーズ*/
	private List<PhaseStateHrImport> lstPhaseState;
}

package nts.uk.ctx.workflow.pub.hrapprovalstate.input;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class FrameHrImport {
	/**承認枠No*/
	private int frameOrder;
	/**確定区分*/
	private int confirmAtr;
	/**対象日*/
	private GeneralDate appDate;
	/**承認情報*/
	private List<ApproverInfoHrImport> lstApproverInfo;
}

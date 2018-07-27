package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WorkplaceInfor;

/**
 * 
 * @author dat.lh
 *
 */
@Value
public class ApprovalStatusActivityData {
	private GeneralDate startDate;
	private GeneralDate endDate;
	private boolean isConfirmData;
	private List<WorkplaceInfor> listWorkplace;
	private List<String> listEmpCd;
}

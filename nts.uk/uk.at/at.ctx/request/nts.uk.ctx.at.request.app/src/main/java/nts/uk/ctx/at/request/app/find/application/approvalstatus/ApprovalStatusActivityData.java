package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WorkplaceInfor;

/**
 * 
 * @author dat.lh
 *
 */
@Value
public class ApprovalStatusActivityData {
	private String startDate;
	private String endDate;
	private boolean isConfirmData;
	private List<WorkplaceInfor> listWorkplaceInfor;
	private List<String> listEmpCd;
}

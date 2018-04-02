package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;

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
	private List<String> listWorkplaceId;
	private List<String> listEmpCd;
}

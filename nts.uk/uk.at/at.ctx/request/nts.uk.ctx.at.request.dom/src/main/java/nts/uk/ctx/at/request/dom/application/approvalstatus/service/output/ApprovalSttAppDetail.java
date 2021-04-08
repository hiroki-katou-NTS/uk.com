package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author Anh.BD
 *
 */
@Value
public class ApprovalSttAppDetail {
	ApplicationApprContent appContent;
//	AppDispName appDispName;
	List<ApproverOutput> listApprover;
	ApprovalSttDetailRecord approvalSttDetail;
	String relationshipName;
	Integer detailSet;
	
}

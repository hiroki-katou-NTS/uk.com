package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation;

import java.util.List;

import lombok.Value;

@Value
public class ApprovalAgencyInformationOutput {
	//REPRESENTER_SID
	List<ObjApproverRepresenter> listApproverAndRepresenterSID;
	
	List<String> listRepresenterSID;
	
	boolean flag;
}

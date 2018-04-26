package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;

@Value
public class ApplicationsListOutput {
	List<ApprovalSttAppDetail> approvalSttAppDetail; 
	HdAppSet lstHdAppSet;
}

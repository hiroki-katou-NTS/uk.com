package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppDetail;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;

@Value
public class ApplicationsListDto {
	List<ApplicationDetailDto> approvalSttAppDetail;
	HdAppSet lstHdAppSet;
}

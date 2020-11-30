package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;

@Value
public class ApplicationsListOutput {
	List<ApprovalSttAppDetail> approvalSttAppDetail; 
	Optional<HolidayApplicationSetting> lstHdAppSet;
	List<AppCompltLeaveSync> listSync;
	boolean displayPrePostFlg;
}

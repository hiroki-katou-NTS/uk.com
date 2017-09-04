package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.DecideAgencyExpired;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto.DetailedScreenPreBootModeOutput;

public interface PreBootModeService {
	
	public DetailedScreenPreBootModeOutput getDetailedScreenPreBootMode(Application applicationData, GeneralDate baseDate);
	/** Can be Approved
	 *14-2.詳細画面起動前モードの判断- 3 */
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status);
		
	public CanBeApprovedOutput approvedRemand(AppApprovalPhase appApprovalPhase);
	
	public CanBeApprovedOutput approvedApproved(AppApprovalPhase appApprovalPhase);
	
	public DecideAgencyExpired decideAgencyExpired(ApprovalFrame approvalFrame);
	/**Decide by Approver
	 * 14-2.詳細画面起動前モードの判断- 2*/
	public boolean decideByApprover(Application applicationData);
	
	public boolean checkFlag(Application applicationData, int displayOrder);
	
	
}
package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DecideAgencyExpiredOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
/**
 * 14-2.詳細画面起動前モードの判断
 * @author dudt
 *
 */
public interface BeforePreBootMode {
	/**
	 * 詳細画面起動前モードの判断
	 * @param applicationData
	 * @param baseDate
	 * @return
	 */
	public DetailedScreenPreBootModeOutput judgmentDetailScreenMode(Application applicationData, GeneralDate baseDate);
	/** Can be Approved
	 *14-2.詳細画面起動前モードの判断- 3 */
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status);
		
	public CanBeApprovedOutput approvedRemand(AppApprovalPhase appApprovalPhase);
	
	public CanBeApprovedOutput approvedApproved(AppApprovalPhase appApprovalPhase);
	
	public DecideAgencyExpiredOutput decideAgencyExpired(List<ApproveAccepted> lstApprover);
	/**Decide by Approver
	 * 2.承認者かの判断*/
	public boolean decideByApprover(Application applicationData);
	
	/** 14-2 3-5.承認中の承認フェーズの判断 */
	public boolean checkFlag(Application applicationData, int dispOrder);
	
}
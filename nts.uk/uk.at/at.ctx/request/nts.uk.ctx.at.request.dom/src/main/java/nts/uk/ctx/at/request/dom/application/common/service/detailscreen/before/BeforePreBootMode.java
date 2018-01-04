package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.CanBeApprovedOutput;
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
	public DetailedScreenPreBootModeOutput judgmentDetailScreenMode(String companyID, String employeeID, String appID, GeneralDate baseDate);
	/** Can be Approved
	 *3.承認できるかの判断
	 * */
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status);
	/**
	 * 2.承認状況の判断(差し戻し)
	 * @param appApprovalPhase
	 * @return
	 */
	public CanBeApprovedOutput approvedRemand(AppApprovalPhase appApprovalPhase);
	/**
	 * 1.承認状況の判断(承認済)
	 * @param appApprovalPhase
	 * @return
	 */
	public CanBeApprovedOutput approvedApproved(AppApprovalPhase appApprovalPhase);
	/**
	 * 3.承認状況の判断(否認)
	 * @param appApprovalPhase
	 * @return
	 */
	public CanBeApprovedOutput approvedDential(AppApprovalPhase appApprovalPhase);
	/**
	 * 4.承認状況の判断(未承認)
	 * @param appApprovalPhase
	 * @return
	 */
	public CanBeApprovedOutput approvedUnapproved(AppApprovalPhase appApprovalPhase);
	/**
	 * 6.代行者期限切れの判断
	 * @param lstApprover
	 * @return
	 */
	public boolean decideAgencyExpired(List<String> listApprovers);
	/**Decide by Approver
	 * 2.承認者かの判断*/
	public boolean decideByApprover(Application applicationData);
	
	/** 5.承認中の承認フェーズの判断*/
	public boolean checkApprovalProgressPhase(Application applicationData, int dispOrder);
	
}
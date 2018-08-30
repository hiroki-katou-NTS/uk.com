package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.resultrecord.AppPhaseConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppPhaseInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;

public interface AppRootConfirmService {
	
	/**
	 * (中間データ版)承認する
	 * @param approverID
	 * @param employeeID
	 * @param date
	 * @param appRootInstance
	 * @param appRootConfirm
	 */
	public void approve(String approverID, String employeeID, GeneralDate date, AppRootInstance appRootInstance, AppRootConfirm appRootConfirm);
	
	/**
	 * (中間データ版)解除する
	 * @param approverID
	 * @param employeeID
	 * @param date
	 * @param appRootInstance
	 * @param appRootConfirm
	 */
	public boolean cleanStatus(String approverID, String employeeID, GeneralDate date, AppRootInstance appRootInstance, AppRootConfirm appRootConfirm);
	
	/**
	 * 中間データから承認フェーズインスタンスに変換する
	 * @param appPhaseInstance
	 * @param appPhaseConfirm
	 * @return
	 */
	public ApprovalPhaseState convertPhaseInsToPhaseState(AppPhaseInstance appPhaseInstance, AppPhaseConfirm appPhaseConfirm);
	
	/**
	 * (中間データ版)承認フェーズ中間データ毎の承認者を取得する
	 * @param appPhaseInstance
	 * @return
	 */
	public List<String> getApproverFromPhase(AppPhaseInstance appPhaseInstance);
	
	/**
	 * 1.解除できるかチェックする
	 * @param approvalPhaseState
	 * @param employeeID
	 * @return
	 */
	public boolean canCancelCheck(ApprovalPhaseState approvalPhaseState, String employeeID);
	
}

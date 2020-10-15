package nts.uk.ctx.at.request.dom.application;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationApprovalService {
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.アルゴリズム.申請を削除する.申請を削除する
	 * @param appID
	 */
	public void delete(String appID);
	
	/**
	 * refactor 4
	 */
	public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState);
	
}

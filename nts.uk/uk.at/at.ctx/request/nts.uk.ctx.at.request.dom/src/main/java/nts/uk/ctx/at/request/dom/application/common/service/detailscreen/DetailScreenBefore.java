package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

public interface DetailScreenBefore {
	
	/**
	 * 15-1.詳細画面の承認コメントを取得する
	 * @param appID 申請ID
	 * @return
	 */
	public List<ApprovalPhaseStateImport_New> getApprovalDetail(String appID);
	
}

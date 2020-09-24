package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

@AllArgsConstructor
@Getter
public class DetailScreenApprovalData {
	
	/**
	 * 承認ルートインスタンス
	 */
	List<ApprovalPhaseStateImport_New> approvalLst;
	
	/**
	 * 承認コメント
	 */
	String authorComment;
	
}

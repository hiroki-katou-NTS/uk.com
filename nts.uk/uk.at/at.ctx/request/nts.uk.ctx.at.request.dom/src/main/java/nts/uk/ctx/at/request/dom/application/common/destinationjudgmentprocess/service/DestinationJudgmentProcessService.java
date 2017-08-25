package nts.uk.ctx.at.request.dom.application.common.destinationjudgmentprocess.service;

import java.util.List;
/**
 * 3-2.送信先の判断処理
 */
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;

public interface DestinationJudgmentProcessService {
	public List<String> getDestinationJudgmentProcessService(List<ApprovalFrame> listApprovalFrame);
}

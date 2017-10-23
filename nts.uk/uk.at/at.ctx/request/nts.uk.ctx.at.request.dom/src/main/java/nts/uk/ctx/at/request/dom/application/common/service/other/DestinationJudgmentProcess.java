package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;


/**
 * 3-2.送信先の判断処理
 * @author tutk
 *
 */
public interface DestinationJudgmentProcess {
	public List<String> getDestinationJudgmentProcessService(List<ApproverRepresenterImport> listApproverAndRepresenterSID);
}

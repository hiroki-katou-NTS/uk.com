package nts.uk.ctx.at.request.dom.application.common.destinationjudgmentprocess.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.ObjApproverRepresenter;


/**
 * 3-2.送信先の判断処理
 * @author tutk
 *
 */
public interface DestinationJudgmentProcessService {
	public List<String> getDestinationJudgmentProcessService(List<ObjApproverRepresenter> listApproverAndRepresenterSID);
}

package nts.uk.ctx.at.request.dom.application.common.destinationjudgmentprocess.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.shr.com.context.AppContexts;
/**
 * 3-2.送信先の判断処理
 * @author tutk
 *
 */
@Stateless
public class DestinationJudgmentProcessDefault implements DestinationJudgmentProcessService {

	@Override
	public List<String> getDestinationJudgmentProcessService(List<ApprovalFrame> listApprovalFrame) {
		List<String> listDestination = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		
		
		return null;
	}

}

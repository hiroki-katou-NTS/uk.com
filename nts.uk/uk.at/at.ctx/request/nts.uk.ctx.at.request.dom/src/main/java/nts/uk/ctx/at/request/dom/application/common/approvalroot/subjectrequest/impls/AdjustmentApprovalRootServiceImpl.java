package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.AdjustmentApprovalRootService;

/**
 * 2.承認ルートを整理する
 * 
 * @author vunv
 *
 */
@Stateless
public class AdjustmentApprovalRootServiceImpl implements AdjustmentApprovalRootService {

	@Override
	public void getApprovalRootOfSubjectRequest(String cid, String sid, Date standardDate, List<String> branchIds) {
		// TODO Auto-generated method stub

	}

}

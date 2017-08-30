package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.GetSubjectOfJobTitleService;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto.ApproverInfo;

/**
 * 3.職位から承認者へ変換する
 * 
 * @author vunv
 *
 */
@Stateless
public class GetSubjectOfJobTitleToServiceImpl implements GetSubjectOfJobTitleService {

	@Inject
	private EmployeeAdaptor emloyeePub;

	@Override
	public List<ApproverInfo> getByWkp(String cid, String wkpId, GeneralDate baseDate, String jobTitleId) {
		// TODO Auto-generated method stub
		return null;
	}

}

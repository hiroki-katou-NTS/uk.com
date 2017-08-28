package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootAdaptorDto;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalRootAdaptorImpl implements ApprovalRootAdaptor
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Override
	public List<ApprovalRootAdaptorDto> findByBaseDate(String cid, String sid, Date standardDate, String appType) {
		return this.approvalRootPub.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new ApprovalRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<ApprovalRootAdaptorDto> findByBaseDateOfCommon(String cid, String sid, Date standardDate) {
		return this.approvalRootPub.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> new ApprovalRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}
}


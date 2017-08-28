package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootDto;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalPubImpl implements ApprovalRootPub{
	
	@Inject
	private PersonApprovalRootRepository appRootRepository;
	
	@Override
	public List<ApprovalRootDto> findByBaseDate(String cid, String sid, Date standardDate, String appType) {
		return this.appRootRepository.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new ApprovalRootDto(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
}

package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootAdaptorDto;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalRootAdaptorImpl implements ApprovalRootAdaptor
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Override
	public List<PersonAppRootAdaptorDto> findByBaseDate(String cid, String sid, Date standardDate, int appType) {
		return this.approvalRootPub.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new PersonAppRootAdaptorDto(
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
	public List<PersonAppRootAdaptorDto> findByBaseDateOfCommon(String cid, String sid, Date standardDate) {
		return this.approvalRootPub.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> new PersonAppRootAdaptorDto(
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
	public List<WkpAppRootAdaptorDto> findWkpByBaseDate(String cid, String workPlaceId, Date baseDate, int appType) {
		return this.approvalRootPub.findWkpByBaseDate(cid, workPlaceId, baseDate, appType).stream()
				.map(x -> new WkpAppRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
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
	public List<WkpAppRootAdaptorDto> findWkpByBaseDateOfCommon(String cid, String workPlaceId, Date baseDate) {
		return this.approvalRootPub.findWkpByBaseDateOfCommon(cid, workPlaceId, baseDate).stream()
				.map(x -> new WkpAppRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
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
	public List<CompanyAppRootAdaptorDto> findCompanyByBaseDate(String cid, Date baseDate, int appType) {
		return this.approvalRootPub.findCompanyByBaseDate(cid, baseDate, appType).stream()
				.map(x -> new CompanyAppRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
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
	public List<CompanyAppRootAdaptorDto> findCompanyByBaseDateOfCommon(String cid, Date baseDate) {
		return this.approvalRootPub.findCompanyByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> new CompanyAppRootAdaptorDto(
						x.getCompanyId(),
						x.getApprovalId(),
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


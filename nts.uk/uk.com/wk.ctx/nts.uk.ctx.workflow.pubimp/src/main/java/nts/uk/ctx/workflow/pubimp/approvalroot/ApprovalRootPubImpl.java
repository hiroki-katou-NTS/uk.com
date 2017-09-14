package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ComApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.PersonApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.WkpApprovalRootExport;

@Stateless
public class ApprovalRootPubImpl implements ApprovalRootPub{
	
	@Inject
	private PersonApprovalRootRepository personAppRootRepository;
	
	@Inject 
	private WorkplaceApprovalRootRepository wkpAppRootRepository;
	
	@Inject 
	private CompanyApprovalRootRepository companyAppRootRepository;
	
	@Inject
	private ApprovalPhaseRepository appPhaseRepository;
	
	@Override
	public List<PersonApprovalRootExport> findByBaseDate(String cid, String sid, GeneralDate standardDate, int appType) {
		return this.personAppRootRepository.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> this.toPersonAppRootExport(x)).collect(Collectors.toList());
	}

	@Override
	public List<PersonApprovalRootExport> findByBaseDateOfCommon(String cid, String sid, GeneralDate standardDate) {
		return this.personAppRootRepository.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> this.toPersonAppRootExport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<WkpApprovalRootExport> findWkpByBaseDate(String cid, String workplaceId, GeneralDate baseDate, int appType) {
		return this.wkpAppRootRepository.findByBaseDate(cid, workplaceId, baseDate, appType).stream()
				.map(x -> this.toWkpAppRootExport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<WkpApprovalRootExport> findWkpByBaseDateOfCommon(String cid, String workplaceId, GeneralDate baseDate) {
		return this.wkpAppRootRepository.findByBaseDateOfCommon(cid, workplaceId, baseDate).stream()
				.map(x -> this.toWkpAppRootExport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<ComApprovalRootExport> findCompanyByBaseDate(String cid, GeneralDate baseDate, int appType) {
		return this.companyAppRootRepository.findByBaseDate(cid, baseDate, appType).stream()
				.map(x -> this.toComAppRootExport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<ComApprovalRootExport> findCompanyByBaseDateOfCommon(String cid, GeneralDate baseDate) {
		return this.companyAppRootRepository.findByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> this.toComAppRootExport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<ApprovalPhaseExport> findApprovalPhaseByBranchId(String cid, String branchId) {
		return this.appPhaseRepository.getAllIncludeApprovers(cid, branchId).stream()
				.map(x -> new ApprovalPhaseExport(
						x.getCompanyId(),
						x.getBranchId(),
						x.getApprovalPhaseId(),
						x.getApprovalForm().value,
						x.getBrowsingPhase(),
						x.getOrderNumber(),
						CollectionUtil.isEmpty(x.getApprovers())? null: x.getApprovers().stream().map(a -> new ApproverExport(
								a.getCompanyId(), 
								a.getApprovalPhaseId(), 
								a.getApproverId(), 
								a.getJobTitleId(), 
								a.getEmployeeId(), 
								a.getOrderNumber(), 
								a.getApprovalAtr().value, 
								a.getConfirmPerson().value))
						.collect(Collectors.toList())
			    )).collect(Collectors.toList());
	}
	
	/**
	 * convert to Person
	 * 
	 * @param root
	 * @return
	 */
	private PersonApprovalRootExport toPersonAppRootExport(PersonApprovalRoot root) {
		return new PersonApprovalRootExport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getEmployeeId(),
				root.getHistoryId(),
				root.getApplicationType()==null?null:root.getApplicationType().value,
				root.getPeriod().getStartDate(),
				root.getPeriod().getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType() == null?null:root.getConfirmationRootType().value,
				root.getEmploymentRootAtr().value
	    );
	}
	
	/**
	 * convert to Workplace
	 * 
	 * @param root
	 * @return
	 */
	private WkpApprovalRootExport toWkpAppRootExport(WorkplaceApprovalRoot root) {
		return new WkpApprovalRootExport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getWorkplaceId(),
				root.getHistoryId(),
				root.getApplicationType()==null?null:root.getApplicationType().value,
				root.getPeriod().getStartDate(),
				root.getPeriod().getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType() == null?null:root.getConfirmationRootType().value,
				root.getEmploymentRootAtr().value
	    );
	}
	
	/**
	 * convert to Company
	 * 
	 * @param root
	 * @return
	 */
	private ComApprovalRootExport toComAppRootExport(CompanyApprovalRoot root) {
		return new ComApprovalRootExport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getHistoryId(),
				root.getApplicationType()==null?null:root.getApplicationType().value,
				root.getPeriod().getStartDate(),
				root.getPeriod().getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType() == null?null:root.getConfirmationRootType().value,
				root.getEmploymentRootAtr().value
	    );
	}
}

package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ComApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpApprovalRootImport;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ComApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.PersonApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.WkpApprovalRootExport;

@Stateless
public class ApprovalRootAdapterImpl implements ApprovalRootAdapter
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Override
	public List<PersonApprovalRootImport> findByBaseDate(String cid, String sid, GeneralDate baseDate, int appType) {
		return this.approvalRootPub.findByBaseDate(cid, sid, baseDate, appType).stream()
				.map(x -> this.toPersonAppRootImport(x)).collect(Collectors.toList());
	}
	
	@Override
	public List<PersonApprovalRootImport> findByBaseDateOfCommon(String cid, String sid, GeneralDate baseDate) {
		return this.approvalRootPub.findByBaseDateOfCommon(cid, sid, baseDate).stream()
				.map(x -> this.toPersonAppRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<WkpApprovalRootImport> findWkpByBaseDate(String cid, String workPlaceId, GeneralDate baseDate, int appType) {
		return this.approvalRootPub.findWkpByBaseDate(cid, workPlaceId, baseDate, appType).stream()
				.map(x -> this.toWkpAppRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<WkpApprovalRootImport> findWkpByBaseDateOfCommon(String cid, String workPlaceId, GeneralDate baseDate) {
		return this.approvalRootPub.findWkpByBaseDateOfCommon(cid, workPlaceId, baseDate).stream()
				.map(x -> this.toWkpAppRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<ComApprovalRootImport> findCompanyByBaseDate(String cid, GeneralDate baseDate, int appType) {
		return this.approvalRootPub.findCompanyByBaseDate(cid, baseDate, appType).stream()
				.map(x -> this.toComAppRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<ComApprovalRootImport> findCompanyByBaseDateOfCommon(String cid, GeneralDate baseDate) {
		return this.approvalRootPub.findCompanyByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> this.toComAppRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPhaseImport> findApprovalPhaseByBranchId(String cid, String branchId) {
		return this.approvalRootPub.findApprovalPhaseByBranchId(cid, branchId).stream()
				.map(x -> new ApprovalPhaseImport(
						x.getCompanyId(),
						x.getBranchId(),
						x.getApprovalPhaseId(),
						x.getApprovalForm(),
						x.getBrowsingPhase(),
						x.getOrderNumber(),
						CollectionUtil.isEmpty(x.getApproverDtos())? null: x.getApproverDtos().stream().map(a -> new ApproverImport(
								a.getCompanyId(), 
								a.getApprovalPhaseId(), 
								a.getApproverId(), 
								a.getJobTitleId(), 
								a.getEmployeeId(), 
								a.getOrderNumber(), 
								a.getApprovalAtr(), 
								a.getConfirmPerson()))
						.collect(Collectors.toList())
			    )).collect(Collectors.toList());
	}
	
	/**
	 * convert to Person
	 * 
	 * @param root
	 * @return
	 */
	private PersonApprovalRootImport toPersonAppRootImport(PersonApprovalRootExport root) {
		return new PersonApprovalRootImport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getEmployeeId(),
				root.getHistoryId(),
				root.getApplicationType(),
				root.getStartDate(),
				root.getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType(),
				root.getEmploymentRootAtr()
	    );
	}
	
	/**
	 * convert to Workplace
	 * 
	 * @param root
	 * @return
	 */
	private WkpApprovalRootImport toWkpAppRootImport(WkpApprovalRootExport root) {
		return new WkpApprovalRootImport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getWorkplaceId(),
				root.getHistoryId(),
				root.getApplicationType(),
				root.getStartDate(),
				root.getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType(),
				root.getEmploymentRootAtr()
	    );
	}
	
	/**
	 * convert to Company
	 * 
	 * @param root
	 * @return
	 */
	private ComApprovalRootImport toComAppRootImport(ComApprovalRootExport root) {
		return new ComApprovalRootImport(
				root.getCompanyId(),
				root.getApprovalId(),
				root.getHistoryId(),
				root.getApplicationType(),
				root.getStartDate(),
				root.getEndDate(),
				root.getBranchId(),
				root.getAnyItemApplicationId(),
				root.getConfirmationRootType(),
				root.getEmploymentRootAtr()
	    );
	}
}


package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverInfoExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApprovalRootAdapterImpl implements ApprovalRootAdapter
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	@Inject 
	private SyJobTitlePub syJobTitlePub;

	@Override
	public List<ApprovalRootImport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr,
			int appType, GeneralDate standardDate) {
		List<ApprovalRootExport> approvalRootData = this.approvalRootPub.getApprovalRootOfSubjectRequest(cid, sid, employmentRootAtr, appType, standardDate);
		if(CollectionUtil.isEmpty(approvalRootData)){
			return Collections.emptyList();
		}
		 
		return approvalRootData.stream().map(x -> this.convertApprovalRootImport(x)).collect(Collectors.toList());
	}
	/**
	 * convert ApprovalRootExport to ApprovalRootImport
	 * @param export
	 * @return
	 */
	private ApprovalRootImport convertApprovalRootImport(ApprovalRootExport export) {
		ApprovalRootImport temp =  new ApprovalRootImport(
				export.getCompanyId(),
				export.getWorkplaceId(),
				export.getApprovalId(),
				export.getEmployeeId(),
				export.getHistoryId(),
				export.getStartDate(),
				export.getEndDate(),
				export.getBranchId(),
				export.getAnyItemApplicationId()
				
				);
		temp.addBeforeApprovers(
				export.getBeforeApprovers().stream()
				.map(x -> this.convertApprovalPhaseImport(x)).collect(Collectors.toList())
				);
		temp.addAfterApprovers(
				export.getAfterApprovers().stream()
				.map(x -> this.convertApprovalPhaseImport(x)).collect(Collectors.toList())
				);
		return temp;
		
	}
	/**
	 * convert ApprovalRootImport to ApprovalRootExport
	 * @param export
	 * @return
	 */
	private ApprovalRootExport convertApprovalRootExport(ApprovalRootImport approvalRootImport) {
		return new ApprovalRootExport(
				approvalRootImport.getCompanyId(),
				approvalRootImport.getWorkplaceId(),
				approvalRootImport.getApprovalId(),
				approvalRootImport.getEmployeeId(),
				approvalRootImport.getHistoryId(),
				approvalRootImport.getStartDate(),
				approvalRootImport.getEndDate(),
				approvalRootImport.getBranchId(),
				approvalRootImport.getAnyItemApplicationId()
				);
		
	}
	/**
	 * covert ApprovalPhaseExport -> ApprovalPhaseImport
	 * @param approvalPhaseExport
	 * @return
	 */
	private ApprovalPhaseImport convertApprovalPhaseImport(ApprovalPhaseExport  approvalPhaseExport) {
		ApprovalPhaseImport temp = new  ApprovalPhaseImport(
				approvalPhaseExport.getCompanyId(),
				approvalPhaseExport.getBranchId(),
				approvalPhaseExport.getApprovalPhaseId(),
				approvalPhaseExport.getApprovalForm(),
				approvalPhaseExport.getBrowsingPhase(),
				approvalPhaseExport.getOrderNumber()
				);
		temp.addApproverList(approvalPhaseExport.getApprovers().stream().map(x -> this.convertApproverInfoImport(x)).collect(Collectors.toList()));
		return temp;
		
	}
	
	/**
	 * covert ApprovalPhaseImport -> ApprovalPhaseExport
	 * @param approvalPhaseExport
	 * @return
	 */
	private ApprovalPhaseExport convertApprovalPhaseExport(ApprovalPhaseImport  approvalPhaseImport) {
		return new  ApprovalPhaseExport(
				approvalPhaseImport.getCompanyId(),
				approvalPhaseImport.getBranchId(),
				approvalPhaseImport.getApprovalPhaseId(),
				approvalPhaseImport.getApprovalForm(),
				approvalPhaseImport.getBrowsingPhase(),
				approvalPhaseImport.getOrderNumber()
				);
		
	}
	
	private ApproverInfoImport convertApproverInfoImport(ApproverInfoExport approverInfoExport) {
		String companyID = AppContexts.user().companyId();
		ApproverInfoImport temp = new  ApproverInfoImport(
				approverInfoExport.getJobId(), // jobID 
				approverInfoExport.getSid(),
				approverInfoExport.getApprovalPhaseId(),
				approverInfoExport.isConfirmPerson(),
				approverInfoExport.getOrderNumber(),
				approverInfoExport.getApprovalAtr() // int approvalAtr  = 0,1
				);
		if(approverInfoExport.getApprovalAtr() ==0) {//if pesson
			temp.addEmployeeName(employeeAdapter.getEmployeeName(approverInfoExport.getSid()));
		}
		if(approverInfoExport.getApprovalAtr() ==1) {
			temp.addEmployeeName(syJobTitlePub.findByJobId(companyID, approverInfoExport.getJobId(), GeneralDate.today()).get().getPositionName());

		}
		return temp;
		
	}
	
	

	@Override
	public List<ApprovalRootImport> adjustmentData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalRootImport> appDatas) {
		appDatas.stream().map(x -> this.convertApprovalRootExport(x)).collect(Collectors.toList());
		return this.approvalRootPub.adjustmentData(cid, sid, baseDate, 
					appDatas.stream().map(x -> this.convertApprovalRootExport(x)).collect(Collectors.toList())
				).stream()
				.map(x -> this.convertApprovalRootImport(x)).collect(Collectors.toList());
	}

	@Override
	public ErrorFlagImport checkError(List<ApprovalPhaseImport> beforeDatas, List<ApprovalPhaseImport> afterDatas) {
		return  EnumAdaptor.valueOf(
				this.approvalRootPub.checkError(
				//list beforeDatas sau khi convert sang Export
				beforeDatas.stream().map(x -> this.convertApprovalPhaseExport(x)).collect(Collectors.toList()),
				//list afterDatas sau khi convert sang Export
				afterDatas.stream().map(x -> this.convertApprovalPhaseExport(x)).collect(Collectors.toList())).value
				, ErrorFlagImport.class);
	}

	@Override
	public List<ApproverInfoImport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		return  this.approvalRootPub.convertToApprover(cid, sid, baseDate, jobTitleId).stream()
				.map(x -> this.convertApproverInfoImport(x)).collect(Collectors.toList());
	}
	
	
}


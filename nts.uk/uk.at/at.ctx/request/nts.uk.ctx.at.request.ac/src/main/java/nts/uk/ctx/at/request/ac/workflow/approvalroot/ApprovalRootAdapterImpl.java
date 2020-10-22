package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.ConcurrentEmployeeRequest;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.RepresenterInformationImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverInfoExport;
//import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApprovalRootAdapterImpl implements ApprovalRootAdapter
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject 
	private SyJobTitlePub syJobTitlePub;
	
	@Inject
	private AgentAdapter agentAdapter;
	
//	@Inject
//	private ApprovalRootStatePub approvalStatePub;

	@Override
	public List<ApprovalRootImport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr,int appType,
			GeneralDate standardDate, int sysAtr) {
		List<ApprovalRootExport> approvalRootData = this.approvalRootPub.getApprovalRootOfSubjectRequest(cid, sid, employmentRootAtr, appType, standardDate, sysAtr);
		if(CollectionUtil.isEmpty(approvalRootData)){
			return Collections.emptyList();
		}
		 
		List<ApprovalRootImport> approvalRootResult = approvalRootData.stream().map(x -> this.convertApprovalRootImport(x)).collect(Collectors.toList());
		List<String> approverSIDList = new ArrayList<>();
		approvalRootResult.stream().forEach(approvalRootImport -> {
			approvalRootImport.getBeforeApprovers().stream().forEach(approvalPhaseImport -> {
				approvalPhaseImport.getApprovers().stream().forEach(approverInfoImport -> {
					if(approverInfoImport.getSid() != null) {
						approverSIDList.add(approverInfoImport.getSid());
					}else if(approverInfoImport.getJobGCD() != null) {
						List<ConcurrentEmployeeRequest> lstEmployeeByJob = employeeAdapter.getConcurrentEmployee(cid,approverInfoImport.getJobGCD(), standardDate);
						if(!lstEmployeeByJob.isEmpty()) {
							for(ConcurrentEmployeeRequest emp : lstEmployeeByJob) {
								approverInfoImport.getApproverSIDList().add(emp.getEmployeeId());
								approverInfoImport.getApproverNameList().add(emp.getPersonName());
								approverSIDList.add(emp.getEmployeeId());
							}
						}
					}
				});
			});
		});
		
		AgentPubImport agentPubImport = agentAdapter.getApprovalAgencyInformation(cid, approverSIDList);
		List<ApproverRepresenterImport> representerList = agentPubImport.getListApproverAndRepresenterSID();
		approvalRootResult.stream().forEach(approvalRootImport -> {
			approvalRootImport.getBeforeApprovers().stream().forEach(approvalPhaseImport -> {
				approvalPhaseImport.getApprovers().stream().forEach(approverInfoImport -> {
					if(approverInfoImport.getSid() != null) {
						representerList.stream().filter(x -> x.getApprover().equals(approverInfoImport.getSid())).findAny()
						.map(y -> {
							if(!(y.getRepresenter().getValue().equals(RepresenterInformationImport.None_Information) ||
									y.getRepresenter().getValue().equals(RepresenterInformationImport.Path_Information))){
								approverInfoImport.addRepresenterSID(y.getRepresenter().getValue());
								approverInfoImport.addRepresenterName(employeeAdapter.getEmployeeName(approverInfoImport.getRepresenterSID()));
							}
							return null;
						}).orElse(null);
					}else if(approverInfoImport.getJobGCD() != null) {
						approverInfoImport.getApproverSIDList().forEach(item -> {
							representerList.stream().filter(x -> x.getApprover().equals(item)).findAny()
							.map(y -> {
								if(!(y.getRepresenter().getValue().equals(RepresenterInformationImport.None_Information) ||
										y.getRepresenter().getValue().equals(RepresenterInformationImport.Path_Information))){
									approverInfoImport.getRepresenterSIDList().add(y.getRepresenter().getValue());
									approverInfoImport.getRepresenterNameList().add(employeeAdapter.getEmployeeName(approverInfoImport.getRepresenterSID()));
								}
								return null;
							}).orElse(null);
						});
					}
				});
			});
		});
		approvalRootResult.stream().forEach(x -> {
			x.getBeforeApprovers().stream().forEach(y ->{
				Collections.sort(y.getApprovers(), Comparator.comparing(ApproverInfoImport :: getApproverOrder));
			});
			Collections.sort(x.getBeforeApprovers(), Comparator.comparing(ApprovalPhaseImport::getPhaseOrder));
		});
		return approvalRootResult;
	}
	/**
	 * convert ApprovalRootExport to ApprovalRootImport
	 * @param export
	 * @return
	 */
	private ApprovalRootImport convertApprovalRootImport(ApprovalRootExport export) {
		List<ApprovalPhaseImport> beforeApprovers = new ArrayList<>();
		export.getBeforeApprovers().stream().forEach(x ->{
			ApprovalPhaseImport beforeApprover = new ApprovalPhaseImport(x.getApprovalId(), 
					x.getPhaseOrder(), x.getApprovalForm(), x.getBrowsingPhase(), x.getApprovalAtr());
			beforeApprovers.add(beforeApprover);
		});
		List<ApprovalPhaseImport> afterApprovers = new ArrayList<>();
		export.getAfterApprovers().stream().forEach(x -> {
			ApprovalPhaseImport afterApprover = new ApprovalPhaseImport(x.getApprovalId(),
					x.getPhaseOrder(), x.getApprovalForm(), x.getBrowsingPhase(), x.getApprovalAtr());
			afterApprovers.add(afterApprover);
		});
		ApprovalRootImport temp = new ApprovalRootImport(export.getCompanyId(),
				export.getWorkplaceId(),
				export.getApprovalId(),
				export.getEmployeeId(),
				export.getHistoryId(),
				export.getApplicationType(),
				export.getStartDate(),
				export.getEndDate(),
				// export.getBranchId(),
				// export.getAnyItemApplicationId(), 
				export.getConfirmationRootType(),
				export.getEmploymentRootAtr(), 
				beforeApprovers, 
				afterApprovers, 
				EnumAdaptor.valueOf(export.getErrorFlag() == null ? 0 : export.getErrorFlag().value, ErrorFlagImport.class));
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
	 * covert ApprovalPhaseExport -> ApprovalPhaseImport
	 * @param phase
	 * @return
	 */
	private ApprovalPhaseImport convertApprovalPhaseImport(ApprovalPhaseExport  phase) {
		ApprovalPhaseImport temp = new  ApprovalPhaseImport(
				phase.getApprovalId(),
				phase.getPhaseOrder(),
				phase.getApprovalForm(),
				phase.getBrowsingPhase(),
				phase.getApprovalAtr()
				);
		temp.addApproverList(phase.getApprovers().stream()
				.map(x -> this.convertApproverInfoImport(phase.getApprovalAtr(), x))
				.collect(Collectors.toList()));
		return temp;
		
	}
	
	
	private ApproverInfoImport convertApproverInfoImport(int approvalAtr, ApproverInfoExport approverInfoExport) {
		String companyID = AppContexts.user().companyId();
		ApproverInfoImport temp = new  ApproverInfoImport(
				approverInfoExport.getJobGCD(), // jobID 
				approverInfoExport.getSid(),
				approverInfoExport.getIsConfirmPerson(),
				approverInfoExport.getApproverOrder()
				);
		if(approvalAtr ==0) {//if pesson
			temp.addEmployeeName(employeeAdapter.getEmployeeName(approverInfoExport.getSid()));
		}
		if(approvalAtr ==1) {
			temp.addEmployeeName(syJobTitlePub.findByJobId(companyID, approverInfoExport.getJobGCD(), GeneralDate.today()).get().getJobTitleName());

		}
		return temp;
		
	}

//	@Override
//	public List<ApproverInfoImport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
//		return  this.approvalRootPub.convertToApprover(cid, sid, baseDate, jobTitleId).stream()
//				.map(x -> this.convertApproverInfoImport(x)).collect(Collectors.toList());
//	}
	@Override
	public Integer getCurrentApprovePhase(String rootStateID, Integer rootType) {
		return approvalRootPub.getCurrentApprovePhase(rootStateID, rootType);
	}	
}


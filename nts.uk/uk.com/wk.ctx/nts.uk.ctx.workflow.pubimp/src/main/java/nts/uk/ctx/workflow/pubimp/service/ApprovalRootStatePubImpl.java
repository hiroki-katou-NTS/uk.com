package nts.uk.ctx.workflow.pubimp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateService;
import nts.uk.ctx.workflow.dom.service.ApproveService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalFrameExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverStateExport;
import nts.uk.ctx.workflow.pub.service.export.ErrorFlagExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalRootStatePubImpl implements ApprovalRootStatePub {
	
	@Inject
	private ApprovalRootStateService approvalRootStateService;
	
	@Inject
	private CollectApprovalRootService collectApprovalRootService;
	
	@Inject
	private PersonAdapter personAdapter;
	
	@Inject
	private AgentRepository agentRepository;
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private ApproveService approveService;

	@Override
	public ApprovalRootContentExport getApprovalRoot(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID, Boolean isCreate) {
		ApprovalRootContentOutput approvalRootContentOutput = null;
		if(isCreate.equals(Boolean.TRUE)){
			approvalRootContentOutput = collectApprovalRootService.getApprovalRootOfSubjectRequest(
					companyID, 
					employeeID, 
					EmploymentRootAtr.APPLICATION, 
					EnumAdaptor.valueOf(appTypeValue, ApplicationType.class) , 
					date);
		} else {
			ApprovalRootState approvalRootState = approvalRootStateRepository.findEmploymentApp(appID).orElseThrow(()->
					new RuntimeException("data WWFDT_APPROVAL_ROOT_STATE error: ID ="+appID)
			);
			approvalRootContentOutput = new ApprovalRootContentOutput(approvalRootState, ErrorFlag.NO_ERROR);
		}
		return new ApprovalRootContentExport(
				new ApprovalRootStateExport(
					approvalRootContentOutput.getApprovalRootState().getListApprovalPhaseState()
					.stream().map(x -> {
						return new ApprovalPhaseStateExport(
								x.getPhaseOrder(), 
								x.getApprovalAtr().name, 
								x.getListApprovalFrame().stream().map(y -> {
									return new ApprovalFrameExport(
											y.getPhaseOrder(), 
											y.getFrameOrder(), 
											y.getApprovalAtr().name, 
											y.getListApproverState().stream().map(z -> { 
												String approverName = personAdapter.getPersonInfo(z.getApproverID()).getEmployeeName();
												String representerName = "";
												List<Agent> listAgent = agentRepository.find(companyID, Arrays.asList(employeeID), date);
												if(!CollectionUtil.isEmpty(listAgent)){
													representerName = personAdapter.getPersonInfo(listAgent.get(0).getAgentSid1()).getEmployeeName();
												}
												return new ApproverStateExport(approverName, representerName);
											}).collect(Collectors.toList()), 
											Strings.isBlank(y.getApproverID()) ? "" : personAdapter.getPersonInfo(y.getApproverID()).getEmployeeName(), 
											Strings.isBlank(y.getRepresenterID()) ? "" : personAdapter.getPersonInfo(y.getRepresenterID()).getEmployeeName(),
											y.getApprovalReason());
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList())
				), 
				EnumAdaptor.valueOf(approvalRootContentOutput.getErrorFlag().value, ErrorFlagExport.class));
		
		
	}
	
	@Override
	public void insertAppRootType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date,
			String historyID, String appID) {
		approvalRootStateService.insertAppRootType(companyID, employeeID, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), date, historyID, appID);
	}

	@Override
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue,
			GeneralDate appDate) {
		return approveService.getNextApprovalPhaseStateMailList(
				companyID, 
				rootStateID, 
				approvalPhaseStateNumber, 
				isCreate, 
				employeeID, 
				EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
				appDate);
	}

	@Override
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate) {
		return approveService.doApprove(companyID, rootStateID, employeeID, isCreate, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appDate);
	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate) {
		return approveService.isApproveAllComplete(companyID, rootStateID, employeeID, isCreate, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appDate);
	}
}

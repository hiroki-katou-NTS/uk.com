package nts.uk.ctx.workflow.pubimp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateService;
import nts.uk.ctx.workflow.dom.service.ApproveService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalAgentInforService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.CollectMailNotifierService;
import nts.uk.ctx.workflow.dom.service.DenyService;
import nts.uk.ctx.workflow.dom.service.JudgmentApprovalStatusService;
import nts.uk.ctx.workflow.dom.service.ReleaseAllAtOnceService;
import nts.uk.ctx.workflow.dom.service.ReleaseService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverApprovedOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.agent.RepresenterInformationExport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalBehaviorAtrExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalFrameExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverApprovedExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverPersonExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverWithFlagExport;
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

	@Inject
	private ReleaseAllAtOnceService releaseAllAtOnceService;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Inject
	private CollectMailNotifierService collectMailNotifierService;
	
	@Inject
	private ReleaseService releaseService;
	
	@Inject
	private DenyService denyService;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
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
					.stream()
					.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
					.map(x -> {
						return new ApprovalPhaseStateExport(
								x.getPhaseOrder(), 
								x.getApprovalAtr().name, 
								x.getListApprovalFrame()
								.stream()
								.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
								.map(y -> {
									return new ApprovalFrameExport(
											y.getPhaseOrder(), 
											y.getFrameOrder(), 
											y.getApprovalAtr().name, 
											y.getListApproverState().stream().map(z -> { 
												String approverName = personAdapter.getPersonInfo(z.getApproverID()).getEmployeeName();
												String representerName = "";
												ApprovalRepresenterOutput approvalRepresenterOutput = 
														collectApprovalAgentInforService.getApprovalAgentInfor(companyID, Arrays.asList(z.getApproverID()));
												if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.FALSE)){
													if(!CollectionUtil.isEmpty(approvalRepresenterOutput.getListAgent())){
														representerName = personAdapter.getPersonInfo(approvalRepresenterOutput.getListAgent().get(0)).getEmployeeName();
													}
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
	public void insertAppRootType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID) {
		approvalRootStateService.insertAppRootType(companyID, employeeID, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), date, appID);
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
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate, String memo) {
		return approveService.doApprove(companyID, rootStateID, employeeID, isCreate, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appDate, memo);
	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate) {
		return approveService.isApproveAllComplete(companyID, rootStateID, employeeID, isCreate, EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appDate);
	}

	@Override
	public void doReleaseAllAtOnce(String companyID, String rootStateID) {
		releaseAllAtOnceService.doReleaseAllAtOnce(companyID, rootStateID);
	}

	@Override
	public ApproverApprovedExport getApproverApproved(String rootStateID) {
		ApproverApprovedOutput approverApprovedOutput = releaseAllAtOnceService.getApproverApproved(rootStateID);
		return new ApproverApprovedExport(
				approverApprovedOutput.getListApproverWithFlagOutput().stream()
					.map(x -> new ApproverWithFlagExport(x.getEmployeeID(), x.getAgentFlag())).collect(Collectors.toList()), 
				approverApprovedOutput.getListApprover());
	}

	@Override
	public AgentPubExport getApprovalAgentInfor(String companyID, List<String> listApprover) {
		ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
		return new AgentPubExport(
				approvalRepresenterOutput.getListApprovalAgentInfor().stream()
					.map(x -> new ApproverRepresenterExport(x.getApprover(), new RepresenterInformationExport(x.getRepresenter().getValue())))
					.collect(Collectors.toList()), 
				approvalRepresenterOutput.getListAgent(), 
				approvalRepresenterOutput.getAllPathSetFlag());
	}

	@Override
	public List<String> getMailNotifierList(String companyID, String rootStateID) {
		return collectMailNotifierService.getMailNotifierList(companyID, rootStateID);
	}

	@Override
	public void deleteApprovalRootState(String rootStateID) {
		approvalRootStateRepository.delete(rootStateID);
	}

	@Override
	public Boolean doRelease(String companyID, String rootStateID, String employeeID) {
		return releaseService.doRelease(companyID, rootStateID, employeeID);
	}

	@Override
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo) {
		return denyService.doDeny(companyID, rootStateID, employeeID, memo);
	}

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID) {
		return judgmentApprovalStatusService.judgmentTargetPersonIsApprover(companyID, rootStateID, employeeID);
	}

	@Override
	public ApproverPersonExport judgmentTargetPersonCanApprove(String companyID, String rootStateID,
			String employeeID) {
		ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID);
		return new ApproverPersonExport(
				approverPersonOutput.getAuthorFlag(), 
				EnumAdaptor.valueOf(approverPersonOutput.getApprovalAtr().value, ApprovalBehaviorAtrExport.class) , 
				approverPersonOutput.getExpirationAgentFlag());
	}
}

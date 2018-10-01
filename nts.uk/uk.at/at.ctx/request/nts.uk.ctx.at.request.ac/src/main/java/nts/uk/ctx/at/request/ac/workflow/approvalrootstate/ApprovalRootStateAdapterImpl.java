package nts.uk.ctx.at.request.ac.workflow.approvalrootstate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalStatusForEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApproveRootStatusForEmpImPort;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApprovedImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverPersonImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRemandImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverWithFlagImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.RepresenterInformationImport;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverApprovedExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverPersonExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalRootStateAdapterImpl implements ApprovalRootStateAdapter {
	
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Inject
	private IntermediateDataPub intermediateDataPub;
	
	@Override
	public Map<String,List<ApprovalPhaseStateImport_New>> getApprovalRootContents(List<String> appIDs, String companyID) {
		Map<String,List<ApprovalPhaseStateImport_New>> approvalPhaseImport_NewMap = new LinkedHashMap<>();
		Map<String,List<ApprovalPhaseStateExport>> approvalRootContentExports = approvalRootStatePub.getApprovalRoots(appIDs, companyID);
		for(Map.Entry<String,List<ApprovalPhaseStateExport>> approvalRootContentExport : approvalRootContentExports.entrySet()){
					
			List<ApprovalPhaseStateImport_New> appRootContentImport_News =	approvalRootContentExport.getValue().stream()
						.map(x -> {
							return new ApprovalPhaseStateImport_New(
									x.getPhaseOrder(), 
									EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrImport_New.class),
									x.getListApprovalFrame().stream()
									.map(y -> {
										return new ApprovalFrameImport_New(
												y.getPhaseOrder(), 
												y.getFrameOrder(), 
												EnumAdaptor.valueOf(y.getApprovalAtr().value, ApprovalBehaviorAtrImport_New.class),
												y.getListApprover().stream().map(z -> 
													new ApproverStateImport_New(
															z.getApproverID(), 
															z.getApproverName(), 
															z.getRepresenterID(),
															z.getRepresenterName()))
													.collect(Collectors.toList()), 
												y.getApproverID(), 
												y.getApproverName(),
												y.getRepresenterID(),
												y.getRepresenterName(),
												y.getApprovalReason(), 0);
									}).collect(Collectors.toList()));
						}).collect(Collectors.toList());
			approvalPhaseImport_NewMap.put(approvalRootContentExport.getKey(), appRootContentImport_News);
		}
		return approvalPhaseImport_NewMap;
	}
	@Override
	public ApprovalRootContentImport_New getApprovalRootContent(String companyID, String employeeID, Integer appTypeValue, GeneralDate appDate, String appID, Boolean isCreate) {
		ApprovalRootContentExport approvalRootContentExport = approvalRootStatePub.getApprovalRoot(companyID, employeeID, appTypeValue, appDate, appID, isCreate);
		return new ApprovalRootContentImport_New(
					new ApprovalRootStateImport_New(
						approvalRootContentExport.getApprovalRootState().getListApprovalPhaseState().stream()
						.map(x -> {
							return new ApprovalPhaseStateImport_New(
									x.getPhaseOrder(), 
									EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrImport_New.class),
									x.getListApprovalFrame().stream()
									.map(y -> {
										return new ApprovalFrameImport_New(
												y.getPhaseOrder(), 
												y.getFrameOrder(), 
												EnumAdaptor.valueOf(y.getApprovalAtr().value, ApprovalBehaviorAtrImport_New.class),
												y.getListApprover().stream().map(z -> 
													new ApproverStateImport_New(
															z.getApproverID(), 
															z.getApproverName(), 
															z.getRepresenterID(),
															z.getRepresenterName()))
													.collect(Collectors.toList()), 
												y.getApproverID(), 
												y.getApproverName(),
												y.getRepresenterID(),
												y.getRepresenterName(),
												y.getApprovalReason(), y.getConfirmAtr());
									}).collect(Collectors.toList()));
						}).collect(Collectors.toList())),
					EnumAdaptor.valueOf(approvalRootContentExport.getErrorFlag().value, ErrorFlagImport.class));
	}

	@Override
	public void insertByAppType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID) {

		approvalRootStatePub.insertAppRootType(companyID, employeeID, appTypeValue, date, appID, 0);

	}

	@Override
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue,
			GeneralDate appDate) {

		return approvalRootStatePub.getNextApprovalPhaseStateMailList(companyID, rootStateID, 
				approvalPhaseStateNumber, isCreate, employeeID, appTypeValue, appDate, 0);

	}

	@Override
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate, String memo) {

		return approvalRootStatePub.doApprove(companyID, rootStateID, employeeID, isCreate, appTypeValue, appDate, memo, 0);

	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate) {
		// TODO Auto-generated method stub

		return approvalRootStatePub.isApproveAllComplete(companyID, rootStateID, employeeID, isCreate, appTypeValue, appDate, 0);

	}

	@Override
	public void doReleaseAllAtOnce(String companyID, String rootStateID) {

		approvalRootStatePub.doReleaseAllAtOnce(companyID, rootStateID, 0);

	}

	@Override
	public ApproverApprovedImport_New getApproverApproved(String rootStateID) {
		ApproverApprovedExport approverApprovedExport = approvalRootStatePub.getApproverApproved(rootStateID, 0);
		return new ApproverApprovedImport_New(
				approverApprovedExport.getListApproverWithFlagOutput().stream()
					.map(x -> new ApproverWithFlagImport_New(x.getEmployeeID(), x.getAgentFlag())).collect(Collectors.toList()), 
				approverApprovedExport.getListApprover());

	}

	@Override
	public AgentPubImport getApprovalAgencyInformation(String companyID, List<String> approver) {
		// TODO Auto-generated method stub
		return convertAgentPubImport(approvalRootStatePub.getApprovalAgentInfor(companyID, approver));
	}
	
	private AgentPubImport convertAgentPubImport(AgentPubExport agentPubExport) {
		return new AgentPubImport(
				agentPubExport.getListApproverAndRepresenterSID().stream()
				.map(x -> this.covertApproverImport(x)).collect(Collectors.toList()),
				agentPubExport.getListRepresenterSID(),
				agentPubExport.isFlag()
				);
		
	}
	
	private ApproverRepresenterImport covertApproverImport(ApproverRepresenterExport approverRepresenterExport) {
		return new  ApproverRepresenterImport(
				approverRepresenterExport.getApprover(),
				new RepresenterInformationImport(approverRepresenterExport.getRepresenter().getValue()) 
				);
	}

	@Override
	public List<String> getMailNotifierList(String companyID, String rootStateID) {
		return approvalRootStatePub.getMailNotifierList(companyID, rootStateID, 0);

	}

	@Override
	public void deleteApprovalRootState(String rootStateID) {
		approvalRootStatePub.deleteApprovalRootState(rootStateID, 0);
	}

	@Override
	public Boolean doRelease(String companyID, String rootStateID, String employeeID) {
		return approvalRootStatePub.doRelease(companyID, rootStateID, employeeID, 0);
	}

	@Override
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo) {
		return approvalRootStatePub.doDeny(companyID, rootStateID, employeeID, memo, 0);
	}

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID) {
		return approvalRootStatePub.judgmentTargetPersonIsApprover(companyID, rootStateID, employeeID, 0);
	}

	@Override
	public ApproverPersonImport judgmentTargetPersonCanApprove(String companyID, String rootStateID,
			String employeeID) {
		ApproverPersonExport approverPersonExport = approvalRootStatePub.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID, 0);
		return new ApproverPersonImport(
				approverPersonExport.getAuthorFlag(), 
				EnumAdaptor.valueOf(approverPersonExport.getApprovalAtr().value, ApprovalBehaviorAtrImport_New.class), 
				approverPersonExport.getExpirationAgentFlag());
	}

	@Override
	public List<String> doRemandForApprover(String companyID, String rootStateID, Integer order) {
		return approvalRootStatePub.doRemandForApprover(companyID, rootStateID, order, 0);
	}

	@Override
	public void doRemandForApplicant(String companyID, String rootStateID) {
		approvalRootStatePub.doRemandForApplicant(companyID, rootStateID, 0);
	}
	// request list 113
	@Override
	public List<ApproveRootStatusForEmpImPort> getApprovalByEmplAndDate(GeneralDate startDate, GeneralDate endDate,
			String employeeID, String companyID, Integer rootType) {
		return intermediateDataPub.getAppRootStatusByEmpPeriod(employeeID, new DatePeriod(startDate, endDate), rootType)
				.stream().map(x -> new ApproveRootStatusForEmpImPort(
						x.getEmployeeID(), 
						x.getDate(), 
						EnumAdaptor.valueOf(x.getDailyConfirmAtr(),ApprovalStatusForEmployeeImport.class))).collect(Collectors.toList());
	}
	@Override
	public List<String> getApproverFromPhase(String appID) {
//		return approvalRootStatePub.getApproverFromPhase(appID);
		return null;
	}
	@Override
	public List<ApproverRemandImport> getListApproverRemand(String appID) {
		return approvalRootStatePub.getListApproverRemand(appID).stream()
				.map(c-> new ApproverRemandImport(c.getPhaseOrder(), c.getSID(), c.isAgent()))
				.collect(Collectors.toList());
	}
	@Override
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, String rootStateID, Integer phaseNumber) {
		return approvalRootStatePub.isApproveApprovalPhaseStateComplete(companyID, rootStateID, phaseNumber);
	}
	@Override
	public List<ApproveRootStatusForEmpImPort> getAppRootStatusByEmpPeriodMonth(String employeeID, DatePeriod period) {
		return Collections.emptyList();
		/*return intermediateDataPub.getAppRootStatusByEmpPeriodMonth(employeeID, period)
				.stream().map(x -> new ApproveRootStatusForEmpImPort(
						x.getEmployeeID(), 
						x.getDate(), 
						EnumAdaptor.valueOf(x.getDailyConfirmAtr(),ApprovalStatusForEmployeeImport.class))).collect(Collectors.toList());*/
	}
}

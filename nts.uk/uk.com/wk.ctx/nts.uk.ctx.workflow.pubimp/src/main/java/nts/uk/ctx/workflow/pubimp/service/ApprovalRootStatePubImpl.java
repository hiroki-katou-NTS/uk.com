package nts.uk.ctx.workflow.pubimp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateService;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.ApproveService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalAgentInforService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.CollectMailNotifierService;
import nts.uk.ctx.workflow.dom.service.DenyService;
import nts.uk.ctx.workflow.dom.service.GenerateApprovalRootStateService;
import nts.uk.ctx.workflow.dom.service.JudgmentApprovalStatusService;
import nts.uk.ctx.workflow.dom.service.ReleaseAllAtOnceService;
import nts.uk.ctx.workflow.dom.service.ReleaseService;
import nts.uk.ctx.workflow.dom.service.RemandService;
import nts.uk.ctx.workflow.dom.service.output.AppRootStateConfirmOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalStatusOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverApprovedOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootConfirmService;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootInstancePeriod;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootInstanceService;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApprovalEmpStatus;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.agent.RepresenterInformationExport;
import nts.uk.ctx.workflow.pub.resultrecord.EmployeePerformParam;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.AppRootStateConfirmExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalActionByEmpl;
import nts.uk.ctx.workflow.pub.service.export.ApprovalBehaviorAtrExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalFrameExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateParam;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootOfEmployeeExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootSituation;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalStatus;
import nts.uk.ctx.workflow.pub.service.export.ApprovalStatusForEmployee;
import nts.uk.ctx.workflow.pub.service.export.ApproveRootStatusForEmpExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverApprovedExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverEmployeeState;
import nts.uk.ctx.workflow.pub.service.export.ApproverPersonExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverRemandExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverWithFlagExport;
import nts.uk.ctx.workflow.pub.service.export.ErrorFlagExport;
import nts.uk.ctx.workflow.pub.service.export.ReleasedProprietyDivision;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
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
	
	@Inject
	private RemandService remandService;
	
	@Inject
	private GenerateApprovalRootStateService generateApprovalRootStateService;
	
	@Inject
	private AppRootInstanceService appRootInstanceService;
	
	@Inject
	private ApprovalRootStateStatusService approvalRootStateStatusService;
	
	@Inject
	private AppRootConfirmService appRootConfirmService;
	
	@Override
	public Map<String,List<ApprovalPhaseStateExport>> getApprovalRoots(List<String> appIDs, String companyID){ 
		String approverID = AppContexts.user().employeeId();
		Map<String,List<ApprovalPhaseStateExport>> approvalPhaseStateExportMs = new LinkedHashMap<>();
		ApprovalRootContentOutput approvalRootContentOutput =  null;
		System.out.println("queryDB start: "+LocalDateTime.now());
		List<ApprovalRootState> approvalRootStates = approvalRootStateRepository.findEmploymentApps(appIDs, approverID);
		System.out.println("queryDB end: "+LocalDateTime.now());
		System.out.println("format start: "+LocalDateTime.now());
		if(!CollectionUtil.isEmpty(approvalRootStates)){
			for(ApprovalRootState approvalRootState :  approvalRootStates){
				approvalRootContentOutput = new ApprovalRootContentOutput(approvalRootState, ErrorFlag.NO_ERROR);
				
				List<ApprovalPhaseStateExport> approvalPhaseStateExports = approvalRootContentOutput.getApprovalRootState().getListApprovalPhaseState()
							.stream()
							.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
							.map(x -> {
								return new ApprovalPhaseStateExport(
										x.getPhaseOrder(),
										EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
										x.getListApprovalFrame()
										.stream()
										.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
										.map(y -> {
											return new ApprovalFrameExport(
													y.getPhaseOrder(), 
													y.getFrameOrder(), 
													EnumAdaptor.valueOf(y.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
													y.getListApproverState().stream().map(z -> { 
														return new ApproverStateExport(z.getApproverID(), "", "", "");
													}).collect(Collectors.toList()), 
													y.getApproverID(),
													"", 
													y.getRepresenterID(),		
													"",
													y.getApprovalReason(), y.getConfirmAtr().value);
										}).collect(Collectors.toList()));
							}).collect(Collectors.toList());
				approvalPhaseStateExportMs.put(approvalRootState.getRootStateID(), approvalPhaseStateExports);
			}
		}
		System.out.println("format end: "+LocalDateTime.now());
		return approvalPhaseStateExportMs;
	}
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
								EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
								x.getListApprovalFrame()
								.stream()
								.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
								.map(y -> {
									return new ApprovalFrameExport(
											y.getPhaseOrder(), 
											y.getFrameOrder(), 
											EnumAdaptor.valueOf(y.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
											y.getListApproverState().stream().map(z -> { 
												String approverName = personAdapter.getPersonInfo(z.getApproverID()).getEmployeeName();
												String representerID = "";
												String representerName = "";
												ApprovalRepresenterOutput approvalRepresenterOutput = 
														collectApprovalAgentInforService.getApprovalAgentInfor(companyID, Arrays.asList(z.getApproverID()));
												if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.FALSE)){
													if(!CollectionUtil.isEmpty(approvalRepresenterOutput.getListAgent())){
														representerID = approvalRepresenterOutput.getListAgent().get(0);
														representerName = personAdapter.getPersonInfo(representerID).getEmployeeName();
													}
												}
												return new ApproverStateExport(z.getApproverID(), approverName, representerID, representerName);
											}).collect(Collectors.toList()), 
											y.getApproverID(),
											Strings.isBlank(y.getApproverID()) ? "" : personAdapter.getPersonInfo(y.getApproverID()).getEmployeeName(), 
											y.getRepresenterID(),		
											Strings.isBlank(y.getRepresenterID()) ? "" : personAdapter.getPersonInfo(y.getRepresenterID()).getEmployeeName(),
											y.getApprovalReason(), y.getConfirmAtr().value);
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList())
				), 
				EnumAdaptor.valueOf(approvalRootContentOutput.getErrorFlag().value, ErrorFlagExport.class));
		
		
	}
	
	@Override
	public void insertAppRootType(String companyID, String employeeID, 
			Integer appTypeValue, GeneralDate date, String appID, Integer rootType) {
		approvalRootStateService.insertAppRootType(
				companyID, 
				employeeID, 
				EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
				date, 
				appID,
				rootType);
	}

	@Override
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue,
			GeneralDate appDate, Integer rootType) {
		return approveService.getNextApprovalPhaseStateMailList(
				companyID, 
				rootStateID, 
				approvalPhaseStateNumber, 
				isCreate, 
				employeeID, 
				EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
				appDate,
				rootType);
	}

	@Override
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, 
			Integer appTypeValue, GeneralDate appDate, String memo, Integer rootType) {
		return approveService.doApprove(
				companyID, 
				rootStateID, 
				employeeID, 
				isCreate, 
				EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
				appDate, 
				memo,
				rootType);
	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate, Integer rootType) {
		return approveService.isApproveAllComplete(
				companyID, 
				rootStateID, 
				employeeID, 
				isCreate, 
				EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
				appDate,
				rootType);
	}

	@Override
	public void doReleaseAllAtOnce(String companyID, String rootStateID, Integer rootType) {
		releaseAllAtOnceService.doReleaseAllAtOnce(companyID, rootStateID, rootType);
	}

	@Override
	public ApproverApprovedExport getApproverApproved(String rootStateID, Integer rootType) {
		ApproverApprovedOutput approverApprovedOutput = releaseAllAtOnceService.getApproverApproved(rootStateID, rootType);
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
	public List<String> getMailNotifierList(String companyID, String rootStateID, Integer rootType) {
		return collectMailNotifierService.getMailNotifierList(companyID, rootStateID, rootType);
	}

	@Override
	public void deleteApprovalRootState(String rootStateID, Integer rootType) {
		approvalRootStateRepository.delete(rootStateID, rootType);
	}

	@Override
	public Boolean doRelease(String companyID, String rootStateID, String employeeID, Integer rootType) {
		return releaseService.doRelease(companyID, rootStateID, employeeID, rootType);
	}

	@Override
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo, Integer rootType) {
		return denyService.doDeny(companyID, rootStateID, employeeID, memo, rootType);
	}

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID, Integer rootType) {
		return judgmentApprovalStatusService.judgmentTargetPersonIsApprover(companyID, rootStateID, employeeID, rootType);
	}

	@Override
	public ApproverPersonExport judgmentTargetPersonCanApprove(String companyID, String rootStateID,
			String employeeID, Integer rootType) {
		ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService
				.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID, rootType);
		return new ApproverPersonExport(
				approverPersonOutput.getAuthorFlag(), 
				EnumAdaptor.valueOf(approverPersonOutput.getApprovalAtr().value, ApprovalBehaviorAtrExport.class) , 
				approverPersonOutput.getExpirationAgentFlag());
	}

	@Override
	public List<String> doRemandForApprover(String companyID, String rootStateID, Integer order, Integer rootType) {
		return remandService.doRemandForApprover(companyID, rootStateID, order, rootType);
	}

	@Override
	public void doRemandForApplicant(String companyID, String rootStateID, Integer rootType) {
		remandService.doRemandForApplicant(companyID, rootStateID, rootType);
	}
	@Override
	public ApprovalRootOfEmployeeExport getApprovalRootOfEmloyee(String employeeID, DatePeriod period, Integer rootType) {
		ApprovalEmpStatus approvalEmpStatus = appRootInstanceService.getApprovalEmpStatus(employeeID, period, EnumAdaptor.valueOf(rootType, RecordRootType.class));
		return new ApprovalRootOfEmployeeExport(
				approvalEmpStatus.getEmployeeID(), 
				approvalEmpStatus.getRouteSituationLst().stream()
				.map(x -> new ApprovalRootSituation(
						"", 
						EnumAdaptor.valueOf(x.getApproverEmpState().value, ApproverEmployeeState.class), 
						x.getDate(), 
						x.getEmployeeID(), 
						x.getApprovalStatus().map(y -> new ApprovalStatus(
								EnumAdaptor.valueOf(y.getApprovalAction().value, ApprovalActionByEmpl.class), 
								EnumAdaptor.valueOf(y.getReleaseAtr().value, ReleasedProprietyDivision.class))).orElse(null))).collect(Collectors.toList()));
	}
	private void checkStatusFrame(ApprovalStatusOutput approvalStatusOutput,ApprovalStatus approvalStatus ){
		// output「ルート状況」をセットする
		if(approvalStatusOutput.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED) && approvalStatusOutput.getApprovableFlag() == true){
			approvalStatus.setReleaseDivision(EnumAdaptor.valueOf(ReleasedProprietyDivision.RELEASE.value, ReleasedProprietyDivision.class));
		}else{
			approvalStatus.setReleaseDivision(EnumAdaptor.valueOf(ReleasedProprietyDivision.NOT_RELEASE.value, ReleasedProprietyDivision.class));
		}
		//承認状況．基準社員の承認アクション
		if(approvalStatusOutput.getApprovableFlag() == false){
			approvalStatus.setApprovalActionByEmpl(EnumAdaptor.valueOf(ApprovalActionByEmpl.NOT_APPROVAL.value, ApprovalActionByEmpl.class));
		}else if(approvalStatusOutput.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
			approvalStatus.setApprovalActionByEmpl(EnumAdaptor.valueOf(ApprovalActionByEmpl.APPROVAL_REQUIRE.value, ApprovalActionByEmpl.class));
		}else{
			approvalStatus.setApprovalActionByEmpl(EnumAdaptor.valueOf(ApprovalActionByEmpl.APPROVALED.value, ApprovalActionByEmpl.class));
		}
	}
	private boolean checkPhase(int phaseOrderApprover,List<Integer> phaseOrders, int type){
		boolean result = false;
		if (type == 0) {
			for (Integer a : phaseOrders) {
				if(a == phaseOrderApprover){
					return true;
				}
			}
		} else if (type == 1) {
			for (Integer a : phaseOrders) {
				if(a > phaseOrderApprover){
					return true;
				}
			}
		}

		return result;
	}
	@Override
	public List<ApproveRootStatusForEmpExport> getApprovalByEmplAndDate(String employeeID, DatePeriod period, Integer rootType) {
		List<String> employeeIDLst = Arrays.asList(employeeID);
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
				.stream().map(x -> new ApproveRootStatusForEmpExport(
						x.getEmployeeID(), 
						x.getDate(), 
						EnumAdaptor.valueOf(x.getDailyConfirmAtr().value, ApprovalStatusForEmployee.class))).collect(Collectors.toList());
	}
	//承認ルート状況を取得する
	private List<ApproveRootStatusForEmpExport> getApproveRootStatusForEmpExport(List<ApprovalRootState> approvalRootSates){
		List<ApproveRootStatusForEmpExport> result = new ArrayList<>();
		for(ApprovalRootState approvalRoot : approvalRootSates){
			ApproveRootStatusForEmpExport approveRootStatusForEmpExport = new ApproveRootStatusForEmpExport();
			int status = ApprovalStatusForEmployee.UNAPPROVED.value;
			boolean unapprovedPhasePresent = false;
			List<ApprovalPhaseState> listApprovalPhaseState = approvalRoot.getListApprovalPhaseState();
			listApprovalPhaseState.sort((a,b) -> b.getPhaseOrder().compareTo(a.getPhaseOrder()));
			for(ApprovalPhaseState approvalPhaseState : listApprovalPhaseState){
				//1.承認フェーズ毎の承認者を取得する(getApproverFromPhase)
				List<String> approverFromPhases = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
				// ループ中の承認フェーズに承認者がいる
				if(!CollectionUtil.isEmpty(approverFromPhases)){
					// ループ中のドメインモデル「承認フェーズインスタンス」．承認区分 == 承認済
					if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
						//未承認フェーズあり=true
						if(unapprovedPhasePresent == true){
							status = ApprovalStatusForEmployee.DURING_APPROVAL.value;
							break;
						}else{
							// 未承認フェーズあり=false
							status = ApprovalStatusForEmployee.APPROVED.value;
							break;
						}
					}else{
						unapprovedPhasePresent = true;
						if(checkApproverOfFrame(approvalPhaseState.getListApprovalFrame())){
							status = ApprovalStatusForEmployee.DURING_APPROVAL.value;
							break;
						}
					}
				}
			}
			approveRootStatusForEmpExport.setAppDate(approvalRoot.getApprovalRecordDate());
			approveRootStatusForEmpExport.setEmployeeID(approvalRoot.getEmployeeID());
			approveRootStatusForEmpExport.setApprovalStatus(EnumAdaptor.valueOf(status, ApprovalStatusForEmployee.class));
			result.add(approveRootStatusForEmpExport);
		}
		return result;
	}
	
	private boolean checkApproverOfFrame(List<ApprovalFrame> listApprovalFrame){
		for(ApprovalFrame approvalFrame : listApprovalFrame){
			if(approvalFrame.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean checkDataApproveed(String approverID, DatePeriod period, Integer rootType) {
		return appRootInstanceService.isDataExist(approverID, period, EnumAdaptor.valueOf(rootType, RecordRootType.class) );
	}
	@Override
	// RequestList229
	public List<ApproveRootStatusForEmpExport> getApprovalByListEmplAndDate(List<String> employeeIDLst, DatePeriod period, Integer rootType) {
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
				.stream().map(x -> new ApproveRootStatusForEmpExport(
						x.getEmployeeID(), 
						x.getDate(), 
						EnumAdaptor.valueOf(x.getDailyConfirmAtr().value, ApprovalStatusForEmployee.class))).collect(Collectors.toList());
	}
	@Override
	public AppRootStateConfirmExport getApprovalRootState(String companyID, String employeeID, Integer confirmAtr,
			Integer appType, GeneralDate date) {
		AppRootStateConfirmOutput appRootStateConfirmOutput = generateApprovalRootStateService.getApprovalRootState(
				companyID, 
				employeeID, 
				EnumAdaptor.valueOf(confirmAtr-1, ConfirmationRootType.class), 
				appType == null ? null : EnumAdaptor.valueOf(appType, ApplicationType.class), 
				date);
		return new AppRootStateConfirmExport(
				appRootStateConfirmOutput.getIsError(), 
				appRootStateConfirmOutput.getRootStateID(), 
				appRootStateConfirmOutput.getErrorMsg());
	}
	@Override
	// requestList155
	public List<ApproveRootStatusForEmpExport> getApprovalByListEmplAndListApprovalRecordDate(List<GeneralDate> dateLst, List<String> employeeIDLst, Integer rootType) {
		List<ApproveRootStatusForEmpExport> result = new ArrayList<>();
		dateLst.forEach(date -> {
			DatePeriod period = new DatePeriod(date, date);
			result.addAll(appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
			.stream().map(x -> new ApproveRootStatusForEmpExport(
					x.getEmployeeID(), 
					x.getDate(), 
					EnumAdaptor.valueOf(x.getDailyConfirmAtr().value, ApprovalStatusForEmployee.class)))
			.collect(Collectors.toList()));
		});
		return result;
	}
	@Override
	// requestList347
	public void registerApproval(String approverID, List<EmployeePerformParam> employeePerformLst, Integer rootType) {
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		employeePerformLst.forEach(employee -> {
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			// 対象日の就業実績確認状態を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootConfirmByDate(companyID, employeeID, date, rootTypeEnum);
			// (中間データ版)承認する
			appRootConfirmService.approve(approverID, employeeID, date, appRootInstance, appRootConfirm);
		});
	}
	@Override
	// requestList356
	public boolean releaseApproval(String approverID, List<EmployeePerformParam> employeePerformLst, Integer rootType) {
		boolean result = false;
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		for(EmployeePerformParam employee : employeePerformLst){
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			// 対象日の就業実績確認状態を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootConfirmByDate(companyID, employeeID, date, rootTypeEnum);
			// (中間データ版)解除する
			result = appRootConfirmService.cleanStatus(approverID, employeeID, date, appRootInstance, appRootConfirm);
			if(!result){
				break;
			}
		};
		return result;
	}
	@Override
	public void cleanApprovalRootState(String rootStateID, Integer rootType) {
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed());
		approvalRootState.getListApprovalPhaseState().stream().forEach(approvalPhaseState -> {
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
				approvalFrame.setApproverID(null);
				approvalFrame.setRepresenterID(null);
				approvalFrame.setApprovalDate(null);
				approvalFrame.setApprovalReason(null);
			});
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
		});
		approvalRootStateRepository.update(approvalRootState, rootType);
		
	}
	@Override
	public void deleteConfirmDay(String employeeID, GeneralDate date) {
		approvalRootStateRepository.deleteConfirmDay(employeeID, date);
	}
	/**
	 * RequestList No.483
	 * 1.承認フェーズ毎の承認者を取得する
	 * @param phase
	 * @return
	 */
	@Override
	public List<String> getApproverFromPhase(ApprovalPhaseStateParam param) {
		ApprovalPhaseState phase = ApprovalPhaseState.createFormTypeJava(param.getRootStateID(), param.getPhaseOrder(),
				param.getApprovalAtr(),param.getApprovalForm(), 
				param.getListApprovalFrame().stream().map(c->new ApprovalFrame(c.getRootStateID(),
					c.getPhaseOrder(),
					c.getFrameOrder(),
					EnumAdaptor.valueOf(c.getApprovalAtr(), ApprovalBehaviorAtr.class),
					EnumAdaptor.valueOf(c.getConfirmAtr(), ConfirmPerson.class),
					c.getListApproverState().stream().map(x -> new ApproverState(x.getRootStateID(),
						x.getPhaseOrder(),
						x.getFrameOrder(),
						x.getApproverID(),
						x.getCompanyID(),
						x.getDate())).collect(Collectors.toList()),
					c.getApproverID(),
					c.getRepresenterID(),
					c.getApprovalDate(),
					c.getApprovalReason())).collect(Collectors.toList()));
		return judgmentApprovalStatusService.getApproverFromPhase(phase);
	}
	/**
	 * RequestList 479
	 * 差し戻し対象者一覧を取得
	 * @param appID
	 * @return
	 */
	@Override
	public List<ApproverRemandExport> getListApproverRemand(String appID) {
		List<ApproverRemandExport> lstResult = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「承認フェーズインスタンス」から最大の承認済フェーズを取得-(Lấy phase đã approve có order lớn nhất từ domain 「承認フェーズインスタンス」)
		List<ApprovalPhaseState> phaseMax = approvalRootStateRepository.findPhaseApprovalMax(appID);
		for (ApprovalPhaseState phase : phaseMax) {
			//アルゴリズム「1.承認フェーズ毎の承認者を取得する」 - RequestList No.483
			List<String> lstApprover = judgmentApprovalStatusService.getApproverFromPhase(phase);
			for (String approver : lstApprover) {
				lstResult.add(new ApproverRemandExport(phase.getPhaseOrder(), approver, false));
			}
			//アルゴリズム「承認代行情報の取得処理」を実行する-(3-1) (Thực hiện "xử lý lấy thông tin đại diện approval")
			AgentPubExport agent = this.getApprovalAgentInfor(companyID, lstApprover);
			//「承認者の代行情報リスト」に存在する承認者に代行を付加する-(thêm thằng đại diện vào list -承認者の代行情報リスト」)
			for (String agentId : agent.getListRepresenterSID()) {
				lstResult.add(new ApproverRemandExport(phase.getPhaseOrder(), agentId, true));
			}
		}
		return lstResult;
	}
	@Override
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, String rootStateID, Integer phaseNumber) {
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, RootType.EMPLOYMENT_APPLICATION.value);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		Optional<ApprovalPhaseState> opCurrentPhase = approvalRootState.getListApprovalPhaseState().stream()
				.filter(x -> x.getPhaseOrder()==phaseNumber).findAny();
		if(!opCurrentPhase.isPresent()){
			return false;
		}
		return approveService.isApproveApprovalPhaseStateComplete(companyID, opCurrentPhase.get());
	}
}

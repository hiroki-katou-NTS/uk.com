package nts.uk.ctx.workflow.pubimp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.StatusOfEmployment;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateService;
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
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutputNew;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.agent.ApproverRepresenterExport;
import nts.uk.ctx.workflow.pub.agent.RepresenterInformationExport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.AppRootStateConfirmExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalActionByEmpl;
import nts.uk.ctx.workflow.pub.service.export.ApprovalBehaviorAtrExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalFormExport;
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
import nts.uk.ctx.workflow.pub.service.export.ApproverPersonExportNew;
import nts.uk.ctx.workflow.pub.service.export.ApproverRemandExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverWithFlagExport;
import nts.uk.ctx.workflow.pub.service.export.ErrorFlagExport;
import nts.uk.ctx.workflow.pub.service.export.ReleasedProprietyDivision;
import nts.uk.shr.com.context.AppContexts;
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
	private ApprovalSettingRepository repoApprSet;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	@Override
	public Map<String,List<ApprovalPhaseStateExport>> getApprovalRoots(List<String> appIDs, String companyID){ 
		String approverID = AppContexts.user().employeeId();
		Map<String,List<ApprovalPhaseStateExport>> mapApprPhsStateEx = new LinkedHashMap<>();
		ApprovalRootContentOutput apprRootContentOut =  null;
		//ドメインモデル「承認ルートインスタンス」を取得する
		List<ApprovalRootState> approvalRootStates = approvalRootStateRepository.findEmploymentApps(appIDs, approverID);
		if(approvalRootStates.isEmpty()){
			return mapApprPhsStateEx;
		}
		//hoatt 2018.12.14
		//EA修正履歴 No.3009
		//ドメインモデル「承認設定」を取得する
		Optional<PrincipalApprovalFlg> flg = repoApprSet.getPrincipalByCompanyId(companyID);
		List<ApprovalRootState> lstApprRootSttFil = new ArrayList<>();
		if(!flg.isPresent() || flg.get().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)){//本人による承認＝falseの場合
			//申請者ID＝ログイン社員IDのデータを取得した一覧から削除する(applicantID = loginID -> remove app)
			lstApprRootSttFil = approvalRootStates.stream().filter(c -> !c.getEmployeeID().equals(approverID))
					.collect(Collectors.toList());
		}else{
			lstApprRootSttFil = approvalRootStates;
		}
		if(lstApprRootSttFil.isEmpty()){
			return mapApprPhsStateEx;
		}
		for(ApprovalRootState approvalRootState :  lstApprRootSttFil){
			apprRootContentOut = new ApprovalRootContentOutput(approvalRootState, ErrorFlag.NO_ERROR);
			
			List<ApprovalPhaseStateExport> lstApprPhsStateEx = apprRootContentOut.getApprovalRootState().getListApprovalPhaseState()
						.stream()
						.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
						.map(x -> {
							return new ApprovalPhaseStateExport(
									x.getPhaseOrder(),
									EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
									EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
									x.getListApprovalFrame()
									.stream()
									.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
									.map(y -> {
										return new ApprovalFrameExport(
												y.getFrameOrder(), 
												y.getLstApproverInfo().stream().map(z -> { 
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
													return new ApproverStateExport(
															z.getApproverID(), 
															EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
															z.getAgentID(),
															approverName, 
															personAdapter.getPersonInfo(z.getAgentID()).getEmployeeName(),
															representerID, 
															representerName,
															z.getApprovalDate(),
															z.getApprovalReason(),
															z.getApproverInListOrder());
												}).collect(Collectors.toList()), 
												y.getConfirmAtr().value,
												y.getAppDate());
									}).collect(Collectors.toList()));
						}).collect(Collectors.toList());
			mapApprPhsStateEx.put(approvalRootState.getRootStateID(), lstApprPhsStateEx);
		}
		return mapApprPhsStateEx;
	}
	@Override
	public ApprovalRootContentExport getApprovalRoot(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID, Boolean isCreate) {
		ApprovalRootContentOutput approvalRootContentOutput = null;
		if(isCreate.equals(Boolean.TRUE)){
			approvalRootContentOutput = collectApprovalRootService.getApprovalRootOfSubjectRequest(
					companyID, 
					employeeID, 
					EmploymentRootAtr.APPLICATION, 
					appTypeValue.toString(), 
					date,
					SystemAtr.WORK,
					Optional.empty());
		} else {
			ApprovalRootState approvalRootState = approvalRootStateRepository.findByID(appID, 0).orElseThrow(()->
					new RuntimeException("data WWFDT_APPROVAL_ROOT_STATE error: ID ="+appID)
			);
			approvalRootContentOutput = new ApprovalRootContentOutput(approvalRootState, ErrorFlag.NO_ERROR);
		}
		ApprovalRootContentExport result = new ApprovalRootContentExport(
				new ApprovalRootStateExport(
					approvalRootContentOutput.getApprovalRootState().getRootStateID(),
					approvalRootContentOutput.getApprovalRootState().getRootType().value,
					approvalRootContentOutput.getApprovalRootState().getApprovalRecordDate(),
					approvalRootContentOutput.getApprovalRootState().getEmployeeID(),
					approvalRootContentOutput.getApprovalRootState().getListApprovalPhaseState()
					.stream()
					.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
					.map(x -> {
						return new ApprovalPhaseStateExport(
								x.getPhaseOrder(),
								EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
								EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
								x.getListApprovalFrame()
								.stream()
								.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
								.map(y -> {
									return new ApprovalFrameExport(
											y.getFrameOrder(), 
											y.getLstApproverInfo().stream().map(z -> { 
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
												return new ApproverStateExport(
														z.getApproverID(), 
														EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
														z.getAgentID(),
														approverName, 
														personAdapter.getPersonInfo(z.getAgentID()).getEmployeeName(),
														representerID, 
														representerName,
														z.getApprovalDate(),
														z.getApprovalReason(),
														z.getApproverInListOrder());
											}).collect(Collectors.toList()), 
											y.getConfirmAtr().value,
											y.getAppDate());
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList())
				), 
				EnumAdaptor.valueOf(approvalRootContentOutput.getErrorFlag().value, ErrorFlagExport.class));
		if(date==null) {
			return checkApproverStatus(result, approvalRootContentOutput.approvalRootState.getApprovalRecordDate());
		}
		return checkApproverStatus(result, date);
	}
	
	@Override
	public void insertAppRootType(String companyID, String employeeID, 
			Integer appTypeValue, GeneralDate appDate, String appID, Integer rootType, GeneralDate baseDate) {
		approvalRootStateService.insertAppRootType(
				companyID, 
				employeeID, 
				appTypeValue.toString(), 
				appDate, 
				appID,
				rootType,
				baseDate);
	}
	
	@Override
	public void insertFromCache(String companyID, String appID, GeneralDate date, String employeeID,
			List<ApprovalPhaseStateExport> listApprovalPhaseState) {
		List<ApprovalPhaseState> convertLst = listApprovalPhaseState.stream()
				.map(x -> ApprovalPhaseState.createFormTypeJava(
						x.getPhaseOrder(), 
						x.getApprovalAtr().value, 
						x.getApprovalForm().value, 
						x.getListApprovalFrame().stream().map(y -> ApprovalFrame.convert(
								y.getFrameOrder(), 
								y.getConfirmAtr(), 
								y.getAppDate(), 
								y.getListApprover().stream().map(z -> ApproverInfor.convert(
										z.getApproverID(), 
										z.getApprovalAtr().value, 
										z.getAgentID(), 
										z.getApprovalDate(), 
										z.getApprovalReason(),
										z.getApproverInListOrder()))
								.collect(Collectors.toList())))
						.collect(Collectors.toList())))
				.collect(Collectors.toList());
		approvalRootStateService.insertFromCache(companyID, appID, date, employeeID, convertLst);
	}

	@Override
	public List<String> getNextApprovalPhaseStateMailList(String rootStateID, Integer approvalPhaseStateNumber) {
		return approveService.getNextApprovalPhaseStateMailList(rootStateID, approvalPhaseStateNumber);
	}

	@Override
	public Integer doApprove(String rootStateID, String employeeID, String memo) {
		return approveService.doApprove(rootStateID, employeeID, memo);
	}

	@Override
	public Boolean isApproveAllComplete(String rootStateID) {
		return approveService.isApproveAllComplete(rootStateID);
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
	public Boolean doDeny(String rootStateID, String employeeID, String memo) {
		return denyService.doDeny(rootStateID, employeeID, memo);
	}

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID, Integer rootType) {
		return judgmentApprovalStatusService.judgmentTargetPersonIsApprover(companyID, rootStateID, employeeID, rootType);
	}

	@Override
	public ApproverPersonExportNew judgmentTargetPersonCanApprove(String companyID, String rootStateID,
			String employeeID, Integer rootType) {
		ApproverPersonOutputNew approverPersonOutput = judgmentApprovalStatusService
				.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID, rootType);
		return new ApproverPersonExportNew(
				approverPersonOutput.getAuthorFlag(), 
				EnumAdaptor.valueOf(approverPersonOutput.getApprovalAtr().value, ApprovalBehaviorAtrExport.class) , 
				approverPersonOutput.getExpirationAgentFlag(),
				EnumAdaptor.valueOf(approverPersonOutput.getApprovalPhaseAtr().value, ApprovalBehaviorAtrExport.class));
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
	public ApprovalRootOfEmployeeExport getApprovalRootOfEmloyee(GeneralDate startDate, GeneralDate endDate,
			String approverID,String companyID,Integer rootType) {
		List<ApprovalRootState> approvalRootStates = new ArrayList<>();
		// 承認者と期間から承認ルートインスタンスを取得する
		long start = System.currentTimeMillis();
		List<ApprovalRootState> resultApprovalRootState = this.approvalRootStateRepository.findByApprover(companyID, startDate, endDate, approverID, rootType);
		approvalRootStates.addAll(resultApprovalRootState);
		long end = System.currentTimeMillis();
		System.out.println("Thời gian chạy đoạn lệnh: " + (end - start) + "Millis");
		// ドメインモデル「代行承認」を取得する
		List<Agent> agents = this.agentRepository.findByApproverAndDate(companyID, approverID, startDate, endDate);
		List<String> employeeApproverID = new ArrayList<>();
		employeeApproverID.add(approverID);
		
		if (!CollectionUtil.isEmpty(agents)) {
			for(Agent agent : agents){
				// ドメインモデル「承認ルートインスタンス」を取得する
				employeeApproverID.add(agent.getEmployeeId());
			}
		}
		long end1 = System.currentTimeMillis();
		System.out.println("Thời gian chạy đoạn lệnh: " + (end1 - end) + "Millis");
		ApprovalRootOfEmployeeExport result = new ApprovalRootOfEmployeeExport();
		
		if(CollectionUtil.isEmpty(approvalRootStates)){
			return result;
		}
		List<ApprovalRootSituation> approvalRootSituations = new ArrayList<>();
		//
		for(ApprovalRootState approverRoot : approvalRootStates){
			ApprovalRootSituation approvalRootSituation = new ApprovalRootSituation(approverRoot.getRootStateID(),
					null,
					approverRoot.getApprovalRecordDate(),
					approverRoot.getEmployeeID(),
					new ApprovalStatus(EnumAdaptor.valueOf(ApprovalActionByEmpl.NOT_APPROVAL.value, ApprovalActionByEmpl.class),
									EnumAdaptor.valueOf(ReleasedProprietyDivision.NOT_RELEASE.value, ReleasedProprietyDivision.class)));
			//承認中のフェーズの承認者か = false
			boolean approverPhaseFlag = false;
			//基準社員のフェーズ=0
			int employeephase = 0;
			approverRoot.getListApprovalPhaseState().sort((a,b) -> b.getPhaseOrder().compareTo(a.getPhaseOrder()));
			List<ApprovalPhaseState> listApprovalPhaseState = approverRoot.getListApprovalPhaseState();
			
			ApprovalStatus approvalStatus = new ApprovalStatus();
			List<Integer> phaseOfApprover = new ArrayList<>();
			boolean statusFrame  = false;
			for(int i =0; i < listApprovalPhaseState.size();i++){
				// add approver
				// TODO
				for(ApprovalFrame approvalFrame : listApprovalPhaseState.get(i).getListApprovalFrame()){
					for(ApproverInfor approverState : approvalFrame.getLstApproverInfo())	{
						// xu li lay list phase chua approverID
						for(String employeeID : employeeApproverID ){
							if(approverState.getApproverID().equals(employeeID)){
								phaseOfApprover.add(listApprovalPhaseState.get(i).getPhaseOrder());
							}
						}
					}
					if(listApprovalPhaseState.get(i).getApprovalForm()==ApprovalForm.SINGLE_APPROVED) {
						Optional<ApproverInfor> opApproverInfor = approvalFrame.getLstApproverInfo()
								.stream()
								.filter(x -> x.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED)
								.findAny();
						if(opApproverInfor.isPresent()) {
							statusFrame = true;
						}
					} else {
						Optional<ApproverInfor> opApproverInfor = approvalFrame.getLstApproverInfo()
								.stream()
								.filter(x -> x.getApprovalAtr()!=ApprovalBehaviorAtr.APPROVED)
								.findAny();
						if(!opApproverInfor.isPresent()) {
							statusFrame = true;
						}
					}
				}
				//1.承認フェーズ毎の承認者を取得する(getApproverFromPhase)
				List<String> approverFromPhases = judgmentApprovalStatusService.getApproverFromPhase(listApprovalPhaseState.get(i));
				if(!CollectionUtil.isEmpty(approverFromPhases)){
					if(!listApprovalPhaseState.get(i).getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
						// 承認中のフェーズ＝ループ中のフェーズ．順序
						int approverPhase = i;
						// フェーズ承認区分＝ループ中のフェーズ．承認区分
						int approverPhaseIndicator = listApprovalPhaseState.get(i).getApprovalAtr().value;
					}
					//1.承認状況の判断
					ApprovalStatusOutput approvalStatusOutput = judgmentApprovalStatusService.judmentApprovalStatusNodataDatabaseAcess(
							companyID, listApprovalPhaseState.get(i), approverID,
							agents.stream().map(x -> x.getAgentSid1()).collect(Collectors.toList()));
					if(listApprovalPhaseState.get(i).getPhaseOrder().equals(5) && listApprovalPhaseState.get(i).getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED) ){
						checkStatusFrame(approvalStatusOutput,approvalStatus);
						employeephase = i;
						break;
					}
					if(approverPhaseFlag == true && listApprovalPhaseState.get(i).getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
						break;
					}
					if(approvalStatusOutput.getApprovalFlag() == true && approvalStatusOutput.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
						approverPhaseFlag = true;
					}
					checkStatusFrame(approvalStatusOutput,approvalStatus);
					//基準社員のフェーズ＝ループ中のフェーズ．順序
					if(approvalStatusOutput.getApprovalFlag() == true){
						employeephase = i;
					}
					if(approvalStatusOutput.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
						break;
					}
					if(statusFrame){
						break;
					}
				}
				
			}
			approvalRootSituation.setApprovalStatus(approvalStatus);
			// output「ルート状況」をセットする
			if(checkPhase(approverRoot.getListApprovalPhaseState().get(employeephase).getPhaseOrder(),phaseOfApprover,0) && approverRoot.getListApprovalPhaseState().get(employeephase) .getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
				approvalRootSituation.setApprovalAtr(ApproverEmployeeState.PHASE_DURING);
			}else if(checkPhase(approverRoot.getListApprovalPhaseState().get(employeephase).getPhaseOrder(),phaseOfApprover,0) && approverRoot.getListApprovalPhaseState().get(employeephase) .getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				approvalRootSituation.setApprovalAtr(ApproverEmployeeState.COMPLETE);
			}else if(checkPhase(approverRoot.getListApprovalPhaseState().get(employeephase).getPhaseOrder(),phaseOfApprover,1)){
				approvalRootSituation.setApprovalAtr(ApproverEmployeeState.PHASE_LESS);
			}else{
				approvalRootSituation.setApprovalAtr(ApproverEmployeeState.PHASE_PASS);
			}
			approvalRootSituations.add(approvalRootSituation);
		}
		long end2 = System.currentTimeMillis();
		System.out.println("Thời gian chạy đoạn lệnh2: " + (end2 - end1) + "Millis");
		result.setEmployeeStandard(approverID);
		result.setApprovalRootSituations(approvalRootSituations);
		return result;
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
	public List<ApproveRootStatusForEmpExport> getApprovalByEmplAndDate(GeneralDate startDate, GeneralDate endDate,
			String employeeID, String companyID, Integer rootType) {
		List<ApproveRootStatusForEmpExport> result = new ArrayList<>();
		// 対象者と期間から承認ルートインスタンスを取得する
		List<ApprovalRootState> approvalRootSates = this.approvalRootStateRepository.findAppByEmployeeIDRecordDate(startDate, endDate, employeeID, rootType);
		
		//承認ルート状況を取得する
		result = this.getApproveRootStatusForEmpExport(approvalRootSates);
		return result;
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
						if(checkApproverOfFrame(approvalPhaseState)){
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
	
	private boolean checkApproverOfFrame(ApprovalPhaseState approvalPhaseState){
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
			if(approvalPhaseState.getApprovalForm()==ApprovalForm.SINGLE_APPROVED) {
				Optional<ApproverInfor> opApproverInfor = approvalFrame.getLstApproverInfo()
						.stream()
						.filter(x -> x.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED)
						.findAny();
				if(opApproverInfor.isPresent()) {
					return true;
				}
			} else {
				Optional<ApproverInfor> opApproverInfor = approvalFrame.getLstApproverInfo()
						.stream()
						.filter(x -> x.getApprovalAtr()!=ApprovalBehaviorAtr.APPROVED)
						.findAny();
				if(!opApproverInfor.isPresent()) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean checkDataApproveed(GeneralDate startDate, GeneralDate endDate, String approverID, Integer rootType,
			String companyID ) {
	/*	List<ApprovalRootState> approvalRootStates = new ArrayList<>();
		if(rootType == null){
			// xử lí 承認者と期間から承認ルートインスタンスを取得する（ルート種類指定なし）
			 approvalRootStates = this.approvalRootStateRepository
					.findEmployeeAppByApprovalRecordDateAndNoRootType(companyID, startDate, endDate, approverID);
			 
		}else{
			// 承認者と期間から承認ルートインスタンスを取得する
			 approvalRootStates = this.approvalRootStateRepository
					.findByApprover(companyID, startDate, endDate, approverID, rootType);
		}
		if(CollectionUtil.isEmpty(approvalRootStates)){
			return false;
		}
		boolean result = false;
		for(ApprovalRootState approval : approvalRootStates){
			ApproverPersonExportNew approverPersonExport = this.judgmentTargetPersonCanApprove(companyID,approval.getRootStateID(),approverID, rootType);
			if(approverPersonExport.getAuthorFlag() && 
					approverPersonExport.getApprovalAtr().equals(ApprovalBehaviorAtrExport.UNAPPROVED) && 
					!approverPersonExport.getExpirationAgentFlag() &&
					(approverPersonExport.getApprovalPhaseAtr()==ApprovalBehaviorAtrExport.UNAPPROVED || approverPersonExport.getApprovalPhaseAtr()==ApprovalBehaviorAtrExport.REMAND)){
				result = true;
				break;
			}else{
				result = false;
			}
		}*/
		//Phan code comment là xử lý cũ của RQ 190 - Update theo bug http://192.168.50.4:3000/issues/108043
		
		boolean result = approvalRootStateRepository.resultKTG002(startDate, endDate, approverID, rootType, companyID); 
		return result;
	}
	@Override
	// RequestList229
	public List<ApproveRootStatusForEmpExport> getApprovalByListEmplAndDate(GeneralDate startDate, GeneralDate endDate,
			List<String> employeeIDs, String companyID, Integer rootType) {
		List<ApproveRootStatusForEmpExport> result = new ArrayList<>();
		// 対象者と期間から承認ルートインスタンスを取得する
		List<ApprovalRootState> approvalRootSates = this.approvalRootStateRepository.findAppByListEmployeeIDRecordDate(startDate, endDate, employeeIDs, rootType);
		
		//承認ルート状況を取得する
		result = this.getApproveRootStatusForEmpExport(approvalRootSates);
		return result;
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
	public List<ApproveRootStatusForEmpExport> getApprovalByListEmplAndListApprovalRecordDate(
			List<GeneralDate> approvalRecordDates, List<String> employeeIDs, Integer rootType) {
		List<ApproveRootStatusForEmpExport> result = new ArrayList<>();
		// 対象者リストと日付リストから承認ルートインスタンスを取得する
		List<ApprovalRootState> approvalRootSates = this.approvalRootStateRepository.findAppByListEmployeeIDAndListRecordDate(approvalRecordDates, employeeIDs, rootType);
		
		//承認ルート状況を取得する
		result = this.getApproveRootStatusForEmpExport(approvalRootSates);
		return result;
	}
	@Override
	// requestList347
	public void registerApproval(String approverID, List<GeneralDate> approvalRecordDates, List<String> employeeIDs,
			Integer rootType,String companyID) {
		// 対象者リストと日付リストから承認ルートインスタンスを取得する
		List<ApprovalRootState> approvalRootSates = this.approvalRootStateRepository.findAppByListEmployeeIDAndListRecordDate(approvalRecordDates, employeeIDs, rootType);
		if(!CollectionUtil.isEmpty(approvalRootSates)){
			for(ApprovalRootState approvalRootState : approvalRootSates){
				 this.doApprove(approvalRootState.getRootStateID(), approverID, "");
			}
		}
	}
	@Override
	// requestList356
	public boolean releaseApproval(String approverID, List<GeneralDate> approvalRecordDates, List<String> employeeIDs,
			Integer rootType, String companyID) {
		boolean result = true;
		// 対象者リストと日付リストから承認ルートインスタンスを取得する
		List<ApprovalRootState> approvalRootSates = this.approvalRootStateRepository.findAppByListEmployeeIDAndListRecordDate(approvalRecordDates, employeeIDs, rootType);
		if(approvalRootSates != null){
			for(ApprovalRootState approvalRootState : approvalRootSates){
				result = this.doRelease(companyID, approvalRootState.getRootStateID(), approverID, rootType);
				if(!result){
					return result;
				}
			}
		}
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
				approvalFrame.getLstApproverInfo().forEach(approverInfor -> {
					approverInfor.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
					approverInfor.setAgentID(null);
					approverInfor.setApprovalDate(null);
					approverInfor.setApprovalReason(null);
				});
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
//		ApprovalPhaseState phase = ApprovalPhaseState.createFormTypeJava(param.getRootStateID(), param.getPhaseOrder(),
//				param.getApprovalAtr(),param.getApprovalForm(), 
//				param.getListApprovalFrame().stream().map(c->new ApprovalFrame(c.getRootStateID(),
//					c.getPhaseOrder(),
//					c.getFrameOrder(),
//					EnumAdaptor.valueOf(c.getApprovalAtr(), ApprovalBehaviorAtr.class),
//					EnumAdaptor.valueOf(c.getConfirmAtr(), ConfirmPerson.class),
//					c.getListApproverState().stream().map(x -> new ApproverInfor(x.getRootStateID(),
//						x.getPhaseOrder(),
//						x.getFrameOrder(),
//						x.getApproverID(),
//						x.getCompanyID(),
//						x.getDate())).collect(Collectors.toList()),
//					c.getApproverID(),
//					c.getRepresenterID(),
//					c.getApprovalDate(),
//					c.getApprovalReason())).collect(Collectors.toList()));
//		return judgmentApprovalStatusService.getApproverFromPhase(phase);
		return null;
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
    @Override
	public Map<String,List<ApprovalPhaseStateExport>> getApprovalRootCMM045(String companyID, String approverID, List<String> lstAgent, DatePeriod period,
			boolean unapprovalStatus, boolean approvalStatus, boolean denialStatus, 
			boolean agentApprovalStatus, boolean remandStatus, boolean cancelStatus){
		Map<String,List<ApprovalPhaseStateExport>> mapApprPhsStateEx = new LinkedHashMap<>();
		ApprovalRootContentOutput apprRootContentOut =  null;
		//ドメインモデル「承認ルートインスタンス」を取得する
		List<ApprovalRootState> approvalRootStates = approvalRootStateRepository.findEmploymentAppCMM045(approverID, lstAgent, period, 
				unapprovalStatus, approvalStatus, denialStatus, agentApprovalStatus, remandStatus, cancelStatus);
		if(approvalRootStates.isEmpty()){
			return mapApprPhsStateEx;
		}
		//hoatt 2018.12.14
		//EA修正履歴 No.3009
		//ドメインモデル「承認設定」を取得する
		Optional<PrincipalApprovalFlg> flg = repoApprSet.getPrincipalByCompanyId(companyID);
		List<ApprovalRootState> lstApprRootSttFil = new ArrayList<>();
		if(!flg.isPresent() || flg.get().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)){//本人による承認＝falseの場合
			//申請者ID＝ログイン社員IDのデータを取得した一覧から削除する(applicantID = loginID -> remove app)
			lstApprRootSttFil = approvalRootStates.stream().filter(c -> !c.getEmployeeID().equals(approverID))
					.collect(Collectors.toList());
		}else{
			lstApprRootSttFil = approvalRootStates;
		}
		if(lstApprRootSttFil.isEmpty()){
			return mapApprPhsStateEx;
		}
		for(ApprovalRootState approvalRootState :  lstApprRootSttFil){
			apprRootContentOut = new ApprovalRootContentOutput(approvalRootState, ErrorFlag.NO_ERROR);
			
			List<ApprovalPhaseStateExport> lstApprPhsStateEx = apprRootContentOut.getApprovalRootState().getListApprovalPhaseState()
						.stream()
						.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
						.map(x -> {
							return new ApprovalPhaseStateExport(
									x.getPhaseOrder(),
									EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
									EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
									x.getListApprovalFrame()
									.stream()
									.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
									.map(y -> {
										return new ApprovalFrameExport(
												y.getFrameOrder(), 
												y.getLstApproverInfo().stream().map(z -> {
													String approverName = "";
													String agentName = "";
													String representerID = "";
													String representerName = "";
													return new ApproverStateExport(
															z.getApproverID(), 
															EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
															z.getAgentID(),
															approverName, 
															agentName,
															representerID, 
															representerName,
															z.getApprovalDate(),
															z.getApprovalReason(),
															z.getApproverInListOrder());
												}).collect(Collectors.toList()), 
												y.getConfirmAtr().value,
												y.getAppDate());
									}).collect(Collectors.toList()));
						}).collect(Collectors.toList());
			mapApprPhsStateEx.put(approvalRootState.getRootStateID(), lstApprPhsStateEx);
		}
		return mapApprPhsStateEx;
    }
	@Override
	public List<ApprovalPhaseStateExport> getApprovalDetail(String appID) {
		String companyID = AppContexts.user().companyId();
		ApprovalRootState approvalRootState = approvalRootStateRepository.findEmploymentApp(appID).orElseThrow(()->
			new BusinessException("Msg_198")
		);
		return approvalRootState.getListApprovalPhaseState()
				.stream()
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
				.map(x -> {
					return new ApprovalPhaseStateExport(
							x.getPhaseOrder(),
							EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
							EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
							x.getListApprovalFrame()
							.stream()
							.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
							.map(y -> {
								return new ApprovalFrameExport(
										y.getFrameOrder(), 
										y.getLstApproverInfo().stream().map(z -> { 
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
											return new ApproverStateExport(
													z.getApproverID(), 
													EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
													z.getAgentID(),
													approverName, 
													personAdapter.getPersonInfo(z.getAgentID()).getEmployeeName(),
													representerID, 
													representerName,
													z.getApprovalDate(),
													z.getApprovalReason(),
													z.getApproverInListOrder());
										}).collect(Collectors.toList()), 
										y.getConfirmAtr().value,
										y.getAppDate());
							}).collect(Collectors.toList()));
				}).collect(Collectors.toList());
	}
	/**
     * [No.309]承認ルートを取得する
     * Phần đối ứng cho bên Jinji (人事)
     * 1.社員の対象申請の承認ルートを取得する
     * @param 会社ID companyID
     * @param 社員ID employeeID
     * @param ・対象申請 targetType
     * @param 基準日 date
     * @param Optional<下位序列承認無＞ lowerApprove
     * @return
     */
	@Override
	public ApprovalRootContentExport getApprovalRootHr(String companyID, String employeeID, String targetType, GeneralDate date, Optional<Boolean> lowerApprove) {
		ApprovalRootContentOutput approvalRootContentOutput = collectApprovalRootService.getApprovalRootOfSubjectRequest(
				companyID, 
				employeeID, 
				EmploymentRootAtr.NOTICE, 
				targetType, 
				date,
				SystemAtr.HUMAN_RESOURCES,
				Optional.empty());
		return new ApprovalRootContentExport(
				new ApprovalRootStateExport(
					approvalRootContentOutput.getApprovalRootState().getRootStateID(),
					approvalRootContentOutput.getApprovalRootState().getRootType().value,
					approvalRootContentOutput.getApprovalRootState().getApprovalRecordDate(),
					approvalRootContentOutput.getApprovalRootState().getEmployeeID(),
					approvalRootContentOutput.getApprovalRootState().getListApprovalPhaseState()
					.stream()
					.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
					.map(x -> {
						return new ApprovalPhaseStateExport(
								x.getPhaseOrder(),
								EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
								EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
								x.getListApprovalFrame()
								.stream()
								.sorted(Comparator.comparing(ApprovalFrame::getFrameOrder))
								.map(y -> {
									return new ApprovalFrameExport(
											y.getFrameOrder(), 
											y.getLstApproverInfo().stream().map(z -> { 
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
												return new ApproverStateExport(
														z.getApproverID(), 
														EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class),
														z.getAgentID(),
														approverName, 
														personAdapter.getPersonInfo(z.getAgentID()).getEmployeeName(),
														representerID, 
														representerName,
														z.getApprovalDate(),
														z.getApprovalReason(),
														z.getApproverInListOrder());
											}).collect(Collectors.toList()), 
											y.getConfirmAtr().value,
											y.getAppDate());
								}).collect(Collectors.toList()));
					}).collect(Collectors.toList())
				), 
				EnumAdaptor.valueOf(approvalRootContentOutput.getErrorFlag().value, ErrorFlagExport.class));
	}
	/**
	 * 11.　承認者の在職状態をチェック
	 * @param export
	 * @param date
	 * @return
	 */
	private ApprovalRootContentExport checkApproverStatus(ApprovalRootContentExport export, GeneralDate date) {
		for(ApprovalPhaseStateExport phaseStateExport : export.getApprovalRootState().getListApprovalPhaseState()) {
			for(ApprovalFrameExport frameExport : phaseStateExport.getListApprovalFrame()) {
				for(ApproverStateExport approverStateExport : frameExport.getListApprover()) {
					// 在職状態を取得(lấy trạng thái atwork)
					StatusOfEmployment statusOfApprover = employeeAdapter.getStatusOfEmployment(
							approverStateExport.getApproverID(), date).getStatusOfEmployment();
					// 承認者の在職状態をチェック(Check AtWorkStatus của approver)
					switch (statusOfApprover) {
					case RETIREMENT:
						approverStateExport.setApproverName(approverStateExport.getApproverName() + "（" + I18NText.getText("CCG001_49") + "）");
						break;
					case LEAVE_OF_ABSENCE:
						approverStateExport.setApproverName(approverStateExport.getApproverName() + "（" + I18NText.getText("CCG001_45") + "）");
						break;
					case HOLIDAY:
						approverStateExport.setApproverName(approverStateExport.getApproverName() + "（" + I18NText.getText("CCG001_47") + "）");
						break;
					default:
						break;
					}
					if(Strings.isNotBlank(approverStateExport.getRepresenterID())) {
						// 在職状態を取得(lấy trạng thái atwork)
						StatusOfEmployment statusOfRepresenter = employeeAdapter.getStatusOfEmployment(
								approverStateExport.getRepresenterID(), date).getStatusOfEmployment();
						// 承認者の在職状態をチェック(Check AtWorkStatus của approver)
						switch (statusOfRepresenter) {
						case RETIREMENT:
							approverStateExport.setRepresenterName(approverStateExport.getRepresenterName() + "（" + I18NText.getText("CCG001_49") + "）");
							break;
						case LEAVE_OF_ABSENCE:
							approverStateExport.setRepresenterName(approverStateExport.getRepresenterName() + "（" + I18NText.getText("CCG001_45") + "）");
							break;
						case HOLIDAY:
							approverStateExport.setRepresenterName(approverStateExport.getRepresenterName() + "（" + I18NText.getText("CCG001_47") + "）");
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return export;
	}
	@Override
	public void insertApp(String appID, GeneralDate appDate, String employeeID, List<ApprovalPhaseStateExport> listApprovalPhaseState) {
		ApprovalRootState approvalRootState = new ApprovalRootState(
				appID, 
				RootType.EMPLOYMENT_APPLICATION, 
				appDate, 
				employeeID, 
				listApprovalPhaseState.stream().map(x -> new ApprovalPhaseState(
						x.getPhaseOrder(), 
						EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtr.class), 
						EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalForm.class), 
						x.getListApprovalFrame().stream().map(y -> new ApprovalFrame(
								y.getFrameOrder(), 
								EnumAdaptor.valueOf(y.getConfirmAtr(), ConfirmPerson.class), 
								y.getAppDate(), 
								y.getListApprover().stream().map(z -> new ApproverInfor(
										z.getApproverID(), 
										EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtr.class), 
										null, 
										null, 
										null, 
										z.getApproverInListOrder()
								)).collect(Collectors.toList()))
						).collect(Collectors.toList()))
				).collect(Collectors.toList()));
		approvalRootStateRepository.insertApp(approvalRootState);
		
	}
	@Override
	public Map<String, List<ApprovalPhaseStateExport>> getApprovalPhaseByID(List<String> appIDLst) {
		Map<String, List<ApprovalPhaseState>> mapResult = approvalRootStateRepository.getApprovalPhaseByID(appIDLst);
		return mapResult.entrySet().stream().collect(Collectors.toMap(key -> key.getKey(), entry -> {
			return entry.getValue().stream().map(x -> new ApprovalPhaseStateExport(
					x.getPhaseOrder(), 
					EnumAdaptor.valueOf(x.getApprovalAtr().value, ApprovalBehaviorAtrExport.class), 
					EnumAdaptor.valueOf(x.getApprovalForm().value, ApprovalFormExport.class), 
					x.getListApprovalFrame().stream().map(y -> new ApprovalFrameExport(
							y.getFrameOrder(), 
							y.getLstApproverInfo().stream().map(z -> new ApproverStateExport(
									z.getApproverID(), 
									EnumAdaptor.valueOf(z.getApprovalAtr().value, ApprovalBehaviorAtrExport.class), 
									z.getAgentID(), 
									Strings.EMPTY, 
									Strings.EMPTY, 
									Strings.EMPTY, 
									Strings.EMPTY, 
									z.getApprovalDate(), 
									z.getApprovalReason(), 
									z.getApproverInListOrder())
									).collect(Collectors.toList()), 
							y.getConfirmAtr().value, 
							y.getAppDate())
							).collect(Collectors.toList()))
					).collect(Collectors.toList());
		}));
	}
}

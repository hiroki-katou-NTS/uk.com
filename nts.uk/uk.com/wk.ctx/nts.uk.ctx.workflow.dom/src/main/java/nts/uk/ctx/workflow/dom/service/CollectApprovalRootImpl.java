package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.SimpleJobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.StatusOfEmployment;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput.LevelApproverList;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput.LevelInforOutput.LevelApproverList.LevelApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.RepresenterInforOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class CollectApprovalRootImpl implements CollectApprovalRootService {
	
	@Inject
	private PersonApprovalRootRepository perApprovalRootRepository;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	@Inject
	private JobAssignSettingRepository jobAssignSetRepository;
	
	@Inject
	private WorkplaceApproverAdapter wkApproverAdapter;
	
	@Inject
	private JobtitleSearchSetRepository jobtitleSearchSetRepository;
	
	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;
	
	@Inject
	private WorkplaceApprovalRootRepository wkpApprovalRootRepository;
	
	@Inject
	private CompanyApprovalRootRepository comApprovalRootRepository;
	
	@Inject
	private ApprovalPhaseRepository approvalPhaseRepository;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Override
	public ApprovalRootContentOutput getApprovalRootOfSubjectRequest(String companyID, String employeeID, EmploymentRootAtr rootAtr, 
			ApplicationType appType, GeneralDate standardDate, SystemAtr sysAtr, Optional<Boolean> lowerApprove) {
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain「個人別就業承認ルート」)
		// ①個人別の該当申請の承認ルートを取得、なかったら②へ
		Optional<PersonApprovalRoot> opPerAppRoot = perApprovalRootRepository.findByBaseDate(companyID, employeeID, standardDate, appType, rootAtr, sysAtr.value);
		if(opPerAppRoot.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opPerAppRoot.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					listApprovalPhaseAfter, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		// ②個人別の共通の承認ルートを取得、なかったら③へ
		Optional<PersonApprovalRoot> opPerAppRootsOfCommon = perApprovalRootRepository.findByBaseDateOfCommon(companyID, employeeID, standardDate, sysAtr.value);
		if(opPerAppRootsOfCommon.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opPerAppRootsOfCommon.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					listApprovalPhaseAfter, opPerAppRootsOfCommon.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		List<String> wpkList = employeeAdapter.findWpkIdsBySid(companyID, employeeID, standardDate);
		for (String wｋｐId : wpkList) {
			Optional<WorkplaceApprovalRoot> opWkpAppRoot = wkpApprovalRootRepository.findByBaseDate(companyID, wｋｐId, standardDate, appType, rootAtr, sysAtr.value);
			if(opWkpAppRoot.isPresent()){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opWkpAppRoot.get().getApprovalId());
				/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
						listApprovalPhaseAfter, opWkpAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
				LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
				ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
				levelOutput.setErrorFlag(errorFlag.value);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
						levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
				return new ApprovalRootContentOutput(approvalRootState, errorFlag);
			}
			Optional<WorkplaceApprovalRoot> opWkpAppRootsOfCom = wkpApprovalRootRepository.findByBaseDateOfCommon(companyID, wｋｐId, standardDate, sysAtr.value);
			if(opWkpAppRootsOfCom.isPresent()){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opWkpAppRootsOfCom.get().getApprovalId());
				/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
						listApprovalPhaseAfter, opWkpAppRootsOfCom.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
				LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
				ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
				levelOutput.setErrorFlag(errorFlag.value);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
						levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
				return new ApprovalRootContentOutput(approvalRootState, errorFlag);
			}
		}
		
		Optional<CompanyApprovalRoot> opComAppRoot = comApprovalRootRepository.findByBaseDate(companyID, standardDate, appType, rootAtr, sysAtr.value);
		if(opComAppRoot.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opComAppRoot.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					listApprovalPhaseAfter, opComAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		Optional<CompanyApprovalRoot> opCompanyAppRootsOfCom = comApprovalRootRepository.findByBaseDateOfCommon(companyID, standardDate, sysAtr.value);
		if(opCompanyAppRootsOfCom.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opCompanyAppRootsOfCom.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					listApprovalPhaseAfter, opCompanyAppRootsOfCom.get().getApprRoot().getHistoryItems().get(0).getHistoryId());*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, sysAtr, lowerApprove);
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseList(companyID, standardDate,
					levelOutput, opPerAppRoot.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		return new ApprovalRootContentOutput(
				ApprovalRootState.builder().listApprovalPhaseState(Collections.emptyList()).build(), 
				ErrorFlag.NO_APPROVER);
	}
	
	@Override
	public List<ApprovalPhase> adjustmentData(String companyID, String employeeID, GeneralDate baseDate,  List<ApprovalPhase> listApprovalPhase) {
		listApprovalPhase.stream().forEach(phase -> {
			if(CollectionUtil.isEmpty(phase.getApprovers())){
				return;
			}

			// ドメインモデル「承認フェーズ」．承認形態をメモリ上保持する(luu thong tin 「承認フェーズ」．承認形態 vao cache) ??
			
			List<Approver> listApprover = new ArrayList<>();
			phase.getApprovers().sort(Comparator.comparing(Approver::getApproverOrder));
			ApprovalAtr appAtr = phase.getApprovalAtr();
			phase.getApprovers().forEach(approver -> {
				
				// 承認者IDリストをクリアする（初期化）(clear thong tin cua list ID nguoi xac nhan)
				if(appAtr.equals(ApprovalAtr.PERSON)){
					StatusOfEmployment statusOfEmployment = employeeAdapter.getStatusOfEmployment(approver.getEmployeeId(), baseDate).getStatusOfEmployment();
					if((statusOfEmployment==StatusOfEmployment.RETIREMENT)||
							(statusOfEmployment==StatusOfEmployment.LEAVE_OF_ABSENCE)||
							(statusOfEmployment==StatusOfEmployment.HOLIDAY)){
						return;
					}
					if(employeeAdapter.isEmployeeDelete(approver.getEmployeeId())){
						return;
					}
					if(listApprover.stream().filter(x -> x.getEmployeeId().equals(approver.getEmployeeId())).findAny().isPresent()){
						return;
					}
					listApprover.add(approver);
					return;
				}
				List<Approver> listApproverJob = this.convertPositionToApprover(companyID, employeeID, baseDate, approver.getJobGCD())
								.stream().map(x -> new Approver(
										approver.getApproverOrder(), 
										x.getJobGCD(), 
										x.getSid(), 
										approver.getConfirmPerson(),
										approver.getSpecWkpId()))
								.collect(Collectors.toList());
				if(CollectionUtil.isEmpty(listApproverJob)){
					return;
				}
				listApproverJob.forEach(x -> {
					if(listApprover.stream().filter(y -> y.getEmployeeId().equals(x.getEmployeeId())).findAny().isPresent()){
						return;
					}
					listApprover.add(x);
				});
			});
			phase.getApprovers().clear();
			if(CollectionUtil.isEmpty(listApprover)){
				return;
			}
			List<Approver> listRemoveApprover = new ArrayList<>();
			List<String> listApproverID = listApprover.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApproverID);
			listApprover.stream().forEach(x -> {
				approvalRepresenterOutput.getListApprovalAgentInfor().stream().filter(y -> y.getApprover().equals(x.getEmployeeId())).findAny().ifPresent(z -> {
					if(z.getRepresenter().getValue().equals(RepresenterInforOutput.Path_Information)){
						listRemoveApprover.add(x);
					}
				});;
			});
			listApprover.removeAll(listRemoveApprover);
			PrincipalApprovalFlg principalApprovalFlg = approvalSettingRepository.getPrincipalByCompanyId(companyID).orElse(PrincipalApprovalFlg.NOT_PRINCIPAL);
			if(principalApprovalFlg.equals(PrincipalApprovalFlg.NOT_PRINCIPAL)){
				List<Approver> listDeleteApprover = listApprover.stream().filter(x -> x.getEmployeeId().equals(employeeID)).collect(Collectors.toList());
				listApprover.removeAll(listDeleteApprover);
			}
			phase.addApproverList(listApprover);
		});
		return listApprovalPhase.stream().filter(x -> !CollectionUtil.isEmpty(x.getApprovers())).collect(Collectors.toList());
	}
	
	@Override
	public List<ApproverInfo> convertPositionToApprover(String companyID, String employeeID, GeneralDate baseDate, String jobTitleId) {
		// 共通アルゴリズム「申請者の職位の序列は承認者のと比較する」を実行する
		Boolean isApper = compareHierarchyTargetPerson(companyID, employeeID, jobTitleId, baseDate);
		if(isApper.equals(Boolean.FALSE)){
			return Collections.emptyList();
		}
		WorkplaceImport wkpInfor = wkApproverAdapter.findBySid(employeeID, baseDate);
		// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
		List<ApproverInfo> approvers = this.getPersonByWorkplacePosition(companyID, wkpInfor.getWkpId(), baseDate, jobTitleId);
		if (!CollectionUtil.isEmpty(approvers)) {
			return approvers;
		}

		// lấy domain 「職位別のサーチ設定」
		Boolean needsSearch = this.jobtitleSearchSetRepository.finById(companyID, jobTitleId)
					.map(s -> s.needsSearch())
						.orElse(false);
		if (needsSearch.equals(Boolean.FALSE)) {
			return Collections.emptyList();
		}
		List<String> wkpIds = this.employeeAdapter.findWpkIdsBySid(companyID, employeeID, baseDate);
		wkpIds.remove(0);

		// 上位職場が存在する(not exist wkpId 上位)
		if (CollectionUtil.isEmpty(wkpIds)) {
			return Collections.emptyList();
		}

		// 上位職場の先頭から最後ループ
		for (String id : wkpIds) {
			// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
			List<ApproverInfo> approversByWkp = this.getPersonByWorkplacePosition(companyID, id, baseDate, jobTitleId);
			// If exist break and return
			if (!CollectionUtil.isEmpty(approversByWkp)) {
				return approversByWkp;
			}
		}
		return Collections.emptyList();
	}
	
	@Override
	public Boolean compareHierarchyTargetPerson(String companyID, String targetPersonID, String positionID,
			GeneralDate date) {
		JobTitleImport jobOfEmp = syJobTitleAdapter.findJobTitleBySid(targetPersonID, date);
		SimpleJobTitleImport approverInfo = null;
		SimpleJobTitleImport requestInfo = null;
		// 承認者の
		List<SimpleJobTitleImport> approverInfoList = syJobTitleAdapter.findByIds(companyID, Arrays.asList(positionID), date);
		if(!CollectionUtil.isEmpty(approverInfoList)){
			approverInfo = approverInfoList.get(0);
		}		
		if (approverInfo != null) {
			// 申請の
			List<SimpleJobTitleImport> requestInfoList = syJobTitleAdapter
					.findByIds(companyID, Arrays.asList(jobOfEmp.getPositionId()), date);
			if(!CollectionUtil.isEmpty(requestInfoList)){
				requestInfo = requestInfoList.get(0);
			}
			if (requestInfo == null) {
				return false;
			}
			if((requestInfo.getDisporder()==null) || (approverInfo.getDisporder()==null)){
				return true;
			}
			if (requestInfo.getDisporder() > approverInfo.getDisporder()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<ApproverInfo> getPersonByWorkplacePosition(String cid, String wkpId, GeneralDate baseDate, String jobTitleId) {
		// 承認者の
		List<ConcurrentEmployeeImport> employeeList = employeeAdapter.getConcurrentEmployee(cid, jobTitleId,
				baseDate);
		Optional<JobAssignSetting> assignSet = jobAssignSetRepository.findById();
		if (assignSet.get().getIsConcurrently()) {
			// 本務兼務区分が兼務の対象者を除く
			employeeList.removeIf(x -> x.isConcurrent());
		}
		
		List<ApproverInfo> approvers = new ArrayList<>();
		for (ConcurrentEmployeeImport emp : employeeList) {
			StatusOfEmployment statusOfEmployment = employeeAdapter.getStatusOfEmployment(emp.getEmployeeId(), baseDate).getStatusOfEmployment();
			if((statusOfEmployment==StatusOfEmployment.RETIREMENT)||
					(statusOfEmployment==StatusOfEmployment.LEAVE_OF_ABSENCE)||
					(statusOfEmployment==StatusOfEmployment.HOLIDAY)){
				continue;
			}
			WorkplaceImport wkpIdOfEmp = wkApproverAdapter.findBySid(emp.getEmployeeId(), baseDate);
			if (wkpId.equals(wkpIdOfEmp.getWkpId())) {
				// truyền tạm approvalAtr = 1
				approvers.add(ApproverInfo.create(emp));
			}
		}
		return approvers;
	}
	
	@Override
	public ErrorFlag checkApprovalRoot(List<ApprovalPhase> listApprovalPhaseBefore, List<ApprovalPhase> listApprovalPhaseAfter) {
		if(CollectionUtil.isEmpty(listApprovalPhaseBefore)|CollectionUtil.isEmpty(listApprovalPhaseAfter)){
			return ErrorFlag.NO_APPROVER;
		}
		for(int i = 0; i < listApprovalPhaseBefore.size(); i++){
			ApprovalPhase approvalPhaseBefore = listApprovalPhaseBefore.get(i);
			Optional<ApprovalPhase> opApprovalPhase = listApprovalPhaseAfter.stream().filter(x -> x.getPhaseOrder() == approvalPhaseBefore.getPhaseOrder()).findAny();
			if(!opApprovalPhase.isPresent()){
				continue;
			}
			ApprovalPhase approvalPhaseAfter = opApprovalPhase.get();
			if(CollectionUtil.isEmpty(approvalPhaseBefore.getApprovers())){
				continue;
			}
			if(approvalPhaseAfter.getApprovers().size() > 10){
				return ErrorFlag.APPROVER_UP_10;
			}
			if(approvalPhaseAfter.getApprovers().size() < 0){
				return ErrorFlag.NO_APPROVER;
			}
			if(approvalPhaseBefore.getApprovalForm().equals(ApprovalForm.EVERYONE_APPROVED)){
				continue;
			}
			for(Approver approver : approvalPhaseBefore.getApprovers()){
				if(approver.getConfirmPerson().equals(ConfirmPerson.NOT_CONFIRM)){
					continue;
				}
				if(CollectionUtil.isEmpty(approvalPhaseAfter.getApprovers())){
					return ErrorFlag.NO_CONFIRM_PERSON;
				}
			}
		}
		return ErrorFlag.NO_ERROR;
	}
	
	@Override
	public void getApprovalRootBy36AppEmployee(String companyID, String employeeID, GeneralDate date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> organizeBrowsingPhase(String companyID, String employeeID, GeneralDate date, ApprovalPhase phase) {
		List<String> viewList = new ArrayList<>();
		if(phase.getBrowsingPhase()==0){
			return viewList;
		}
		ApprovalAtr appAtr = phase.getApprovalAtr();
		phase.getApprovers().forEach(approver -> {
			if(appAtr.equals(ApprovalAtr.PERSON)){
				viewList.add(approver.getEmployeeId());
			} else {
				List<String> approvers = this.convertPositionToApprover(companyID, employeeID, date, approver.getJobGCD())
											.stream().map(x -> x.getSid()).collect(Collectors.toList());
				viewList.addAll(approvers);
			}
		});
		return viewList.stream().distinct().collect(Collectors.toList());
	}

	private ApprovalRootState createFromApprovalPhaseList(String companyID, GeneralDate date,
			LevelOutput levelOutput, String histotyID) {
		List<ApprovalPhaseState> listApprovalPhaseState = levelOutput.getLevelInforLst().stream().map(levelInforLst -> {
			List<ApprovalFrame> resultApprovalFrame = levelInforLst.getApproverLst().stream().map(levelApproverList -> {
				List<ApproverInfor> lstApproverInfo = levelApproverList.getApproverInfoLst()
					.stream().map(approverInfo -> new ApproverInfor(
							approverInfo.getApproverID(), 
							ApprovalBehaviorAtr.UNAPPROVED, 
							"", 
							null, 
							"")).collect(Collectors.toList());
				return ApprovalFrame.convert(
						levelApproverList.getOrder(), 
						levelApproverList.isComfirmAtr() ? 1 : 0, 
						date, 
						lstApproverInfo);
			}).collect(Collectors.toList());
			return ApprovalPhaseState.createFormTypeJava(
					levelInforLst.getLevelNo(), 
					ApprovalBehaviorAtr.UNAPPROVED.value, 
					levelInforLst.getApprovalForm(), 
					resultApprovalFrame);
		}).collect(Collectors.toList());	
		return ApprovalRootState.builder()
				.historyID(histotyID)
				.listApprovalPhaseState(listApprovalPhaseState)
				.build();
			/*
			List<ApprovalFrame> listApprovalFrameByPerson = levelInforLst.getApprovers().stream()
			.filter(approver -> Strings.isBlank(approver.getJobGCD()))
			.map(approver -> {
				List<ApproverInfor> listApproverState = new ArrayList<>();
				listApproverState.add(
						new ApproverInfor(
								approver.getEmployeeId(),
								ApprovalBehaviorAtr.UNAPPROVED, 
								"", 
								null,
								""));
				return new ApprovalFrame(
						approver.getApproverOrder(), 
						approver.getConfirmPerson(),
						date,
						listApproverState);
			}).collect(Collectors.toList());
			resultApprovalFrame.addAll(listApprovalFrameByPerson);
			
			List<Approver> allListApproverByJob = approvalPhase.getApprovers().stream()
			.filter(approver -> !Strings.isBlank(approver.getJobGCD()))
			.collect(Collectors.toList());
			Map<String, List<Approver>> findMap = allListApproverByJob.stream()
					.collect(Collectors.groupingBy(Approver::getJobGCD));
			findMap.forEach((k,v) -> {
				List<ApproverInfor> listApproverStateByJob = v.stream()
						.map(approver -> new ApproverInfor(
								approver.getEmployeeId(),
								ApprovalBehaviorAtr.UNAPPROVED, 
								"", 
								null,
								""))
						.collect(Collectors.toList()); 
				if(!CollectionUtil.isEmpty(listApproverStateByJob)){
					ApprovalFrame approvalFrameByJob = 
							new ApprovalFrame(
									v.get(0).getApproverOrder(), 
									v.get(0).getConfirmPerson(), 
									date, 
									listApproverStateByJob);
					resultApprovalFrame.add(approvalFrameByJob);
				}
			});
			resultApprovalFrame.sort((a,b) -> a.getFrameOrder() - b.getFrameOrder());
			ApprovalPhaseState approvalPhaseState = new ApprovalPhaseState(
					approvalPhase.getPhaseOrder(), 
					ApprovalBehaviorAtr.UNAPPROVED, 
					EnumAdaptor.valueOf(approvalPhase.getApprovalForm().value, ApprovalForm.class), 
					resultApprovalFrame);
			return approvalPhaseState;
		}).sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
				.collect(Collectors.toList());
		return ApprovalRootState.builder()
				.historyID(histotyID)
				.listApprovalPhaseState(listApprovalPhaseState)
				.build();
				*/
	}
	
	private ApprovalRootState createFromApprovalPhaseListConfirm(String companyID, GeneralDate date,
			LevelOutput levelOutput, String histotyID) {
		List<ApprovalPhaseState> listApprovalPhaseState = levelOutput.getLevelInforLst().stream().map(levelInforLst -> {
			List<ApprovalFrame> resultApprovalFrame = new ArrayList<>();
			levelInforLst.getApproverLst().forEach(levelApproverList -> {
				List<ApprovalFrame> approvalFrameLst = levelApproverList.getApproverInfoLst()
					.stream().map(approverInfo -> {
						ApproverInfor approverInfor = new ApproverInfor(
							approverInfo.getApproverID(), 
							ApprovalBehaviorAtr.UNAPPROVED, 
							"", 
							null, 
							"");
						return ApprovalFrame.convert(
								levelApproverList.getOrder(), 
								levelApproverList.isComfirmAtr() ? 1 : 0, 
								date, 
								Arrays.asList(approverInfor));
					}).collect(Collectors.toList());
				resultApprovalFrame.addAll(approvalFrameLst);
			});
			return ApprovalPhaseState.createFormTypeJava(
					levelInforLst.getLevelNo(), 
					ApprovalBehaviorAtr.UNAPPROVED.value, 
					levelInforLst.getApprovalForm(), 
					resultApprovalFrame);
		}).collect(Collectors.toList());	
		return ApprovalRootState.builder()
				.historyID(histotyID)
				.listApprovalPhaseState(listApprovalPhaseState)
				.build();
	}

	@Override
	public ApprovalRootContentOutput getApprovalRootConfirm(String companyID, String employeeID,
			ConfirmationRootType confirmAtr, GeneralDate standardDate) {
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain「個人別就業承認ルート」)
		// ①個人別の該当申請の承認ルートを取得、なかったら②へ
		List<PersonApprovalRoot> perAppRootList = perApprovalRootRepository.findEmpByConfirm(companyID, employeeID, confirmAtr, standardDate);
		if(!CollectionUtil.isEmpty(perAppRootList)){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(perAppRootList.get(0).getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
					levelOutput, perAppRootList.get(0).getApprRoot().getHistoryItems().get(0).getHistoryId());
			if(errorFlag == ErrorFlag.NO_ERROR){
				String appID = IdentifierUtil.randomUniqueId();
				approvalRootState = ApprovalRootState.createFromFirst(
						companyID,
						appID,  
						EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
						approvalRootState.getHistoryID(), 
						standardDate, 
						employeeID, 
						approvalRootState);
				// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
			}
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		// ②個人別の共通の承認ルートを取得、なかったら③へ
		Optional<PersonApprovalRoot> opPerAppRootsOfCommon;
		try {
			opPerAppRootsOfCommon = perApprovalRootRepository.findByBaseDateOfCommon(companyID, employeeID, standardDate, SystemAtr.WORK.value);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApprovalRootContentOutput(ApprovalRootState.builder().listApprovalPhaseState(Collections.emptyList()).build(), ErrorFlag.ABNORMAL_TERMINATION);
		}
		if(opPerAppRootsOfCommon.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opPerAppRootsOfCommon.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
					levelOutput, opPerAppRootsOfCommon.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			if(errorFlag.equals(ErrorFlag.NO_ERROR)){
				String appID = IdentifierUtil.randomUniqueId();
				approvalRootState = ApprovalRootState.createFromFirst(
						companyID,
						appID,  
						EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
						approvalRootState.getHistoryID(), 
						standardDate, 
						employeeID, 
						approvalRootState);
				// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
			}
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		List<String> wpkList = employeeAdapter.findWpkIdsBySid(companyID, employeeID, standardDate);
		for (String wｋｐId : wpkList) {
			List<WorkplaceApprovalRoot> wkpAppRootList = wkpApprovalRootRepository.findEmpByConfirm(companyID, wｋｐId, confirmAtr, standardDate);
			if(!CollectionUtil.isEmpty(wkpAppRootList)){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(wkpAppRootList.get(0).getApprovalId());
				/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
				LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
				ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
				levelOutput.setErrorFlag(errorFlag.value);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
						levelOutput, wkpAppRootList.get(0).getApprRoot().getHistoryItems().get(0).getHistoryId());
				if(errorFlag.equals(ErrorFlag.NO_ERROR)){
					String appID = IdentifierUtil.randomUniqueId();
					approvalRootState = ApprovalRootState.createFromFirst(
							companyID,
							appID,  
							EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
							approvalRootState.getHistoryID(), 
							standardDate, 
							employeeID, 
							approvalRootState);
					// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
				}
				return new ApprovalRootContentOutput(approvalRootState, errorFlag);
			}
			Optional<WorkplaceApprovalRoot> opWkpAppRootsOfCom;
			try {
				opWkpAppRootsOfCom = wkpApprovalRootRepository.findByBaseDateOfCommon(companyID, wｋｐId, standardDate, SystemAtr.WORK.value);
			} catch (Exception e) {
				e.printStackTrace();
				return new ApprovalRootContentOutput(ApprovalRootState.builder().listApprovalPhaseState(Collections.emptyList()).build(), ErrorFlag.ABNORMAL_TERMINATION);
			}
			if(opWkpAppRootsOfCom.isPresent()){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opWkpAppRootsOfCom.get().getApprovalId());
				/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
				LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
				ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
				levelOutput.setErrorFlag(errorFlag.value);
				ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
						levelOutput, opWkpAppRootsOfCom.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
				if(errorFlag.equals(ErrorFlag.NO_ERROR)){
					String appID = IdentifierUtil.randomUniqueId();
					approvalRootState = ApprovalRootState.createFromFirst(
							companyID,
							appID,  
							EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
							approvalRootState.getHistoryID(), 
							standardDate, 
							employeeID, 
							approvalRootState);
					// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
				}
				return new ApprovalRootContentOutput(approvalRootState, errorFlag);
			}
		}
		
		List<CompanyApprovalRoot> comAppRootList = comApprovalRootRepository.findEmpByConfirm(companyID, confirmAtr, standardDate);
		if(!CollectionUtil.isEmpty(comAppRootList)){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(comAppRootList.get(0).getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
					levelOutput, comAppRootList.get(0).getApprRoot().getHistoryItems().get(0).getHistoryId());
			if(errorFlag.equals(ErrorFlag.NO_ERROR)){
				String appID = IdentifierUtil.randomUniqueId();
				approvalRootState = ApprovalRootState.createFromFirst(
						companyID,
						appID,  
						EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
						approvalRootState.getHistoryID(), 
						standardDate, 
						employeeID, 
						approvalRootState);
				// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
			}
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		
		Optional<CompanyApprovalRoot> opCompanyAppRootsOfCom;
		try {
			opCompanyAppRootsOfCom = comApprovalRootRepository.findByBaseDateOfCommon(companyID, standardDate, SystemAtr.WORK.value);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApprovalRootContentOutput(ApprovalRootState.builder().listApprovalPhaseState(Collections.emptyList()).build(), ErrorFlag.ABNORMAL_TERMINATION);
		}
		if(opCompanyAppRootsOfCom.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(opCompanyAppRootsOfCom.get().getApprovalId());
			/*List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);*/
			LevelOutput levelOutput = this.organizeApprovalRoute(companyID, employeeID, standardDate, listApprovalPhaseBefore, SystemAtr.WORK, Optional.empty());
			ErrorFlag errorFlag = this.checkApprovalRoot(levelOutput);
			levelOutput.setErrorFlag(errorFlag.value);
			ApprovalRootState approvalRootState = this.createFromApprovalPhaseListConfirm(companyID, standardDate,
					levelOutput, opCompanyAppRootsOfCom.get().getApprRoot().getHistoryItems().get(0).getHistoryId());
			if(errorFlag.equals(ErrorFlag.NO_ERROR)){
				String appID = IdentifierUtil.randomUniqueId();
				approvalRootState = ApprovalRootState.createFromFirst(
						companyID,
						appID,  
						EnumAdaptor.valueOf(confirmAtr.value+1, RootType.class), 
						approvalRootState.getHistoryID(), 
						standardDate, 
						employeeID, 
						approvalRootState);
				// approvalRootStateRepository.insert(companyID, approvalRootState,RootType.CONFIRM_WORK_BY_DAY.value);
			}
			return new ApprovalRootContentOutput(approvalRootState, errorFlag);
		}
		return new ApprovalRootContentOutput(ApprovalRootState.builder().listApprovalPhaseState(Collections.emptyList()).build(), ErrorFlag.NO_APPROVER);
	}
	
	@Override
	public LevelOutput organizeApprovalRoute(String companyID, String employeeID, GeneralDate baseDate,
			List<ApprovalPhase> listApprovalPhase, SystemAtr systemAtr, Optional<Boolean> lowerApprove) {
		// 社員と基準日から所属職場履歴項目を取得する(Lấy AffWorkpaceHistoryItem từ employee và BaseDate)
		String empWkpID = wkApproverAdapter.findBySid(employeeID, baseDate).getWkpId();
		// 申請者の対象申請の承認者一覧を作成(Tạp ApproverList của TargerApplication của Applicant)
		LevelOutput result = new LevelOutput(0, new ArrayList<>());
		// ドメインモデル「承認フェーズ」．順序１～５ループする(loop tu Approval phase1denApproval phase5)
		listApprovalPhase.sort((a,b) -> a.getPhaseOrder() - b.getPhaseOrder());
		for(ApprovalPhase approvalPhase : listApprovalPhase) {
			// ループ中の承認フェーズに承認者を設定したかチェックする(check xem Approval phase dang xu ly co duoc cai dat nguoi xac nhan hay khong)
			if(CollectionUtil.isEmpty(approvalPhase.getApprovers())) {
				continue;
			}
			// レベル情報を作成(tạo thông tin level)
			LevelInforOutput levelInforOutput = new LevelInforOutput(
					approvalPhase.getPhaseOrder(), 
					approvalPhase.getApprovalForm().value, 
					approvalPhase.getApprovalAtr().value, 
					new ArrayList<>());
			// ドメインモデル「承認フェーズ」．承認者１～５ループする(loop tu Approval phase1denApproval phase5)
			approvalPhase.getApprovers().sort((a,b) -> a.getApproverOrder() - b.getApproverOrder());
			for(Approver approver : approvalPhase.getApprovers()) {
				// 承認者一覧を作成(Tạo ApproverList)
				LevelApproverList levelApproverList = new LevelApproverList(
						approver.getApproverOrder(), 
						"", 
						approver.getConfirmPerson().value!=0?true:false, 
						new ArrayList<>());
				List<ApproverInfo> approverInfoLst = new ArrayList<>();
				// ループ中の承認フェーズ．承認者指定区分をチェック(Check ApprovalPhase.ApproverSettingAtr đang loop)
				if(approvalPhase.getApprovalAtr()==ApprovalAtr.PERSON) {
					approverInfoLst.add(new ApproverInfo(
							"", 
							approver.getEmployeeId(), 
							approver.getApproverOrder(), 
							approver.getConfirmPerson().value!=0?true:false, 
							""));
				} else {
					// 社員IDと基準日から職位情報を取得(Lấy thông tin position từ EmployeeID và BaseDate)
					JobTitleImport jobOfEmp = syJobTitleAdapter.findJobTitleBySid(employeeID, baseDate);
					// 職位IDから序列の並び順を取得(Lấy thứ tự sắp xếp theo cấp bậc từ postionID)
					Optional<Integer> opDispOrder = this.getDisOrderFromJobID(jobOfEmp.getPositionId(), companyID, baseDate);
					// 承認者グループから承認者を取得(Lấy approver từ ApproverGroup)
					approverInfoLst = getApproverFromGroup(
							companyID, 
							approver.getJobGCD(), 
							approver.getSpecWkpId(), 
							empWkpID, 
							opDispOrder, 
							employeeID, 
							baseDate, 
							systemAtr, 
							lowerApprove);
					if(CollectionUtil.isEmpty(approverInfoLst)) {
						approverInfoLst = this.getUpperApproval(
								companyID, 
								approver.getJobGCD(), 
								empWkpID, 
								opDispOrder, 
								employeeID, 
								baseDate, 
								systemAtr, 
								lowerApprove, 
								approvalPhase.getApprovalAtr());
					}
					if(!CollectionUtil.isEmpty(approverInfoLst)) {
						levelApproverList.setApproverInfoLst(this.adjustApprover(approverInfoLst, baseDate, companyID, employeeID));
						levelInforOutput.getApproverLst().add(levelApproverList);
						break;
					}
				}
				levelApproverList.setApproverInfoLst(this.adjustApprover(approverInfoLst, baseDate, companyID, employeeID));
				levelInforOutput.getApproverLst().add(levelApproverList);
			}
			result.getLevelInforLst().add(levelInforOutput);
		}
		return result;
	}
	
	@Override
	public Optional<Integer> getDisOrderFromJobID(String jobID, String companyID, GeneralDate baseDate) {
		Optional<Integer> opDispOrder = Optional.empty();
		List<SimpleJobTitleImport> requestInfoList = syJobTitleAdapter
				.findByIds(companyID, Arrays.asList(jobID), baseDate);
		if(!CollectionUtil.isEmpty(requestInfoList)){
			opDispOrder = Optional.ofNullable(requestInfoList.get(0).getDisporder());
		}
		return opDispOrder;
	}

	@Override
	public List<ApproverInfo> getApproverFromGroup(String companyID, String approverGroupCD, String specWkpId, String empWkpID,
			Optional<Integer> opDispOrder, String employeeID, GeneralDate baseDate, SystemAtr systemAtr, Optional<Boolean> lowerApprove) {
		List<ApproverInfo> result = new ArrayList<>();
		// 承認者Gコードから職位情報を取得(Lấy thông tin position từ ApproverGCode)
		List<String> jobIDLst = syJobTitleAdapter.getJobIDFromGroup(companyID, approverGroupCD);
		// 取得したList＜職位ID＞をループ(Loop List<PositionID> đã lấy)
		for(String jobID : jobIDLst) {
			// Input．特定職場IDをチェック(Check Input. SepecificWorkplaceID)
			if(Strings.isBlank(specWkpId)) {
				// 申請者より、下の職位の承認者とチェック(Check Approver có chức vụ thấp hơn người làm đơn)
				boolean getFlag = this.checkApproverApplicantOrder(systemAtr, jobID, opDispOrder, lowerApprove, companyID, baseDate);
				if(!getFlag) {
					continue;
				}
			}
			// 6.職場に指定する職位の対象者を取得する(getPersonByWorkplacePosition)
			List<ApproverInfo> approverInfoLoopLst = this.getPersonByWorkplacePosition(
					companyID, 
					Strings.isNotBlank(specWkpId) ? specWkpId : empWkpID, 
					baseDate, 
					jobID);
			// 承認者リストに取得した承認者を追加(THêm Approver đã lấy vào ApproverList)
			result.addAll(approverInfoLoopLst);
		}
		return result;
	}

	@Override
	public boolean checkApproverApplicantOrder(SystemAtr systemAtr, String jobID, Optional<Integer> opDispOrder, 
			Optional<Boolean> lowerApprove, String companyID, GeneralDate baseDate) {
		// 取得フラグ　＝　True(GetFlag = True)
		boolean result = true;
		// Input．システム区分をチェック(Check Input. SystemType)
		if(systemAtr==SystemAtr.HUMAN_RESOURCES) {
			// Input．下位序列承認無をチェック(Check ''ko approve cấp bậc thấp hơn'')
			if(!lowerApprove.get()) {
				return result;
			}
		}
		// Optional<申請者の序列の並び順＞をチェック(Check Optional<thứ tự sắp xếp theo cấp bậc của người làm đơn＞)
		if(!opDispOrder.isPresent()) {
			return result;
		}
		// 職位IDから序列の並び順を取得(Lất thứ tự sắp xếp theo cấp bậc từ PositionID)
		List<SimpleJobTitleImport> simpleJobTitleImportList = syJobTitleAdapter.findByIds(companyID, Arrays.asList(jobID), baseDate);
		// 取得したOptional<並び順＞をチェック(Check Optional<order＞ đã lấy)
		if(CollectionUtil.isEmpty(simpleJobTitleImportList)){
			return result;
		}	
		// 序列の並び順を比較(So sánh thứ tự sắp xép của cấp bậc)
		Integer jobOrder = simpleJobTitleImportList.get(0).getDisporder();
		if(jobOrder >= opDispOrder.get()) {
			return result;
		}
		// 取得フラグ　＝　False(GetFlag = False)
		result = false;
		return result;
	}

	@Override
	public List<LevelApproverInfo> adjustApprover(List<ApproverInfo> approverInfoLst, GeneralDate baseDate, String companyID, String employeeID) {
		List<LevelApproverInfo> result = new ArrayList<>();
		// 承認者の在職状態と承認権限をチェック(Check trạng thái atwork và quyền approval của người approve)
		List<ApproverInfo> approverInfoAfterLst = this.checkApproverStatusAndAuthor(approverInfoLst, baseDate, companyID);
		// 取得した承認者リストをチェック(Check ApproverList đã lấy)
		if(CollectionUtil.isEmpty(approverInfoAfterLst)) {
			return Collections.emptyList();
		}
		for(ApproverInfo approverInfo : approverInfoAfterLst) {
			// 承認代行情報の取得処理(xử lý lấy thông tin đại diện approve)
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(
					companyID, 
					Arrays.asList(approverInfo.getSid()));
			if(!approvalRepresenterOutput.getListApprovalAgentInfor().get(0).isPass()) {
				String representerID = "";
				if(!CollectionUtil.isEmpty(approvalRepresenterOutput.getListAgent())) {
					representerID = approvalRepresenterOutput.getListAgent().get(0);
				}
				// Output．承認者一覧に取得した承認者の代行情報リストを追加(Them list thông tin đại diện của người approve đã lấy vào Output.ApproverList)
				result.add(new LevelApproverInfo(approverInfo.getSid(), representerID));
			}
		}
		// ドメインモデル「承認設定」．本人による承認をチェックする(Check domain「承認設定」. 本人による承認 )
		PrincipalApprovalFlg principalApprovalFlg = approvalSettingRepository.getPrincipalByCompanyId(companyID).orElse(PrincipalApprovalFlg.NOT_PRINCIPAL);
		if(principalApprovalFlg.equals(PrincipalApprovalFlg.NOT_PRINCIPAL)){
			// 申請本人社員IDを承認者IDリストから消す(Xóa 申請本人社員ID từ ApproverIDList)
			List<LevelApproverInfo> listDeleteApprover = result.stream().filter(x -> x.getApproverID().equals(employeeID)).collect(Collectors.toList());
			result.removeAll(listDeleteApprover);
		}
		return result;
	}

	@Override
	public List<ApproverInfo> checkApproverStatusAndAuthor(List<ApproverInfo> approverInfoLst, GeneralDate baseDate, String companyID) {
		// 承認者リストをループ(Loop ApproverList)
		for(ApproverInfo approverInfo : approverInfoLst) {
			// 在職状態を取得(lấy trạng thái atwork)
			StatusOfEmployment statusOfEmployment = employeeAdapter.getStatusOfEmployment(approverInfo.getSid(), baseDate).getStatusOfEmployment();
			// 承認者の在職状態をチェック(Check AtWorkStatus của approver)
			if(!((statusOfEmployment==StatusOfEmployment.RETIREMENT)||
					(statusOfEmployment==StatusOfEmployment.LEAVE_OF_ABSENCE)||
					(statusOfEmployment==StatusOfEmployment.HOLIDAY))){
				// 指定社員が基準日に承認権限を持っているかチェックする(Check xem employee chỉ định có quyền approve ở thời điểm baseDate hay ko)
				boolean canApproval = employeeAdapter.canApprovalOnBaseDate(companyID, approverInfo.getSid(), baseDate);
				// 取得した権限状態をチェック(Check trạng thái quyền hạn đã lấy)
				if(canApproval) {
					continue;
				}
			}
			// 承認者リストにループ中の承認者を除く(Xóa approver đang loop trong ApproverList)
			approverInfoLst.remove(approverInfo);
		}
		return approverInfoLst;
	}

	@Override
	public List<ApproverInfo> getUpperApproval(String companyID, String approverGroupCD, String empWkpID, Optional<Integer> opDispOrder, 
			String employeeID, GeneralDate baseDate, SystemAtr systemAtr, Optional<Boolean> lowerApprove, ApprovalAtr approvalAtr) {
		// Input．承認者指定区分をチェック(Check Input . ApproverSettingAtr)
		if(approvalAtr!=ApprovalAtr.APPROVER_GROUP) {
			return Collections.emptyList();
		}
		/*
		Boolean needsSearch = this.jobtitleSearchSetRepository.finById(companyID, jobTitleId)
				.map(s -> s.needsSearch()).orElse(false);
		if (needsSearch.equals(Boolean.FALSE)) {
			return Collections.emptyList();
		}
		*/
		// 職場IDと基準日から上位職場を取得する ( Acquire upper workplace from workplace ID )
		List<String> upperWkpIDLst = this.employeeAdapter.findWpkIdsBySid(companyID, employeeID, baseDate);
		for(String upperWkpID : upperWkpIDLst) {
			// 承認者グループから承認者を取得(Lấy Approver từ ApproverGroup)
			List<ApproverInfo> approverInfoLst = this.getApproverFromGroup(companyID, approverGroupCD, "", upperWkpID, opDispOrder, employeeID, baseDate, systemAtr, lowerApprove);
			// 取得した承認者リストをチェック(Check ApproverList đã  lấy)
			if(!CollectionUtil.isEmpty(approverInfoLst)) {
				return approverInfoLst;
			}
		}
		return Collections.emptyList();
	}
	
	@Override
	public ErrorFlag checkApprovalRoot(LevelOutput levelOutput) {
		List<LevelInforOutput> levelInforLst = levelOutput.getLevelInforLst();
		if(CollectionUtil.isEmpty(levelInforLst)){
			return ErrorFlag.NO_APPROVER;
		}
		for(LevelInforOutput levelInforOutput : levelInforLst) {
			if(levelInforOutput.getApproverLst().size() > 10) {
				return ErrorFlag.APPROVER_UP_10;
			} 
			if(levelInforOutput.getApproverLst().size() <= 0) {
				return ErrorFlag.NO_APPROVER;
			}
			if(levelInforOutput.getApprovalForm() == ApprovalForm.EVERYONE_APPROVED.value){
				continue;
			}
			for(LevelApproverList levelApproverList : levelInforOutput.getApproverLst()){
				if(!levelApproverList.isComfirmAtr()){
					continue;
				}
				if(CollectionUtil.isEmpty(levelApproverList.getApproverInfoLst())){
					return ErrorFlag.NO_CONFIRM_PERSON;
				}
			}
		}
		return ErrorFlag.NO_ERROR;
	}
}

package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.JobtitleToApproverService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalPhaseOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalForm;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.dom.service.output.RepresenterInforOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
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
	private JobtitleToApproverService jobtitleToAppService;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Override
	public ApprovalRootContentOutput getApprovalRootOfSubjectRequest(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate standardDate) {
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain「個人別就業承認ルート」)
		// ①個人別の該当申請の承認ルートを取得、なかったら②へ
		Optional<PersonApprovalRoot> opPerAppRoot = perApprovalRootRepository.findByBaseDate(companyID, employeeID, standardDate, appType, rootAtr);
		if(opPerAppRoot.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opPerAppRoot.get().getBranchId());
			List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
		}
		
		// ドメインモデル「個人別就業承認ルート」を取得する(lấy thông tin domain 「個人別就業承認ルート」)
		// ②個人別の共通の承認ルートを取得、なかったら③へ
		Optional<PersonApprovalRoot> opPerAppRootsOfCommon = perApprovalRootRepository.findByBaseDate(companyID, employeeID, standardDate, appType, EmploymentRootAtr.COMMON);
		if(opPerAppRootsOfCommon.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opPerAppRootsOfCommon.get().getBranchId());
			List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
		}
		
		List<String> wpkList = employeeAdapter.findWpkIdsBySid(companyID, employeeID, standardDate);
		for (String wｋｐId : wpkList) {
			Optional<WorkplaceApprovalRoot> opWkpAppRoot = wkpApprovalRootRepository.findByBaseDate(companyID, wｋｐId, standardDate, appType, rootAtr);
			if(opWkpAppRoot.isPresent()){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opPerAppRoot.get().getBranchId());
				List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
				return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
			}
			Optional<WorkplaceApprovalRoot> opWkpAppRootsOfCom = wkpApprovalRootRepository.findByBaseDate(companyID, wｋｐId, standardDate, appType, EmploymentRootAtr.COMMON);
			if(opWkpAppRootsOfCom.isPresent()){
				List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opWkpAppRootsOfCom.get().getBranchId());
				List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
				ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
				return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
			}
		}
		
		Optional<CompanyApprovalRoot> opComAppRoot = comApprovalRootRepository.findByBaseDate(companyID, standardDate, appType, rootAtr);
		if(opComAppRoot.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opPerAppRoot.get().getBranchId());
			List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
		}
		
		Optional<CompanyApprovalRoot> opCompanyAppRootsOfCom = comApprovalRootRepository.findByBaseDate(companyID, standardDate, appType, EmploymentRootAtr.COMMON);
		if(opCompanyAppRootsOfCom.isPresent()){
			List<ApprovalPhase> listApprovalPhaseBefore = approvalPhaseRepository.getAllIncludeApprovers(companyID, opCompanyAppRootsOfCom.get().getBranchId());
			List<ApprovalPhase> listApprovalPhaseAfter = this.adjustmentData(companyID, employeeID, standardDate, listApprovalPhaseBefore);
			ErrorFlag errorFlag = this.checkApprovalRoot(listApprovalPhaseBefore, listApprovalPhaseAfter);
			return new ApprovalRootContentOutput(listApprovalPhaseAfter, errorFlag);
		}
		
		return new ApprovalRootContentOutput(Collections.emptyList(), null);
	}
	
	@Override
	public List<ApprovalPhase> adjustmentData(String companyID, String employeeID, GeneralDate baseDate,  List<ApprovalPhase> listApprovalPhase) {
		listApprovalPhase.stream().forEach(approvalPhase -> {
			if(CollectionUtil.isEmpty(approvalPhase.getApprovers())){
				return;
			}

			// ドメインモデル「承認フェーズ」．承認形態をメモリ上保持する(luu thong tin 「承認フェーズ」．承認形態 vao cache) ??
			
			List<String> listApprover = new ArrayList<>();
			approvalPhase.getApprovers().forEach(approver -> {
				// 承認者IDリストをクリアする（初期化）(clear thong tin cua list ID nguoi xac nhan)
				
				if(approver.getApprovalAtr().equals(ApprovalAtr.PERSON)){
					listApprover.add(approver.getApproverId());
					return;
				}
				List<String> listApproverJob = this.convertPositionToApprover(companyID, employeeID, baseDate, approver.getJobTitleId())
								.stream().map(x -> x.getSid()).collect(Collectors.toList());
				if(CollectionUtil.isEmpty(listApproverJob)){
					return;
				}
				listApprover.addAll(listApproverJob);
			});
			
			if(CollectionUtil.isEmpty(listApprover)){
				return;
			}
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
			listApprover.stream().forEach(x -> {
				approvalRepresenterOutput.getListApprovalAgentInfor().stream().filter(y -> y.getApprover().equals(x)).findAny().ifPresent(z -> {
					if(z.getRepresenter().equals(RepresenterInforOutput.Path_Information)){
						listApprover.remove(x);
					}
				});;
			});
			PrincipalApprovalFlg principalApprovalFlg = approvalSettingRepository.getPrincipalByCompanyId(companyID).orElse(PrincipalApprovalFlg.NOT_PRINCIPAL);
			if(principalApprovalFlg.equals(PrincipalApprovalFlg.NOT_PRINCIPAL)){
				listApprover.remove(employeeID);
			}
		});
		return Collections.emptyList();
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
		// 承認者の
		JobTitleImport jobOfApprover = syJobTitleAdapter.findJobTitleByPositionId(companyID, positionID, date);		
		if (jobOfEmp != null) {
			// 申請の
			JobTitleImport jobOfRequest = syJobTitleAdapter.findJobTitleByPositionId(companyID, jobOfEmp.getPositionId(), date);
			if (jobOfApprover == null || jobOfRequest == null) {
				return false;
			}
			if (jobOfApprover.isGreaterThan(jobOfRequest)) {
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
		JobAssignSetting assignSet = jobAssignSetRepository.findById(cid);
		if (assignSet.getIsConcurrently()) {
			// 本務兼務区分が兼務の対象者を除く
			employeeList.removeIf(x -> x.isConcurrent());
		}
		
		List<ApproverInfo> approvers = new ArrayList<>();
		for (ConcurrentEmployeeImport emp : employeeList) {
			WorkplaceImport wkpIdOfEmp = wkApproverAdapter.findBySid(emp.getEmployeeId(), baseDate);
			if (wkpId.equals(wkpIdOfEmp.getWkpId())) {
				// truyền tạm approvalAtr = 1
				approvers.add(ApproverInfo.create(emp));
			}
		}
		return approvers;
	}

	
	
	/**
	 * 2.承認ルートを整理する call this activity fo every branchId
	 */
	private List<ApprovalPhaseOutput> adjustmentApprovalRootData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalPhase> appPhases) {
		List<ApprovalPhaseOutput> phaseResults = new ArrayList<>();

		for (ApprovalPhase phase : appPhases) {
			ApprovalPhaseOutput phaseResult = ApprovalPhaseOutput.convertToOutputData(phase);

			List<Approver> approvers = phase.getApprovers();
			if (CollectionUtil.isEmpty(approvers)) {
				break;
			}

			List<ApproverInfo> approversResult = new ArrayList<>();
			approvers.stream().forEach(x -> {
				// 個人の場合
				if (x.getApprovalAtr() == ApprovalAtr.PERSON) {
					approversResult.add(ApproverInfo.create(x, employeeAdapter.getEmployeeName(x.getEmployeeId())));
				} else {
					// 職位の場合
					List<ApproverInfo> approversOfJob = jobtitleToAppService.convertToApprover(cid, sid, baseDate,
							x.getJobTitleId());
					approversResult.addAll(approversOfJob);
				}
			});

			// 承認者IDリストに承認者がいるかチェックする
			if (CollectionUtil.isEmpty(approversResult)) {
				break;
			}

			List<String> approverIds = approversResult.stream().map(x -> x.getSid()).collect(Collectors.toList());
			// 3-1.承認代行情報の取得処理
			ApprovalRepresenterOutput agency = collectApprovalAgentInforService.getApprovalAgentInfor(cid, approverIds);
			// remove approvers with agency is PASS
			List<String> agencyAppIds = agency.getListApprovalAgentInfor().stream()
					.filter(x -> x.isPass()).map(x -> x.getApprover())
					.collect(Collectors.toList());
			approverIds.removeAll(agencyAppIds);

			// get 承認設定
			approvalSettingRepository.getPrincipalByCompanyId(cid).ifPresent(a -> {
				if (a == PrincipalApprovalFlg.NOT_PRINCIPAL) {
					// 申請本人社員IDを承認者IDリストから消す
					approverIds.remove(sid);
				}
			});

			// remove duplicate data
			phaseResult.addApproverList(removeDuplicateSid(approversResult.stream().filter(x -> {
				return approverIds.contains(x);
			}).collect(Collectors.toList())));

			// add to result
			phaseResults.add(phaseResult);
		}
		return phaseResults;
	}

	/**
	 * 承認者IDリストに重複の社員IDを消す(xóa ID của nhân viên bị trùng trong List ID người xác
	 * nhận)
	 * 
	 * @param approvers
	 *            承認者IDリスト
	 * @return ApproverInfos
	 */
	private List<ApproverInfo> removeDuplicateSid(List<ApproverInfo> approvers) {
		List<ApproverInfo> result = new ArrayList<>();

		Map<String, List<ApproverInfo>> approversBySid = approvers.stream()
				.collect(Collectors.groupingBy(x -> x.getSid()));
		for (Map.Entry<String, List<ApproverInfo>> info : approversBySid.entrySet()) {
			List<ApproverInfo> values = info.getValue();
			values.sort((a, b) -> Integer.compare(a.getOrderNumber(), b.getOrderNumber()));
			Optional<ApproverInfo> value = values.stream().filter(x -> x.getIsConfirmPerson()).findFirst();
			if (value.isPresent()) {
				result.add(value.get());
			} else {
				result.add(values.get(0));
			}
		}
		return result;
	}
	
	@Override
	public ErrorFlag checkApprovalRoot(List<ApprovalPhase> listApprovalPhaseBefore, List<ApprovalPhase> listApprovalPhaseAfter) {
		for(int i = 0; i < listApprovalPhaseBefore.size(); i++){
			ApprovalPhase approvalPhaseBefore = listApprovalPhaseBefore.get(i);
			ApprovalPhase approvalPhaseAfter = listApprovalPhaseBefore.get(i);
			if(CollectionUtil.isEmpty(approvalPhaseBefore.getApprovers())){
				continue;
			}
			if(approvalPhaseAfter.getApprovers().size() > 10){
				return ErrorFlag.APPROVER_UP_10;
			}
			if(approvalPhaseAfter.getApprovers().size() < 0){
				return ErrorFlag.NO_APPROVER;
			}
			if(approvalPhaseBefore.getApprovalForm().equals(ApprovalForm.EVERYONEAPPROVED)){
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
	public List<String> organizeBrowsingPhase(String companyID, String employeeID, GeneralDate date, ApprovalPhase approvalPhase) {
		List<String> viewList = new ArrayList<>();
		if(approvalPhase.getBrowsingPhase()==0){
			return viewList;
		}
		approvalPhase.getApprovers().forEach(approver -> {
			if(approver.getApprovalAtr().equals(ApprovalAtr.PERSON)){
				viewList.add(approver.getEmployeeId());
			} else {
				List<String> approvers = this.convertPositionToApprover(companyID, employeeID, date, approver.getJobTitleId())
											.stream().map(x -> x.getSid()).collect(Collectors.toList());
				viewList.addAll(approvers);
			}
		});
		return viewList.stream().distinct().collect(Collectors.toList());
	}
}

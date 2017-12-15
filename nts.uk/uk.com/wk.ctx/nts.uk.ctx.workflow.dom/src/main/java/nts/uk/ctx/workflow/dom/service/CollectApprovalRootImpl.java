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
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
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
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Override
	public List<ApprovalRootOutput> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr,
			int appType, GeneralDate baseDate) {
		List<ApprovalRootOutput> result = new ArrayList<>();
		// get 個人別就業承認ルート from workflow
		List<PersonApprovalRoot> perAppRoots = perApprovalRootRepository.findByBaseDate(cid, sid, baseDate,
				appType);
		if (CollectionUtil.isEmpty(perAppRoots)) {
			// get 個人別就業承認ルート from workflow by other conditions
			List<PersonApprovalRoot> perAppRootsOfCommon = perApprovalRootRepository.findByBaseDateOfCommon(cid,
					sid, baseDate);
			if (CollectionUtil.isEmpty(perAppRootsOfCommon)) {
				// 所属職場を含む上位職場を取得
				List<String> wpkList = employeeAdapter.findWpkIdsBySid(cid, sid, baseDate);
				for (String wｋｐId : wpkList) {
					List<WorkplaceApprovalRoot> wkpAppRoots = wkpApprovalRootRepository.findByBaseDate(cid, wｋｐId,
							baseDate, appType);
					if (!CollectionUtil.isEmpty(wkpAppRoots)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map(x -> ApprovalRootOutput.convertFromWkpData(x))
								.collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
						break;
					}

					List<WorkplaceApprovalRoot> wkpAppRootsOfCom = wkpApprovalRootRepository
							.findByBaseDateOfCommon(cid, wｋｐId, baseDate);
					if (!CollectionUtil.isEmpty(wkpAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map(x -> ApprovalRootOutput.convertFromWkpData(x))
								.collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
						break;
					}
				}

				// ドメインモデル「会社別就業承認ルート」を取得する
				List<CompanyApprovalRoot> comAppRoots = comApprovalRootRepository.findByBaseDate(cid, baseDate,
						appType);
				if (CollectionUtil.isEmpty(comAppRoots)) {
					List<CompanyApprovalRoot> companyAppRootsOfCom = comApprovalRootRepository
							.findByBaseDateOfCommon(cid, baseDate);
					if (!CollectionUtil.isEmpty(companyAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = companyAppRootsOfCom.stream().map(x -> ApprovalRootOutput.convertFromCompanyData(x))
								.collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
					}
				} else {
					// 2.承認ルートを整理する
					result = comAppRoots.stream().map(x -> ApprovalRootOutput.convertFromCompanyData(x))
							.collect(Collectors.toList());
					this.adjustmentData(cid, sid, baseDate, result);
				}

			} else {
				// 2.承認ルートを整理する
				result = perAppRoots.stream().map(x -> ApprovalRootOutput.convertFromPersonData(x))
						.collect(Collectors.toList());
				this.adjustmentData(cid, sid, baseDate, result);
			}

		} else {
			// 2.承認ルートを整理する
			result = perAppRoots.stream().map(x -> ApprovalRootOutput.convertFromPersonData(x))
					.collect(Collectors.toList());
			this.adjustmentData(cid, sid, baseDate, result);
		}

		return result;
	}
	
	@Override
	public List<ApprovalRootOutput> adjustmentData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalRootOutput> appDatas) {
		appDatas.stream().forEach(x -> {
			List<ApprovalPhase> appPhase = approvalPhaseRepository.getAllIncludeApprovers(cid, x.getBranchId())
					.stream().filter(f -> f.getBrowsingPhase() == 0).collect(Collectors.toList());
			x.setBeforePhases(appPhase);
			List<ApprovalPhaseOutput> phases = this.adjustmentApprovalRootData(cid, sid, baseDate, appPhase);
			x.setAdjustedPhases(phases);
			// 7.承認ルートの異常チェック
			ErrorFlag errorFlag = ErrorFlag.NO_ERROR;
			if (CollectionUtil.isEmpty(appPhase)) {
				errorFlag = ErrorFlag.NO_APPROVER;
			} else {
				errorFlag = x.getAfterPhases().checkError(appPhase);
			}

			x.setErrorFlag(errorFlag);
		});
		return appDatas;
	}

	
	
	@Override
	public List<ApproverInfo> convertPositionToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		// 共通アルゴリズム「申請者の職位の序列は承認者のと比較する」を実行する
		boolean isApper = compareHierarchyTargetPerson(cid, sid, jobTitleId, baseDate);
		if (isApper) {
			WorkplaceImport wkpInfor = wkApproverAdapter.findBySid(sid, baseDate);
			// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
			List<ApproverInfo> approvers = this.getPersonByWorkplacePosition(cid, wkpInfor.getWkpId(), baseDate, jobTitleId);
			if (!CollectionUtil.isEmpty(approvers)) {
				return approvers;
			}

			// lấy domain 「職位別のサーチ設定」
			boolean needsSearch = this.jobtitleSearchSetRepository.finById(cid, jobTitleId)
						.map(s -> s.needsSearch())
							.orElse(false);
			if (needsSearch) {
				List<String> wkpIds = this.employeeAdapter.findWpkIdsBySid(cid, sid, baseDate);
				wkpIds.remove(0);

				// 上位職場が存在する(not exist wkpId 上位)
				if (CollectionUtil.isEmpty(wkpIds)) {
					Collections.emptyList();
				}

				// 上位職場の先頭から最後ループ
				for (String id : wkpIds) {
					// thực hiện xử lý 「職場に指定する職位の対象者を取得する」
					List<ApproverInfo> approversByWkp = this.getPersonByWorkplacePosition(cid, id, baseDate, jobTitleId);
					// If exist break and return
					if (!CollectionUtil.isEmpty(approversByWkp)) {
						return approversByWkp;
					}
				}
			}
		}
		return Collections.emptyList();
		
	}
	
	@Override
	public Boolean compareHierarchyTargetPerson(String companyID, String targetPersonID, String approverID,
			GeneralDate date) {
		JobTitleImport jobOfEmp = syJobTitleAdapter.findJobTitleBySid(targetPersonID, date);
		// 承認者の
		JobTitleImport jobOfApprover = syJobTitleAdapter.findJobTitleByPositionId(companyID, approverID, date);		
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
	public ErrorFlag checkApprovalRoot(List<ApprovalPhase> listApprovalPhase) {
		ErrorFlag errorFlag = ErrorFlag.NO_ERROR;
		for(ApprovalPhase approvalPhase : listApprovalPhase){
			
		}
		return errorFlag;
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

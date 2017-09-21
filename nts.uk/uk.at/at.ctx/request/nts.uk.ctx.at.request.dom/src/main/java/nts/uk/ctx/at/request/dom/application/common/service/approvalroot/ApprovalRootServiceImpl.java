package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ComApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalPhaseOutput;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ApproverInfo;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ConfirmPerson;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output.ErrorFlag;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AprovalPersonFlg;

/**
 * 1.社員の対象申請の承認ルートを取得する
 * 
 * @author vunv
 *
 */
@Stateless
public class ApprovalRootServiceImpl implements ApprovalRootService {

	@Inject
	private ApprovalRootAdapter approvalRootAdaptorDto;

	@Inject
	private EmployeeAdapter employeeAdaptor;
	
	@Inject
	private JobtitleToApproverService jobtitleToAppService;

	/**
	 * 3-1.承認代行情報の取得処理
	 */
	@Inject
	private ApprovalAgencyInformation appAgencyInfoService;
	
	/** 承認設定 */
	@Inject
	private ApplicationSettingRepository appSettingRepository;
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param baseDate 基準日
	 */
	@Override
	public List<ApprovalRootOutput> getApprovalRootOfSubjectRequest(
			String cid, String sid, int employmentRootAtr, 
			int appType,GeneralDate baseDate) {
		List<ApprovalRootOutput> result = new ArrayList<>();
		// get 個人別就業承認ルート from workflow
		List<PersonApprovalRootImport> perAppRoots = this.approvalRootAdaptorDto.findByBaseDate(cid, sid, baseDate, appType);
		if (CollectionUtil.isEmpty(perAppRoots)) {
			// get 個人別就業承認ルート from workflow by other conditions
			List<PersonApprovalRootImport> perAppRootsOfCommon = this.approvalRootAdaptorDto.findByBaseDateOfCommon(cid, sid, baseDate);
			if (CollectionUtil.isEmpty(perAppRootsOfCommon)) {
				// 所属職場を含む上位職場を取得
				List<String> wpkList = this.employeeAdaptor.findWpkIdsBySid(cid, sid, baseDate);
				for (String wｋｐId : wpkList) {
					List<WkpApprovalRootImport> wkpAppRoots = this.approvalRootAdaptorDto.findWkpByBaseDate(cid, wｋｐId, baseDate, appType);
					if (!CollectionUtil.isEmpty(wkpAppRoots)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map( x -> ApprovalRootOutput.convertFromWkpData(x)).collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
						break;
					} 
					
					List<WkpApprovalRootImport> wkpAppRootsOfCom = this.approvalRootAdaptorDto.findWkpByBaseDateOfCommon(cid, wｋｐId, baseDate);
					if (!CollectionUtil.isEmpty(wkpAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map( x -> ApprovalRootOutput.convertFromWkpData(x)).collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
						break;
					} 
				}
				
				// ドメインモデル「会社別就業承認ルート」を取得する
				List<ComApprovalRootImport> comAppRoots = this.approvalRootAdaptorDto.findCompanyByBaseDate(cid, baseDate, appType);
				if (CollectionUtil.isEmpty(comAppRoots)){
					List<ComApprovalRootImport> companyAppRootsOfCom = this.approvalRootAdaptorDto.findCompanyByBaseDateOfCommon(cid, baseDate);
					if (!CollectionUtil.isEmpty(companyAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = comAppRoots.stream().map( x -> ApprovalRootOutput.convertFromCompanyData(x)).collect(Collectors.toList());
						this.adjustmentData(cid, sid, baseDate, result);
					} 
				}else {
					// 2.承認ルートを整理する
					result = comAppRoots.stream().map( x -> ApprovalRootOutput.convertFromCompanyData(x)).collect(Collectors.toList());
					this.adjustmentData(cid, sid, baseDate, result);
				}
					
			}else {
				// 2.承認ルートを整理する
				result = perAppRoots.stream().map( x -> ApprovalRootOutput.convertFromPersonData(x)).collect(Collectors.toList());
				this.adjustmentData(cid, sid, baseDate, result);
			}
			
		}else {
			// 2.承認ルートを整理する
			result = perAppRoots.stream().map( x -> ApprovalRootOutput.convertFromPersonData(x)).collect(Collectors.toList());
			this.adjustmentData(cid, sid, baseDate, result);
		}
		
		return result;
	}
	
	/**
	 * 2.承認ルートを整理する 
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param branchIds
	 */
	@Override
	public List<ApprovalRootOutput> adjustmentData(String cid, String sid, GeneralDate baseDate,  List<ApprovalRootOutput> appDatas) {
		appDatas.stream().forEach(x -> {
			List<ApprovalPhaseImport> appPhase = this.approvalRootAdaptorDto.findApprovalPhaseByBranchId(cid, x.getBranchId())
					.stream().filter(f -> f.getBrowsingPhase() == 0)
					.collect(Collectors.toList());
			x.setBeforeApprovers(appPhase);
			List<ApprovalPhaseOutput> phases = this.adjustmentApprovalRootData(cid, sid, baseDate, appPhase);
			x.setAfterApprovers(phases);
			
			// 7.承認ルートの異常チェック
			ErrorFlag errorFlag = this.checkError(appPhase, phases);
			x.setErrorFlag(errorFlag);
		});
		return appDatas;
	}

	/**
	 * 7.承認ルートの異常チェック
	 */
	@Override
	public ErrorFlag checkError(List<ApprovalPhaseImport> beforeDatas, List<ApprovalPhaseOutput> afterDatas) {
		ErrorFlag errorFlag = ErrorFlag.NO_ERROR;
		for (ApprovalPhaseImport phase : beforeDatas) {
			if (!CollectionUtil.isEmpty(phase.getApproverDtos())) {
				int approverCounts = 0;
				List<ApproverInfo> afterApprovers = new ArrayList<>();
				if(!CollectionUtil.isEmpty(afterDatas)) {
					afterApprovers = afterDatas.stream().filter(x -> x.getApprovalPhaseId().equals(phase.getApprovalPhaseId())).findFirst().get().getApprovers();
					approverCounts = afterApprovers.size();
				}
				//int approverCounts = afterApprovers.size();
				
				// １フェーズにトータルの実際の承認者 > 10
				if (approverCounts > 10) {
					errorFlag = ErrorFlag.APPROVER_UP_10;
					break;
				}
				
				// １フェーズにトータルの実際の承認者 <= 0
				if (approverCounts <= 0) {
					errorFlag = ErrorFlag.NO_APPROVER;
					break;
				}
				
				// approvers count > 0 and < 10
				if (phase.getApprovalForm() == ApprovalForm.SINGLE_APPROVED.value) {
					List<ApproverImport> befApprovers = phase.getApproverDtos().stream().filter(x-> x.getConfirmPerson() == ConfirmPerson.CONFIRM.value).collect(Collectors.toList());
					
					if (!CollectionUtil.isEmpty(befApprovers)) {
						// 確定者あるドメインモデル「承認者」から変換した実際の承認者がいるかチェックする
						Optional<ApproverInfo> approver =  afterApprovers.stream().map(x -> {
							Optional<ApproverImport> impBef = befApprovers.stream().filter(y -> y.getApproverId().equals(x.getSid())).findFirst();
							if (!impBef.isPresent()) {
								return null;
							}
							return x;
						}).findFirst();
						
						if (!approver.isPresent()) {
							errorFlag = ErrorFlag.NO_CONFIRM_PERSON;
							break;
						}
					}
				}
				
			}
		}
		
		return errorFlag;
	}
	
	
	/**
	 * 2.承認ルートを整理する call this activity fo every branchId
	 */
	private List<ApprovalPhaseOutput> adjustmentApprovalRootData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalPhaseImport> appPhases) {
		List<ApprovalPhaseOutput> phaseResults = new ArrayList<>();
		
		for (ApprovalPhaseImport phase : appPhases) {
			ApprovalPhaseOutput phaseResult = ApprovalPhaseOutput.convertDtoToResult(phase);
			
			List<ApproverImport> approvers = phase.getApproverDtos();
			if (!CollectionUtil.isEmpty(approvers)) {
				List<ApproverInfo> approversResult = new ArrayList<>();
				approvers.stream().forEach(x -> {
					// 個人の場合
					if (x.getApprovalAtr() == 0) {
						approversResult.add(new ApproverInfo(x.getEmployeeId(), x.getApprovalPhaseId(), true, x.getOrderNumber()));
					} else if (x.getApprovalAtr() == 1) {
						// 職位の場合
						List<ApproverInfo> approversOfJob = this.jobtitleToAppService.convertToApprover(cid, sid,
								baseDate, x.getJobTitleId());
						if(approversOfJob != null) {
							approversResult.addAll(approversOfJob);
						}
					}
				});
				
				// 承認者IDリストに承認者がいるかチェックする
				if (!CollectionUtil.isEmpty(approversResult)) {
					List<String> approverIds = approversResult.stream().map(x -> x.getSid()).collect(Collectors.toList());	
					// 3-1.承認代行情報の取得処理
					ApprovalAgencyInformationOutput agency = this.appAgencyInfoService.getApprovalAgencyInformation(cid, approverIds);
					// remove approvers with agency is PASS
					List<String> agencyAppIds = agency.getListApproverAndRepresenterSID().stream()
							.filter(x -> x.getRepresenter().equals("Pass"))
							.map(x->x.getApprover()).collect(Collectors.toList());
					approverIds.removeAll(agencyAppIds);
					
					//get 承認設定 
					Optional<ApplicationSetting> appSetting = this.appSettingRepository.getApplicationSettingByComID(cid);
					if (appSetting.isPresent()) {
						if (appSetting.get().getPersonApprovalFlg() == AprovalPersonFlg.OTHER) {
							// 申請本人社員IDを承認者IDリストから消す
							approverIds.remove(sid);
						}
					}
					
					// remove duplicate data
					phaseResult.setApprovers(removeDuplicateSid(approversResult.stream().filter(x -> {
						return approverIds.contains(x);
					}).collect(Collectors.toList())));
					
					// add to result
					phaseResults.add(phaseResult);
				}
			}
		}
		return phaseResults;
	}

	/**
	 * 承認者IDリストに重複の社員IDを消す(xóa ID của nhân viên bị trùng trong List ID người xác nhận)
	 * 
	 * @param approvers 承認者IDリスト
	 * @return ApproverInfos
	 */
	private List<ApproverInfo> removeDuplicateSid(List<ApproverInfo> approvers) {
		List<ApproverInfo> result = new ArrayList<>();
		
		Map<String, List<ApproverInfo>> approversBySid = approvers.stream().collect(Collectors.groupingBy(x -> x.getSid()));
		for (Map.Entry<String, List<ApproverInfo>> info : approversBySid.entrySet()) {
			List<ApproverInfo> values = info.getValue();
			values.sort((a,b) -> Integer.compare(a.getOrderNumber(), b.getOrderNumber()));
			Optional<ApproverInfo> value = values.stream().filter(x -> x.isConfirmPerson()).findFirst();
			if (value.isPresent()) {
				result.add(value.get());
			}else {
				result.add(values.get(0));
			}
		}
		return result;
	}

}

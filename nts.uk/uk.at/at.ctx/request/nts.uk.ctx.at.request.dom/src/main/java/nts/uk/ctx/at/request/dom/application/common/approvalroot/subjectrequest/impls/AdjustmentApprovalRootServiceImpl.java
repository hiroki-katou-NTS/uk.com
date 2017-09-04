package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.AdjustmentApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.JobtitleToApproverService;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto.ApprovalPhaseAdaptorResult;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.dto.ApproverInfo;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformationService;
import nts.uk.ctx.at.request.dom.application.common.service.other.dto.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AprovalPersonFlg;

/**
 * 2.承認ルートを整理する
 * 
 * @author vunv
 *
 */
@Stateless
public class AdjustmentApprovalRootServiceImpl implements AdjustmentApprovalRootService {

	@Inject
	private JobtitleToApproverService jobtitleToAppService;

	/**
	 * 3-1.承認代行情報の取得処理
	 */
	@Inject
	private ApprovalAgencyInformationService appAgencyInfoService;
	
	/** 承認設定 */
	@Inject
	private ApplicationSettingRepository appSettingRepository;
	
	@Override
	public List<ApprovalPhaseAdaptorResult> adjustmentApprovalRootData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalPhaseAdaptorDto> appPhases) {
		List<ApprovalPhaseAdaptorResult> phaseResults = new ArrayList<>();
		
		for (ApprovalPhaseAdaptorDto phase : appPhases) {
			ApprovalPhaseAdaptorResult phaseResult = ApprovalPhaseAdaptorResult.convertDtoToResult(phase);
			
			List<ApproverAdaptorDto> approvers = phase.getApproverDtos();
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
						approversResult.addAll(approversOfJob);
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

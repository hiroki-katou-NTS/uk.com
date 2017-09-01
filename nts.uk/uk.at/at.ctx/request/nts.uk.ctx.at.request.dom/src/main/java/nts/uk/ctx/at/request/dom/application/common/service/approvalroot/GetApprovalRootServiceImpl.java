package nts.uk.ctx.at.request.dom.application.common.service.approvalroot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto.ApprovalPhaseAdaptorResult;
import nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto.ApprovalRootDataResult;

/**
 * 1.社員の対象申請の承認ルートを取得する
 * 
 * @author vunv
 *
 */
@Stateless
public class GetApprovalRootServiceImpl implements GetApprovalRootService {

	@Inject
	private ApprovalRootAdaptor approvalRootAdaptorDto;

	@Inject
	private EmployeeAdaptor employeeAdaptor;

	@Inject
	private AdjustmentApprovalRootService adjustmentAppRootService;
	
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
	public List<ApprovalRootDataResult> getApprovalRootOfSubjectRequest(
			String cid, String sid, int employmentRootAtr, 
			int appType,Date baseDate) {
		List<ApprovalRootDataResult> result = new ArrayList<>();
		// get 個人別就業承認ルート from workflow
		List<PersonAppRootAdaptorDto> perAppRoots = this.approvalRootAdaptorDto.findByBaseDate(cid, sid, baseDate, appType);
		if (CollectionUtil.isEmpty(perAppRoots)) {
			// get 個人別就業承認ルート from workflow by other conditions
			List<PersonAppRootAdaptorDto> perAppRootsOfCommon = this.approvalRootAdaptorDto.findByBaseDateOfCommon(cid, sid, baseDate);
			if (CollectionUtil.isEmpty(perAppRootsOfCommon)) {
				// 所属職場を含む上位職場を取得
				List<String> wpkList = this.employeeAdaptor.findWpkIdsBySid(cid, sid, GeneralDate.legacyDate(baseDate));
				for (String wｋｐId : wpkList) {
					List<WkpAppRootAdaptorDto> wkpAppRoots = this.approvalRootAdaptorDto.findWkpByBaseDate(cid, wｋｐId, baseDate, appType);
					if (!CollectionUtil.isEmpty(wkpAppRoots)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromWkpData(x)).collect(Collectors.toList());
						this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
						break;
					} 
					
					List<WkpAppRootAdaptorDto> wkpAppRootsOfCom = this.approvalRootAdaptorDto.findWkpByBaseDateOfCommon(cid, wｋｐId, baseDate);
					if (!CollectionUtil.isEmpty(wkpAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = wkpAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromWkpData(x)).collect(Collectors.toList());
						this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
						break;
					} 
				}
				
				// ドメインモデル「会社別就業承認ルート」を取得する
				List<CompanyAppRootAdaptorDto> comAppRoots = this.approvalRootAdaptorDto.findCompanyByBaseDate(cid, baseDate, appType);
				if (CollectionUtil.isEmpty(comAppRoots)){
					List<CompanyAppRootAdaptorDto> companyAppRootsOfCom = this.approvalRootAdaptorDto.findCompanyByBaseDateOfCommon(cid, baseDate);
					if (!CollectionUtil.isEmpty(companyAppRootsOfCom)) {
						// 2.承認ルートを整理する
						result = comAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromCompanyData(x)).collect(Collectors.toList());
						this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
					} 
				}else {
					// 2.承認ルートを整理する
					result = comAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromCompanyData(x)).collect(Collectors.toList());
					this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
				}
					
			}else {
				// 2.承認ルートを整理する
				result = perAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromPersonData(x)).collect(Collectors.toList());
				this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
			}
			
		}else {
			// 2.承認ルートを整理する
			result = perAppRoots.stream().map( x -> ApprovalRootDataResult.convertFromPersonData(x)).collect(Collectors.toList());
			this.adjustmentApprovalRoot(cid, sid, GeneralDate.legacyDate(baseDate), result);
		}
		
		return result;
	}
	
	/**
	 * 2.承認ルートを整理する call this activity fo every branchId
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param branchIds
	 */
	private void adjustmentApprovalRoot(String cid, String sid, GeneralDate baseDate,  List<ApprovalRootDataResult> appDatas) {
		appDatas.stream().forEach(x -> {
			List<ApprovalPhaseAdaptorDto> appPhase = this.approvalRootAdaptorDto.findApprovalPhaseByBranchId(cid, x.getBranchId())
					.stream().filter(f -> f.getBrowsingPhase() == 0)
					.collect(Collectors.toList());
			List<ApprovalPhaseAdaptorResult> phases = this.adjustmentAppRootService.adjustmentApprovalRootData(cid, sid, baseDate, appPhase);
			x.setApprovers(phases);	
		});
	}

}

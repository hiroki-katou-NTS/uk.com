package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

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
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.GetApprovalRootService;

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

	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 */
	@Override
	public void getApprovalRootOfSubjectRequest(
			String cid, String sid, int employmentRootAtr, 
			int appType,Date standardDate) {
		List<String> branchIds = new ArrayList<>();
		// get 個人別就業承認ルート from workflow
		List<PersonAppRootAdaptorDto> perAppRoots = this.approvalRootAdaptorDto.findByBaseDate(cid, sid, standardDate, appType);
		if (CollectionUtil.isEmpty(perAppRoots)) {
			// get 個人別就業承認ルート from workflow by other conditions
			List<PersonAppRootAdaptorDto> perAppRootsOfCommon = this.approvalRootAdaptorDto.findByBaseDateOfCommon(cid, sid, standardDate);
			if (CollectionUtil.isEmpty(perAppRootsOfCommon)) {
				// 所属職場を含む上位職場を取得
				List<String> wpkList = this.employeeAdaptor.findWpkIdsBySCode(cid, sid, GeneralDate.legacyDate(standardDate));
				for (String wｋｐId : wpkList) {
					List<WkpAppRootAdaptorDto> wkpAppRoots = this.approvalRootAdaptorDto.findWkpByBaseDate(cid, wｋｐId, standardDate, appType);
					if (!CollectionUtil.isEmpty(wkpAppRoots)) {
						// 2.承認ルートを整理する
						break;
					} 
					
					List<WkpAppRootAdaptorDto> wkpAppRootsOfCom = this.approvalRootAdaptorDto.findWkpByBaseDateOfCommon(cid, wｋｐId, standardDate);
					if (!CollectionUtil.isEmpty(wkpAppRootsOfCom)) {
						// 2.承認ルートを整理する
						break;
					} 
				}
				
				// ドメインモデル「会社別就業承認ルート」を取得する
				List<CompanyAppRootAdaptorDto> comAppRoots = this.approvalRootAdaptorDto.findCompanyByBaseDate(cid, standardDate, appType);
				if (CollectionUtil.isEmpty(comAppRoots)){
					List<CompanyAppRootAdaptorDto> companyAppRootsOfCom = this.approvalRootAdaptorDto.findCompanyByBaseDateOfCommon(cid, standardDate);
					if (!CollectionUtil.isEmpty(companyAppRootsOfCom)) {
						// 2.承認ルートを整理する
					} 
				}else {
					// 2.承認ルートを整理する
				}
					
			}else {
				// 2.承認ルートを整理する
			}
			
		}else {
			// 2.承認ルートを整理する
			branchIds = perAppRoots.stream().map(x -> x.getBranchId()).collect(Collectors.toList());
			
		}
	}

}

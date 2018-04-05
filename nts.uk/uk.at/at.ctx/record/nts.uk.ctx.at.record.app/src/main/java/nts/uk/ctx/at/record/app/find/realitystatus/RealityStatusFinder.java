package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.realitystatus.service.RealityStatusService;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.SumCountOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.UseSetingOutput;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@Stateless
public class RealityStatusFinder {
	@Inject
	ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	RealityStatusService realityStatusService;

	public List<RealityStatusActivityDto> getStatusActivity(RealityStatusActivityData wkpInfoDto) {
		List<RealityStatusActivityDto> listStatusActivity = new ArrayList<RealityStatusActivityDto>();
		// アルゴリズム「承認状況取得実績使用設定」を実行する
		UseSetingOutput useSeting = realityStatusService.getUseSetting();
		// 職場ID(リスト)
		List<String> listWorkplaceId = wkpInfoDto.getListWorkplaceId();
		for (String wkpId : listWorkplaceId) {
			GeneralDate startDate = GeneralDate.fromString(wkpInfoDto.getStartDate(), "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(wkpInfoDto.getEndDate(), "yyyy/MM/dd");
			// アルゴリズム「承認状況取得社員」を実行する
			List<ApprovalStatusEmployeeImport> listStatusEmp = approvalStatusAdapter.getApprovalStatusEmployee(wkpId,
					startDate, endDate, wkpInfoDto.getListEmpCd());
			// アルゴリズム「承認状況取得職場実績確認」を実行する
			SumCountOutput count = realityStatusService.getApprovalSttConfirmWkpResults(listStatusEmp, wkpId,
					useSeting);
			// 保持内容．未確認データ確認
			if (wkpInfoDto.isConfirmData()) {
				// 職場本人未確認件数及び職場上司未確認件数、月別未確認件数がすべて「０件」の場合
				if (count.personUnconfirm == 0 && count.bossUnconfirm == 0 && count.monthUnconfirm == 0) {
					continue;
				}
			}
			listStatusActivity.add(new RealityStatusActivityDto(wkpId, count.monthConfirm, count.monthUnconfirm,
					count.personConfirm, count.personUnconfirm, count.bossConfirm, count.bossUnconfirm));
		}
		return listStatusActivity;
	}
}

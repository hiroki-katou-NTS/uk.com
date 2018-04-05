package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusRequestAdapter;
import nts.uk.ctx.at.record.dom.adapter.request.service.RealityStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.realitystatus.service.RealityStatusService;
import nts.uk.ctx.at.record.dom.realitystatus.service.TransmissionAttr;
import nts.uk.ctx.at.record.dom.realitystatus.service.WkpIdMailCheck;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.EmpUnconfirmResultOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.SumCountData;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.UseSetingData;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@Stateless
public class RealityStatusFinder {
	@Inject
	ApprovalStatusRequestAdapter approvalStatusAdapter;

	@Inject
	RealityStatusService realityStatusService;

	/**
	 * アルゴリズム「承認状況職場実績起動」を実行する
	 * 
	 * @param wkpInfoDto
	 * @return
	 */
	public List<RealityStatusActivityDto> getStatusActivity(RealityStatusActivityParam wkpInfoDto) {
		List<RealityStatusActivityDto> listStatusActivity = new ArrayList<RealityStatusActivityDto>();
		// アルゴリズム「承認状況取得実績使用設定」を実行する
		UseSetingData useSeting = realityStatusService.getUseSetting();
		// 職場ID(リスト)
		List<String> listWorkplaceId = wkpInfoDto.getListWorkplaceId();
		for (String wkpId : listWorkplaceId) {
			GeneralDate startDate = GeneralDate.fromString(wkpInfoDto.getStartDate(), "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(wkpInfoDto.getEndDate(), "yyyy/MM/dd");
			// アルゴリズム「承認状況取得社員」を実行する
			List<RealityStatusEmployeeImport> listStatusEmp = approvalStatusAdapter.getApprovalStatusEmployee(wkpId,
					startDate, endDate, wkpInfoDto.getListEmpCd());
			// アルゴリズム「承認状況取得職場実績確認」を実行する
			SumCountData count = realityStatusService.getApprovalSttConfirmWkpResults(listStatusEmp, wkpId, useSeting);
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

	/**
	 * アルゴリズム「承認状況未確認メール送信」を実行する
	 * 
	 * @param listWkp
	 */
	public void checkSendUnconfirmedMail(List<WkpIdMailCheckParam> listWkp) {
		// アルゴリズム「承認状況送信者メール確認」を実行する
		// Waiting

		// アルゴリズム「承認状況未確認メール送信実行チェック」を実行する
		if (listWkp.stream().filter(x -> x.isCheckOn()).count() == 0) {
			throw new BusinessException("Msg_794");
		}
	}

	/**
	 * アルゴリズム「承認状況未確認メール送信実行」を実行する
	 * 
	 * @param type
	 *            送信区分
	 */
	public void exeSendUnconfirmMail(ExeSendUnconfirmMailParam dto) {
		List<WkpIdMailCheck> listWkp = new ArrayList<>();
		for (WkpIdMailCheckParam item : dto.getListWkp()) {
			WkpIdMailCheck wkp = new WkpIdMailCheck(item.getWkpId(), item.isCheckOn());
			listWkp.add(wkp);
		}
		GeneralDate startDate = GeneralDate.fromString(dto.getStartDate(), "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(dto.getEndDate(), "yyyy/MM/dd");
		// アルゴリズム「承認状況未確認メール送信社員取得」を実行する
		EmpUnconfirmResultOutput result = realityStatusService.getListEmpUnconfirm(
				EnumAdaptor.valueOf(dto.getType(), TransmissionAttr.class), listWkp, startDate, endDate,
				dto.getListEmpCd());
		if (!result.isOK()) {
			// メッセージ（Msg_787）を表示する
			throw new BusinessException("Msg_787");
		}
		// アルゴリズム「承認状況未確認メール本文取得」を実行する
		
	}
}

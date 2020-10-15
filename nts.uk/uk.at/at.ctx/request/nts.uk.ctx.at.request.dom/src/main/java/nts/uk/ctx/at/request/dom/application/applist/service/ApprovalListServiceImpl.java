package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalListServiceImpl implements ApprovalListService {

	@Override
	public void checkBeforeSearch(AppListExtractCondition appListExtractCondition) {
		// 期間開始日付/期間終了日付　入力チェック
		// check trên UI
		// 日付入力大小チェック
		// check trên UI
		// 申請一覧抽出条件.申請一覧区分
		if(appListExtractCondition.getAppListAtr()==ApplicationListAtr.APPROVER) {
			// 承認状況のチェックの確認
			boolean anySelectCondition = appListExtractCondition.getOpRemandStatus().orElse(false) ||
					appListExtractCondition.getOpCancelStatus().orElse(false) ||
					appListExtractCondition.getOpApprovalStatus().orElse(false) ||
					appListExtractCondition.getOpAgentApprovalStatus().orElse(false) ||
					appListExtractCondition.getOpDenialStatus().orElse(false) ||
					appListExtractCondition.getOpUnapprovalStatus().orElse(false);
			if(!anySelectCondition) {
				// メッセージ（Msg_360）を表示する
				throw new BusinessException("Msg_360");
			}
		}
		// アルゴリズム「申請一覧検索条件確認」を実行する
		this.confirmSearchCondition(appListExtractCondition);
		
	}

	@Override
	public void confirmSearchCondition(AppListExtractCondition appListExtractCondition) {
		// 表示されている場合、事前、事後共にOFF
		if(!appListExtractCondition.isPreOutput() && !appListExtractCondition.isPostOutput()) {
			// メッセージ「#Msg_1722」を表示する（事前又は事後を指定してください）
			throw new BusinessException("Msg_1722");
		}
		// 検索申請種類が１件も選択されていない
		List<ListOfAppTypes> listOfAppTypes = appListExtractCondition.getOpListOfAppTypes().orElse(Collections.emptyList());
		if(CollectionUtil.isEmpty(listOfAppTypes)) {
			// メッセージ「#Msg_1723」を表示する（申請種類を指定してください）
			throw new BusinessException("Msg_1723");
		}
		
	}

	@Override
	public boolean checkErrorComfirm(ApprovalListDisplaySetting approvalListDisplaySetting,
			WorkMotionData workMotionData, ApplicationType appType) {
		// 申請種類
		if(appType!=ApplicationType.OVER_TIME_APPLICATION && appType!=ApplicationType.HOLIDAY_WORK_APPLICATION) {
			return true;
		}
		return false;
	}
	
}

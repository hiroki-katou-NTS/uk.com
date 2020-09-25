package nts.uk.ctx.at.request.dom.application.applist.service;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;

public interface ApprovalListService {
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧検索前チェック.申請一覧検索前チェック
	 * @param appListExtractCondition ドメイン：申請一覧抽出条件
	 */
	public void checkBeforeSearch(AppListExtractCondition appListExtractCondition);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧検索条件確認.申請一覧検索条件確認
	 * @param appListExtractCondition
	 */
	public void confirmSearchCondition(AppListExtractCondition appListExtractCondition);
	
	public boolean checkErrorComfirm(ApprovalListDisplaySetting approvalListDisplaySetting, WorkMotionData workMotionData, ApplicationType appType);
	
}

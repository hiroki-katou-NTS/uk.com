package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4
 * @author Doan Duy Hung
 *
 */
public interface AppDataCreation {
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リストのデータを作成.申請一覧リストのデータを作成
	 * @param companyID 会社ID
	 * @param appLst 申請一覧リスト
	 * @param period 対象期間
	 * @param mode モード：　申請一覧か承認一覧
	 * @param mapApproval Map＜ルートインスタンスID、承認フェーズList＞
	 * @param device デバイス：PC or スマートフォン
	 * @param appListExtractCondition 申請一覧抽出条件
	 * @param appListInfo 申請一覧情報
	 * @return
	 */
	public AppListInfo createAppLstData(String companyID, List<Application> appLst, DatePeriod period, ApplicationListAtr mode, 
			Map<String,List<ApprovalPhaseStateImport_New>> mapApproval, int device, AppListExtractCondition appListExtractCondition, 
			AppListInfo appListInfo);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の並び順を変更する.申請一覧の並び順を変更する
	 * @param appListInfo 申請一覧情報
	 * @param appListExtractCondition 申請一覧抽出条件
	 * @param device デバイス
	 * @return
	 */
	public AppListInfo changeOrderOfAppLst(AppListInfo appListInfo, AppListExtractCondition appListExtractCondition, int device);
	
}

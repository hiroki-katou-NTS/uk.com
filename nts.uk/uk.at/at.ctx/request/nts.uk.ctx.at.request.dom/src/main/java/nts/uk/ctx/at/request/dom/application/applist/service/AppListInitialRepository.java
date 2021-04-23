package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInitOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpInfo;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author hoatt
 *
 */
public interface AppListInitialRepository {
	/**
	 * 0 - 申請一覧事前必須チェック
	 * @param　申請種類 appType
	 * @param 職場ID　wkpID
	 * @param 会社ID　companyId
	 * @return
	 */
	public Boolean checkAppPredictRequire(int appType, String wkpID, String companyId);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リスト取得.申請一覧リスト取得
	 * @param param ドメイン：申請一覧抽出条件
	 * @param device デバイス：PC or スマートフォン
	 * @param appListInfo ドメイン：申請一覧情報
	 * @return
	 */
	public AppListInitOutput getApplicationList(AppListExtractCondition param, int device, AppListInfo appListInfo);

	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リスト取得申請.申請一覧リスト取得申請
	 * @param param ドメイン：申請一覧抽出条件
	 * @param device デバイス：PC or スマートフォン
	 * @param appListInfo ドメイン：申請一覧情報
	 * @return
	 */
	public AppListInfo getApplicationListByApp(AppListExtractCondition param, int device, AppListInfo appListInfo);
	/**
	 * 2.1 - 申請一覧対象申請者取得
	 * @param 抽出条件　param
	 * @return
	 */
	public ListApplicantOutput getListApplicantForListApp(AppListExtractCondition param);

	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リスト取得承認(二次開発).申請一覧リスト取得承認(二次開発)
	 * @param param 申請一覧抽出条件
	 * @param device デバイス：PC or スマートフォン
	 * @param appListInfo 申請一覧情報
	 * @return
	 */
	public AppListInfo getAppListByApproval(AppListExtractCondition param, int device, AppListInfo appListInfo);

	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リスト取得承認件数.申請一覧リスト取得承認件数
	 * @param listApp 申請一覧(LIST）
	 * @param appStatus 申請件数
	 * @return
	 */
	public ApplicationStatus countAppListApproval(List<ListOfApplication> listApp, ApplicationStatus appStatus);

	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リスト取得マスタ情報.申請一覧リスト取得マスタ情報
	 * @param application 申請
	 * @param period 対象期間
	 * @param displayWorkPlaceName 所属職場名表示
	 * @param mapEmpInfo Map<社員ID, 個人社員基本情報>
	 * @param mapWkpInfo Map<<社員ID, 期間> 職場情報> 
	 * @param device デバイス：PC or スマートフォン
	 * @return
	 */
	public AppInfoMasterOutput getListAppMasterInfo(Application application, DatePeriod period, NotUseAtr displayWorkPlaceName, 
			Map<String, SyEmployeeImport> mapEmpInfo, Map<Pair<String, DatePeriod>, WkpInfo> mapWkpInfo, int device);
	/**
	 * 12 - 申請一覧初期日付期間
	 * @param 会社ID　companyId
	 * @return
	 */
	public DatePeriod getInitialPeriod(String companyId);
	/**
	 * 12.1 - 申請一覧初期日付期間_申請
	 * @param 会社ID　companyId
	 * @return
	 */
	public DatePeriod getInitPeriodApp(String companyId);
	/**
	 * 職場IDから申請承認設定情報取得
	 * @param companyId
	 * @param wkpId
	 * @param appType
	 * @param date
	 * @return
	 */
	public List<ApplicationUseSetting> detailSetKAF022(String companyId, String wkpId, GeneralDate date);
}

package nts.uk.ctx.at.auth.infra.repository.wkpmanager;

import java.util.ArrayList;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.at.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.shr.com.context.AppContexts;

public class JpaWorkplaceManagerRepository extends JpaRepository implements WorkplaceManagerRepository{
	/**
	 * Query strings
	 */
	
	@Override
	public List<String> getAllWorkplaceList() {
		/*
		 * 取得条件
		 * ・社員ID＝ログイン者
		 * ・基準日＝システム日付
		 * output
		 * ・職場ID
		 * ・職場表示名
		 */
		return getEmployeeWorkplaceHistory(AppContexts.user().employeeId(), GeneralDate.today());
	}

	@Override
	public List<String> getAllWorkplaceManagerList(String companyId, String workplaceId, GeneralDate refDate) {
		/*
		 * ドメインモデル「職場管理機能」を取得する
		 * KACMT_WORPLACE_FUNCTION
		 * 条件：
		 * なし
		 */
		List<WorkPlaceFunction> workplaceFuncList = new ArrayList<>();
		/*
		 * ドメインモデル「職場管理者」を取得する
		 * input：
		 * ・会社ID
		 * ・職場ID
		 * ・基準日（開始日、終了日）
		 * output：
		 * ・職場ID
		 * ・社員ID
		 * ・履歴期間
		 * ・ロールID
		 */
		List<WorkplaceManager> wkpManagerList = new ArrayList<>(); // Query String
		if (CollectionUtil.isEmpty(wkpManagerList)) {
			// 画面を新規モードにする - Mode NEW
		} else {
			// 一番上の職場管理者を選択する - select top workplace
			// 職場管理者リストの先頭行を対象とする - target first line --> Client code
		}
		return null;
	}

	/**
	 * アルゴリズム「社員と基準日から所属職場履歴項目を取得する」を実行する
	 * 
	 * 【input】
	 * 社員ID
	 * 基準日
	 */
	@Override
	public List<String> getEmployeeWorkplaceHistory(String employeeId, GeneralDate baseDate) {
		/*
		 * ドメインモデル「所属職場履歴」を取得する
		 * KMNMT_AFFI_WORKPLACE_HIST - KmnmtAffiliWorkplaceHist.java
		 * 【条件】
		 * 社員ID　＝　パラメータ．社員ID
		 */
		List<String> affWorkplaceHstList = new ArrayList<String>();
		/*
		 * 基準日を含む履歴項目の履歴IDを取得する
		 * 開始日＜＝基準日＜＝終了日
		 */
		
		/*
		 * ドメインモデル「所属職場履歴項目」を取得する
		 * 【条件】
		 * 履歴ID　＝　取得した履歴ID
		 */
		
		/*
		 * 【output】
		 * 所属職場履歴項目
		 * ※取得できない場合はnull
		 */
		List<String> affWorkplaceHstItemList = new ArrayList<String>();
		if (CollectionUtil.isEmpty(affWorkplaceHstItemList)) {
			// 終了状態：社員所属職場履歴取得失敗
			return null;
		} else {
			/*
			 * アルゴリズム「職場IDから職場情報を取得」を実行する
			 * 【パラメータ】
　			 * ①会社ID：ログイン会社ID
　			 * ②履歴.職場ID：上記処理で取得した「所属職場.職場ID」
　			 * ③基準日： 上記処理で取得した「所属職場.期間.開始日」
			 */
			for (String item : affWorkplaceHstItemList) {
				getWorkplaceByWorkplaceId("", "", null);
				if (CollectionUtil.isEmpty(affWorkplaceHstItemList)) {
					// 終了状態：社員所属職場履歴取得失敗
					return null;
				} else {
					// 社員所属職場履歴を取得
				}
			}
		}
		// 終了状態：成功
		return null;
	}

	/**
	 * 【input】
	 * ・会社ID
	 * ・職場ID
	 * ・基準日
	 * 【output】
	 * ・cls <職場表示名>
	 */
	@Override
	public List<String> getWorkplaceByWorkplaceId(String companyId, String workplaceHstId, GeneralDate refDate) {
		/*
		 * ドメインモデル「職場」を取得する
		 * 【条件】
　		 * ・会社ID
　		 * ・職場ID
　		 * ・基準日に一致する
		 */
		List<String> workplaceList = new ArrayList<String>();
		/*
		 * ドメインモデル「職場情報」を取得する
		 * 【条件】
　		 * ・会社ID
　		 * ・職場ID
　		 * ・履歴ID：取得した履歴ID
		 */
		List<String> workplaceNameList = new ArrayList<String>();
		for (String workplace : workplaceList) {
			// Execute query string
			List<String> workplaceInfoList = new ArrayList<String>();
			for (String wkpInfo : workplaceInfoList) {
				workplaceNameList.add(wkpInfo);
			}
		}
		/*
		 * 「職場情報」を返す
		 */
		return workplaceNameList;
	}

}

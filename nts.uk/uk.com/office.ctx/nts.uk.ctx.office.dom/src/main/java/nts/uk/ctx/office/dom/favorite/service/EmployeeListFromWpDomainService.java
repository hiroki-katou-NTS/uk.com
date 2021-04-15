package nts.uk.ctx.office.dom.favorite.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.職場IDから社員IDリストを取得
 * 
 * 
 * 職場IDから社員IDリストを取得
 */
public class EmployeeListFromWpDomainService {
	
	private EmployeeListFromWpDomainService() {}
	
	/**
	 * [1]社員IDリストを取得
	 * @param Require @Require
	 * @param sid ログイン社員ID
	 * @param wkpIds 職場IDリスト
	 * @param ymd 基準日
	 * @return 社員IDリスト
	 */
	public static List<String> getEmployeeIdList(Require require, String sid, List<String> wkpIds, GeneralDate baseDate) {
		// if　職場IDリスト.isEmpty()
		if (wkpIds.isEmpty()) {
			// 職場IDリスト　＝　require.社員の職場IDを取得する(ログイン社員ID、基準日)
			String wkpId = require.getWorkplaceId(Arrays.asList(sid), baseDate)
					.get(sid);
			wkpIds = Arrays.asList(wkpId);
		}
		// return require.職場の所属社員を取得する(職場IDリスト、基準日)
		return require.acquireToTheWorkplace(wkpIds, baseDate);
	}

	public static interface Require {
		
		/**
		 * [R-1] 社員の職場IDを取得する
		 * @param sIds 社員IDリスト
		 * @param baseDate 基準日
		 * @return
		 */
		Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate);
		
		/**
		 * [R-2]職場の所属社員を取得する
		 * @param wkps 職場IDリスト
		 * @param baseDate 基準日
		 * @return
		 */
		List<String> acquireToTheWorkplace(List<String> wkps, GeneralDate baseDate);
	}
}

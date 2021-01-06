package nts.uk.ctx.sys.assist.dom.favorite.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.EmployeeBelongWorkplaceAdapter;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.職場IDから社員IDリストを取得
 * 
 * 
 * 職場IDから社員IDリストを取得
 */
public class EmployeeListFromWpDomainService {
	
	public EmployeeListFromWpDomainService() {}
	
	/**
	 * [1]社員IDリストを取得
	 * @param Require @Require
	 * @param wkpIds 職場IDリスト
	 * @param ymd 基準日
	 * @return 社員IDリスト
	 */
	public List<String> getEmployeeIdList(Require require, List<String> wkpIds, GeneralDate baseDate) {
		// if　職場IDリスト.isEmpty()
		if (wkpIds.isEmpty()) {
			// 職場IDリスト　＝　require.社員の職場IDを取得する(ログイン社員ID、基準日)
			String wkpId = require.getWorkplaceId(Arrays.asList(AppContexts.user().employeeId()), baseDate)
					.get(AppContexts.user().employeeId());
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
	
	@RequiredArgsConstructor
	public static class DefaultRequireImpl implements Require {
		@Inject
		private EmployeeWorkplaceIdAdapter employeeWorkplaceIdAdapter;
		
		@Inject
		private EmployeeBelongWorkplaceAdapter employeeBelongWorkplaceAdapter;

		@Override
		public Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate) {
			return this.employeeWorkplaceIdAdapter.getWorkplaceId(sIds, baseDate);
		}

		@Override
		public List<String> acquireToTheWorkplace(List<String> wkps, GeneralDate baseDate) {
			return this.employeeBelongWorkplaceAdapter.getEmployeeByWplAndBaseDate(wkps, baseDate);
		}
	}
}

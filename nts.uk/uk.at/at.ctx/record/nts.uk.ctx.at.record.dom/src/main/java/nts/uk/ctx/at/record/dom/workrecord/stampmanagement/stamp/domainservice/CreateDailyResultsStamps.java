package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;

/**
 * 	打刻入力から日別実績を作成する
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻入力から日別実績を作成する
 * @author chungnt
 *
 */

public class CreateDailyResultsStamps {

	public void create(Require require, String companyID, String employeeId, Optional<GeneralDate> date) {
		
		if (!date.isPresent()) {
			return;
		}
		// 	$エラー一覧 = require.日別実績の作成(会社ID, 社員ID, 年月日, しない, 打刻反映する, empty, empty)
		
		List<String> lstEmpId = require.getListEmpID(companyID, date.get().today());
		
		if (!lstEmpId.isEmpty()) {
			
//			TopPageAlarmStamping topPageAlarmStamping = TopPageAlarmStamping.this.get(lstEmpId, employeeId, lsterror);
//			require.insert(topPageAlarmStamping);
		}
		return;
	}
	
	public static interface Require  {

		/**
		 * 	[R-1] 日別実績の作成	
		 */
		//	アルゴリズム.日別実績の作成(会社ID, 社員ID, 期間, 再作成区分, 実行タイプ, 就業計算と集計実行ログ, ロック中計算/集計できるか)
		//Khong tim thay
		
		/**
		 * 	[R-2] 就業担当者を取得する
		 */
		List<String> getListEmpID(String companyID , GeneralDate referenceDate);
		
		/**
		 * 	[R-3] トップページアラームを追加する	
		 */
		void insert(TopPageAlarmStamping domain);
	}
}

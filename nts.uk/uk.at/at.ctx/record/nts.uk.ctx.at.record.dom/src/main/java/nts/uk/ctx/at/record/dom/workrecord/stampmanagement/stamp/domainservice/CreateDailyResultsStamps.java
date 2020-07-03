package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 	打刻入力から日別実績を作成する
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻入力から日別実績を作成する
 * @author chungnt
 *
 */

public class CreateDailyResultsStamps {

	/**
	 * 
	 * @param require  		
	 * @param companyID		会社ID	
	 * @param employeeId	社員ID
	 * @param date			年月日
	 */
	public static void create(Require require, String companyID, String employeeId, Optional<GeneralDate> date) {
		
		if (!date.isPresent()) {
			return;
		}
		
		// 	$エラー一覧 = require.日別実績の作成(会社ID, 社員ID, 年月日, しない, 打刻反映する, empty, empty)
		// TODO: Chungnt
		List<ErrorMessageInfo> errorMessageInfos = require.getListError(companyID, employeeId, null, 0, 0, null, 0);
		
		List<String> lstEmpId = require.getListEmpID(companyID, date.map(m -> m.today()).orElse(GeneralDate.today()));
		
		if (!lstEmpId.isEmpty()) {
			
			List<String> lsterror = errorMessageInfos.stream().map(m -> m.getMessageError().v()).collect(Collectors.toList());
			
			TopPageAlarmStamping topPageAlarmStamping = new TopPageAlarmStamping(companyID, lstEmpId, employeeId, lsterror);
			
			//require.トップページアラームを追加する($トップページアラーム)	
			require.insert(topPageAlarmStamping);
		}
		return;
	}
	
	public static interface Require  {

		/**
		 * 	[R-1] 日別実績の作成	
		 */
		//	アルゴリズム.日別実績の作成(会社ID, 社員ID, 期間, 再作成区分, 実行タイプ, 就業計算と集計実行ログ, ロック中計算/集計できるか)
		List<ErrorMessageInfo> getListError(String companyID, String employeeId, DatePeriod period, int reCreateAtr, int i, EmpCalAndSumExeLog empCalAndSumExeLog, int i1);
		
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

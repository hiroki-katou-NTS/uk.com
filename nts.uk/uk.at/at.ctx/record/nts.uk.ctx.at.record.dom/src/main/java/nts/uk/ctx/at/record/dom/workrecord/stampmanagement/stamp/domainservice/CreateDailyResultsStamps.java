package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

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
	public static List<ErrorMessageInfo> create(Require require, String companyID, String employeeId,
			Optional<GeneralDate> date) {

		if (!date.isPresent()) {
			return new ArrayList<>();
		}

		DatePeriod datePeriod = new DatePeriod(date.get(), date.get());
		
		// $エラー一覧 = require.日別実績の作成(会社ID, 社員ID, 年月日, しない, 打刻反映する, empty, empty)
		OutputCreateDailyResult createDataNewNotAsync = require.createDataNewNotAsync(
				employeeId, 
				datePeriod,
				ExecutionAttr.MANUAL, 
				companyID, 
				ExecutionTypeDaily.IMPRINT, 
				Optional.empty(), 
				Optional.empty());
		
		return createDataNewNotAsync.getListErrorMessageInfo();

	}
	
	public static interface Require  {

		/**
		 * 	[R-1] 日別実績の作成	
		 */
		//	アルゴリズム.日別実績の作成(会社ID, 社員ID, 期間, 再作成区分, 実行タイプ, 就業計算と集計実行ログ, ロック中計算/集計できるか)
		OutputCreateDailyResult createDataNewNotAsync(String employeeId,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				ExecutionTypeDaily executionType,Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock);
	}
}

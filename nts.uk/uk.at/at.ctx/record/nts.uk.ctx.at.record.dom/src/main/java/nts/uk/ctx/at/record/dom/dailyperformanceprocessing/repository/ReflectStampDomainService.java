package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;

/**
 * 
 * @author nampt 日別実績処理 作成処理 ⑥１日の日別実績の作成処理 2.打刻を取得して反映する
 * 
 *
 */
public interface ReflectStampDomainService {

	// 2.打刻を取得して反映する  (new)
	/**
	 * 
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param processingDate 年月日
	 * @param executionType 再作成フラグ
	 * @param flag 打刻実行フラグ（未反映のみ, 全部）
	 * @param empCalAndSumExecLogID
	 * @param workInfoOfDailyPerformance 日別実績の勤務情報
	 * @param affInfoOfDaily 日別実績の所属情報
	 * @param workTypeOfDaily 日別実績の勤務種別
	 * @param calcOfDaily 日別実績の計算区分
	 * @return
	 */
	public OutputAcquireReflectEmbossingNew acquireReflectEmbossingNew(String companyID, String employeeID,
			GeneralDate processingDate,ExecutionTypeDaily executionType,EmbossingExecutionFlag flag,
			IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt,CacheCarrier carrier);
}

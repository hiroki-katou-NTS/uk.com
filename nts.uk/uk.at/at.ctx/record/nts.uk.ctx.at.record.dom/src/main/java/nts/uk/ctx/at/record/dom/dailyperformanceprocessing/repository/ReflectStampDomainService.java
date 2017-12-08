package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 作成処理
 * ⑥１日の日別実績の作成処理
 * 2.打刻を取得して反映する
 * 
 *
 */
public interface ReflectStampDomainService {
	
	void reflectStampInfo(String companyID, String employeeID, GeneralDate processingDate, WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
}

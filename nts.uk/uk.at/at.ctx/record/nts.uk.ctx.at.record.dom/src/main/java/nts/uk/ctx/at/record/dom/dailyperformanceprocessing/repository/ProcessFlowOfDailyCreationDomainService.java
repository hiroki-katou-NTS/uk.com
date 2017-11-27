package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 計算集計Mgrクラス
 * アルゴリズム : 計算集計Mgrクラス
 *
 */
public interface ProcessFlowOfDailyCreationDomainService {
	
	//日別作成の処理の流れ
	boolean processFlowOfDailyCreation(int executionAttr, DatePeriod periodTime, String empCalAndSumExecLogID, int reCreateAttr);

}

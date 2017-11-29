package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 作成処理
 * アルゴリズム : ③日別実績の作成処理
 *
 */
public interface CreateDailyResultDomainService {

	int createDailyResult(List<String> emloyeeIds, int reCreateAttr, DatePeriod periodTime, int executionAttr, String companyId, String empCalAndSumExecLogID);
	
}

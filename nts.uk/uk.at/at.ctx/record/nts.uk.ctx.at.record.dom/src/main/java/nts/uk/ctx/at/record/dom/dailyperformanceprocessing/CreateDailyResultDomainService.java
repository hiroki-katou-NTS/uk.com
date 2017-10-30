package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 作成処理
 * アルゴリズム : ③日別実績の作成処理
 *
 */
public interface CreateDailyResultDomainService {

	int createDailyResult(List<String> emloyeeIds, int reCreateAttr, GeneralDate startDate, GeneralDate endDate, int executionAttr, String empCalAndSumExecLogID);
	
}

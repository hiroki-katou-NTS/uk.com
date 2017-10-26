package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 作成処理
 * アルゴリズム : ③日別実績の作成処理
 *
 */
public interface CreateDailyResultDomainService {

	boolean createDailyResult(List<String> emloyeeIds, int reCreateAttr, BigDecimal startDate, BigDecimal endDate, int executionAttr, long empCalAndSumExecLogID);
	
}

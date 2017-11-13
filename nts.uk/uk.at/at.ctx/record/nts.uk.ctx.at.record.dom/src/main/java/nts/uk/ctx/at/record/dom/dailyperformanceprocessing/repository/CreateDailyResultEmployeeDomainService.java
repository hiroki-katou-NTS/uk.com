package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt
 * 作成処理
 * 日別作成Mgrクラス . アルゴリズム . 社員の日別実績を作成する. ⑤社員の日別実績を作成する
 *
 */
public interface CreateDailyResultEmployeeDomainService {
	
	List<ClosureIdLockDto> createDailyResultEmployee(List<String> employeeIds, DatePeriod periodTimes,int reCreateAttr, String empCalAndSumExecLogID);

}

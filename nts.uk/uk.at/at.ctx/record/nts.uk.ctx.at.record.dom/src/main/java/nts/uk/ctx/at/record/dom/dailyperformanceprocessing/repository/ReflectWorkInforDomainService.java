package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt
 * 日別実績処理
 * 作成処理
 * ⑥１日の日別実績の作成処理
 * 勤務情報を反映する
 * 
 *
 */
public interface ReflectWorkInforDomainService {

	boolean reflectWorkInformation(List<String> employeeID, DatePeriod periodTimes, String empCalAndSumExecLogID, int reCreateAttr);
}

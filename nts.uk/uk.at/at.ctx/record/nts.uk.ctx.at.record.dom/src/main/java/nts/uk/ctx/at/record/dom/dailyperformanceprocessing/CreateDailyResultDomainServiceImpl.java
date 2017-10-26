package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;

public class CreateDailyResultDomainServiceImpl implements CreateDailyResultDomainService {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Override
	public boolean createDailyResult(List<String> emloyeeIds, int reCreateAttr, BigDecimal startDate,
			BigDecimal endDate, int executionAttr, long empCalAndSumExecLogID) {
		
		//日別作成を実行するかチェックする
		List<EmpCalAndSumExeLog> empCalAndSumExeLogs = this.empCalAndSumExeLogRepository.getListByExecutionContent(empCalAndSumExecLogID, 0);
		
		//④ログ情報（実行ログ）を更新する
		//パラメータ「実行区分」＝手動　の場合
		if(executionAttr == 0){
			
		}
		//ログ情報（実行ログ）を更新する - フロー終了 ( flow end)
		
		
		return false;
	}

}

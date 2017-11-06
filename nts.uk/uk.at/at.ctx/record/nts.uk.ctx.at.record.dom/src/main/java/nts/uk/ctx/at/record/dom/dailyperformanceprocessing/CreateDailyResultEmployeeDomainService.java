package nts.uk.ctx.at.record.dom.dailyperformanceprocessing;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author nampt
 * 作成処理
 * 日別作成Mgrクラス . アルゴリズム . 社員の日別実績を作成する. ⑤社員の日別実績を作成する
 *
 */
public interface CreateDailyResultEmployeeDomainService {
	
	int createDailyResultEmployee(String emloyeeId, GeneralDate startDate, GeneralDate endDate);

}

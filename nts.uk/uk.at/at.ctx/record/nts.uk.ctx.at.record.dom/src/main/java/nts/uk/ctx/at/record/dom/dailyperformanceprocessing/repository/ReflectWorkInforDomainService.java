package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

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

	void reflectWorkInformation(String companyID, String employeeID, GeneralDate processingDate, String empCalAndSumExecLogID, ExecutionType reCreateAttr);
	
	AffiliationInforState createAffiliationInforOfDailyPerfor(String companyId, String employeeId, GeneralDate day,String empCalAndSumExecLogID);
}

package nts.uk.ctx.at.request.dom.application.common.service;
/**
 * 
 * 16.その他
 *
 */
import java.util.List;

import nts.arc.time.GeneralDate;

public interface OtherCommonAlgorithmService {
	/**
	 * 1.職場別就業時間帯を取得
	 * @param companyID
	 * @param employeeID
	 * @param referenceDate
	 */
	public void getWorkingHoursByWorkplace(String companyID,String employeeID,GeneralDate referenceDate);
	/**
	 * 4.社員の当月の期間を算出する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param date 基準日
	 * @return List<String>: [0]: startDate, [1]: endDate <=> 締め期間(開始年月日,終了年月日) 
	 */
	public List<GeneralDate> employeePeriodCurrentMonthCalculate(String companyID, String employeeID, GeneralDate date);
	
}

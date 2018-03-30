package nts.uk.pub.spr.dailystatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.dailystatus.output.DailyStatusSpr;

/**
 * 03 実績残業報告の有無
 * @author Doan Duy Hung
 *
 */
public interface SprDailyStatusService {
	
	/**
	 * 03 実績残業報告の有無
	 * @param loginEmpCD
	 * @param employeeCD
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<DailyStatusSpr> getStatusOfDaily(String loginEmpCD, String employeeCD, String startDate, String endDate);
	
	/**
	 * パラメータチェック
	 * @param loginEmpCD
	 * @param employeeCD
	 * @param startDate
	 * @param endDate
	 */
	public void checkParam(String loginEmpCD, String employeeCD, String startDate, String endDate);
	
	/**
	 * 実績残業報告の有無
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<DailyStatusSpr> getEmpDailyStatus(String employeeID, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * 本人確認
	 * @param appDate
	 * @param employeeID
	 * @return
	 */
	public Integer getEmployeeStatus(GeneralDate appDate, String employeeID);
	
	/**
	 * 上司承認
	 * @param appDate
	 * @param employeeID
	 * @return
	 */
	public Integer getManagerStatus(GeneralDate appDate, String employeeID);
}

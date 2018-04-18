package nts.uk.pub.spr.appstatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.pub.spr.export.AppOvertimeStatusSprExport;
import nts.uk.pub.spr.appstatus.output.AppStatusSpr;

/**
 * 02 事前残業申請の有無
 * @author Doan Duy Hung
 *
 */
public interface SprAppStatusService {
	
	/**
	 * 02 事前残業申請の有無
	 * @param loginEmpCD
	 * @param employeeCD
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AppStatusSpr> getAppStatus(String loginEmpCD, String employeeCD, String startDate, String endDate);
	
	/**
	 * パラメータチェック
	 * @param loginEmpCD
	 * @param employeeCD
	 * @param startDate
	 * @param endDate
	 */
	public void checkParam(String loginEmpCD, String employeeCD, String startDate, String endDate);
	
	/**
	 * 事前残業申請の有無
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AppStatusSpr> getOverTimeAppStatus(String employeeID, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * 申請状況確認
	 * @param appDate
	 * @param employeeID
	 * @param overTimeAtr
	 * @return
	 */
	public AppOvertimeStatusSprExport getOverTimeAppInfo(GeneralDate appDate, String employeeID, Integer overTimeAtr);
	
}

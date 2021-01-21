package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampSetOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampCommonDomainService {
	
	// 打刻申請設定の取得
	public AppStampSetOutput appStampSet(String companyID); 
	
	// 申請理由の生成と検査
	public void appReasonCheck(String applicationReason, AppStamp_Old appStamp);
	
	public void validateReason(AppStamp_Old appStamp);
	
	public String getEmployeeName(String employeeID);
	
	public AppStamp_Old findByID(String companyID, String appID);
	
	public List<AttendanceResultImport> getAttendanceResult(String companyID, List<String> employeeIDLst, GeneralDate date, StampRequestMode_Old stampRequestMode);
}

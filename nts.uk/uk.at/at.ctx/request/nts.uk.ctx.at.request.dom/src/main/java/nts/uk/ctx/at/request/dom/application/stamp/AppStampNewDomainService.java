package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampSetOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampNewDomainService {
	
	// 打刻申請（新規）起動前処理
	public AppStampNewPreOutput appStampPreProcess(String companyID, String employeeID, GeneralDate appDate);
	
	// 外出／育児／介護の申請の新規登録
	public void appStampGoOutPermitRegister(String titleReason, String detailReason, AppStamp appStamp);
	
	// 出勤／退勤漏れの申請の新規登録
	public void appStampWorkRegister(String titleReason, String detailReason, AppStamp appStamp);
	
	// 打刻の取消申請の新規登録
	public void appStampCancelRegister(String titleReason, String detailReason, AppStamp appStamp);
	
	// レコーダイメージの申請の新規登録
	public void appStampOnlineRecordRegister(String titleReason, String detailReason, AppStamp appStamp);
	
	// その他打刻申請の新規登録
	public void appStampOtherRegister(String titleReason, String detailReason, AppStamp appStamp);
}

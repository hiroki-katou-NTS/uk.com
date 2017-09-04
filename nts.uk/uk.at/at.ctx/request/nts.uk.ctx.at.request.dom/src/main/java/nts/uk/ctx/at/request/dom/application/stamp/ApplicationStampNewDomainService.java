package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

public interface ApplicationStampNewDomainService {
	
	// 打刻申請（目次）起動
	public void appStampActivation(String companyID, String employeeID, int rootAtr, int appAtr, GeneralDate date);
	
	// 打刻申請（新規）起動前処理
	public void appStampPreProcess(String companyID, String employeeID, GeneralDate date, StampRequestMode stampRequestMode);
	
	// 外出／育児／介護の申請（新規）起動
	public void appStampGoOutPermitActivation(String companyID, String employeeID, GeneralDate date);
	
	// 外出／育児／介護の申請の新規登録
	public void appStampGoOutPermitRegister(Application application, ApplicationStamp applicationStamp);
	
	// 出勤／退勤漏れの申請（新規）起動 
	public void appStampWorkActivation(String companyID, String employeeID, GeneralDate date);
	
	// 出勤／退勤漏れの申請の新規登録
	public void appStampWorkRegister(Application application, ApplicationStamp applicationStamp);
	
	// 打刻の取消申請（新規）起動
	public void appStampCancelActivation(String companyID, String employeeID, GeneralDate date);
	
	// 打刻の取消申請の新規登録
	public void appStampCancelRegister(Application application, ApplicationStamp applicationStamp);
	
	// レコーダイメージの申請（新規）起動
	public void appStampOnlineRecordActivation(String companyID, String employeeID, GeneralDate date);
	
	// レコーダイメージの申請の新規登録
	public void appStampOnlineRecordRegister(Application application, ApplicationStamp applicationStamp);
	
	// その他打刻申請（新規）起動
	public void appStampOtherActivation(String companyID, String employeeID, GeneralDate date);
	
	// その他打刻申請の新規登録
	public void appStampOtherRegister(Application application, ApplicationStamp applicationStamp);
	
	// 打刻申請設定の取得
	public void appStampSet(String employeeID); 
	
	// 打刻申請の新規登録
	public void appStampRegistration();
	
	// 申請理由の生成と検査
	public void appReasonCheck();
	
}

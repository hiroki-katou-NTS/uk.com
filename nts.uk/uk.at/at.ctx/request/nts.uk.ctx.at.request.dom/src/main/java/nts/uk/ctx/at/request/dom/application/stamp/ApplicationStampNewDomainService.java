package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

public interface ApplicationStampNewDomainService {
	
	// 打刻申請（目次）起動
	public void appStampActivation(ApplicationStamp applicationStamp);
	
	// 打刻申請（新規）起動前処理
	public void appStampPreProcess(ApplicationStamp applicationStamp);
	
	// 外出／育児／介護の申請（新規）起動
	public void appStampGoOutPermitActivation(ApplicationStamp applicationStamp);
	
	// 外出／育児／介護の申請の新規登録
	public void appStampGoOutPermitRegister(ApplicationStamp applicationStamp);
	
	// 出勤／退勤漏れの申請（新規）起動 
	public void appStampWorkActivation(ApplicationStamp applicationStamp);
	
	// 出勤／退勤漏れの申請の新規登録
	public void appStampWorkRegister(ApplicationStamp applicationStamp);
	
	// 打刻の取消申請（新規）起動
	public void appStampCancelActivation(ApplicationStamp applicationStamp);
	
	// 打刻の取消申請の新規登録
	public void appStampCancelRegister(ApplicationStamp applicationStamp);
	
	// レコーダイメージの申請（新規）起動
	public void appStampOnlineRecordActivation(ApplicationStamp applicationStamp);
	
	// レコーダイメージの申請の新規登録
	public void appStampOnlineRecordRegister(ApplicationStamp applicationStamp);
	
	// その他打刻申請（新規）起動
	public void appStampOtherActivation(ApplicationStamp applicationStamp);
	
	// その他打刻申請の新規登録
	public void appStampOtherRegister(ApplicationStamp applicationStamp);
	
	// 打刻申請設定の取得
	public void appStampSet(String employeeID); 
	
	// 打刻申請の新規登録
	public void appStampRegistration(ApplicationStamp applicationStamp);
	
	// 申請理由の生成と検査
	public void appReasonCheck(String titleReason, String detailReason, Application application);
	
}

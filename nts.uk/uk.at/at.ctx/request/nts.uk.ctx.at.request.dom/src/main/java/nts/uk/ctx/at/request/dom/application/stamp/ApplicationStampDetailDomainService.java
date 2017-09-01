package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

public interface ApplicationStampDetailDomainService {
	
	// 打刻申請（詳細）起動前処理
	public void appStampPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// 外出／育児／介護の許可申請（詳細）起動
	public void appStampGoOutPermitPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// 外出／育児／介護の許可申請の更新登録
	public void appStampGoOutPermitUpdate(Application application);
	
	// 出勤／退勤漏れの申請（詳細）起動
	public void appStampWorkPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// 出勤／退勤漏れの申請の更新登録
	public void appStampWorkUpdate(Application application);
	
	// 打刻の取消申請（詳細）起動
	public void appStampCancelPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// 打刻の取消申請の更新登録
	public void appStampCancelUpdate(Application application);
	
	// レコーダイメージの申請（詳細）起動
	public void appStampOnlineRecordPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// レコーダイメージの申請の更新登録
	public void appStampOnlineRecordUpdate(Application application);
	
	// その他打刻申請（詳細）起動
	public void appStampOtherPreProcess(String companyID, String appID, String employeeID, GeneralDate date);
	
	// その他打刻申請の更新登録
	public void appStampOtherUpdate(Application application);
}

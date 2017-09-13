package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

public interface ApplicationStampDetailDomainService {
	
	// 打刻申請（詳細）起動前処理
	public void appStampPreProcess(ApplicationStamp applicationStamp);
	
	// 外出／育児／介護の許可申請（詳細）起動
	public void appStampGoOutPermitPreProcess(ApplicationStamp applicationStamp);
	
	// 外出／育児／介護の許可申請の更新登録
	public void appStampGoOutPermitUpdate(ApplicationStamp applicationStamp);
	
	// 出勤／退勤漏れの申請（詳細）起動
	public void appStampWorkPreProcess(ApplicationStamp applicationStamp);
	
	// 出勤／退勤漏れの申請の更新登録
	public void appStampWorkUpdate(ApplicationStamp applicationStamp);
	
	// 打刻の取消申請（詳細）起動
	public void appStampCancelPreProcess(ApplicationStamp applicationStamp);
	
	// 打刻の取消申請の更新登録
	public void appStampCancelUpdate(ApplicationStamp applicationStamp);
	
	// レコーダイメージの申請（詳細）起動
	public void appStampOnlineRecordPreProcess(ApplicationStamp applicationStamp);
	
	// レコーダイメージの申請の更新登録
	public void appStampOnlineRecordUpdate(ApplicationStamp applicationStamp);
	
	// その他打刻申請（詳細）起動
	public void appStampOtherPreProcess(ApplicationStamp applicationStamp);
	
	// その他打刻申請の更新登録
	public void appStampOtherUpdate(ApplicationStamp applicationStamp);
}

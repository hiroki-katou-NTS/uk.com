package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampDetailDomainService {
	
	// 打刻申請（詳細）起動前処理
	public void appStampPreProcess(AppStamp appStamp);
	
	// 外出／育児／介護の許可申請の更新登録
	public void appStampGoOutPermitUpdate(String titleReason, String detailReason, AppStamp appStamp);
	
	// 出勤／退勤漏れの申請の更新登録
	public void appStampWorkUpdate(String titleReason, String detailReason, AppStamp appStamp);
	
	// 打刻の取消申請の更新登録
	public void appStampCancelUpdate(String titleReason, String detailReason, AppStamp appStamp);
	
	// レコーダイメージの申請の更新登録
	public void appStampOnlineRecordUpdate(String titleReason, String detailReason, AppStamp appStamp);
	
	// その他打刻申請の更新登録
	public void appStampOtherUpdate(String titleReason, String detailReason, AppStamp appStamp);
}

package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

/**
 * 
 * @author hieult
 *
 */
/**
 * 
 * 10-2.詳細画面解除後の処理
 *
 */
public interface DetailAfterRelease {
	
	/**
	 * 10-2.詳細画面解除後の処理
	 * @param application Application
	 * @param loginID Login ID
	 */
	public void detailAfterRelease(String companyID, String appID, String loginID, String memo);
	
}

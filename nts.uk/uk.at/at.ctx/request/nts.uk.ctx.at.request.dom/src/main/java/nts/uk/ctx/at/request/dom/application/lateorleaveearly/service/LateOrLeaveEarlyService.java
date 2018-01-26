package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;

/**
 * 
 * @author hieult
 *
 */


public interface LateOrLeaveEarlyService {
	
	boolean isExist(String companyID, String appID);
	/**
	 * 事前制約をチェックする (Kiểm tra 事前制約)
	 * ドメインモデル「遅刻早退取消申請」の新規登録する
	 * @param lateOrLeaveEarly
	 */
	void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly);
	
	void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly);
	
	void deleteLateOrLeaveEarly (String companyID, String appID);
	
    void registerLateOrLeaveEarly (String companyID);
    
    public String getApplicantName(String employeeID);
	
    void changeApplication (String companyID , String appID, GeneralDate applicationDate, int actualCancelAtr, int early1,
			int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason);
    
    public LateOrLeaveEarly findByID(String companyID, String appID);
}

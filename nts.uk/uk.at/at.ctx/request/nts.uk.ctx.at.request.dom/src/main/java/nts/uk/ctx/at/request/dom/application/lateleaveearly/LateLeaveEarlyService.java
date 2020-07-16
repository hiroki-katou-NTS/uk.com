package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;

public interface LateLeaveEarlyService {
	
	/**
	 * getLateLeaveEarlyInfo
	 * 
	 * 遅刻早退取消申請起動時の表示情報
	 * @return ArrivedLateLeaveEarlyInfoOutput the lateEarlyLeaveInfo
	 */
	ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(String appId);
}

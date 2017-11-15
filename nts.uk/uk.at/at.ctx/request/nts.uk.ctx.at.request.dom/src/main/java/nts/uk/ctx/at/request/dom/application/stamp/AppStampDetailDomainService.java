package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampDetailDomainService {
	
	// 打刻申請（詳細）起動前処理
	public void appStampPreProcess(AppStamp appStamp);
	
	public void appStampUpdate(String applicationReason, AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases);
}

package nts.uk.ctx.at.request.dom.application.stamp;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampDetailDomainService {
	
	// 打刻申請（詳細）起動前処理
	public void appStampPreProcess(AppStamp_Old appStamp);
	
	public ProcessResult appStampUpdate(String applicationReason, AppStamp_Old appStamp);
}

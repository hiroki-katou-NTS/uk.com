package nts.uk.ctx.at.request.dom.reasonappdaily;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

/**
 * @author thanh_nx
 *
 *         申請理由情報
 */
@Getter
public class ApplicationReasonInfo {

	// 定型
	private final AppStandardReasonCode standardReasonCode;

	// 申請理由
	private final AppReason opAppReason;

	public ApplicationReasonInfo(AppStandardReasonCode standardReasonCode, AppReason opAppReason) {
		this.standardReasonCode = standardReasonCode;
		this.opAppReason = opAppReason;
	}
	
	
}

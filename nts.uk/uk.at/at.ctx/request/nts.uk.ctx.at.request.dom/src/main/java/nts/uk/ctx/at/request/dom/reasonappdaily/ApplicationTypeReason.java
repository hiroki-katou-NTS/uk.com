package nts.uk.ctx.at.request.dom.reasonappdaily;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;

/**
 * @author thanh_nx
 *
 *         申請理由の申請種類
 */
@Getter
public class ApplicationTypeReason {

	// 申請種類
	private final ApplicationType appType;

	// 残業申請区分
	private final Optional<OvertimeAppAtr> overAppAtr;

	public ApplicationTypeReason(ApplicationType appType, Optional<OvertimeAppAtr> overAppAtr) {
		this.appType = appType;
		this.overAppAtr = overAppAtr;
	}

}

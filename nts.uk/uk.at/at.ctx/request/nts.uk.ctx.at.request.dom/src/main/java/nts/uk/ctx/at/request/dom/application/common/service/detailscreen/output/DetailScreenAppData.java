package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application_New;

/**
 * 申請データの内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DetailScreenAppData {
	
	/**
	 * 申請
	 */
	Application_New application;
	
	
	DetailScreenApprovalData detailScreenApprovalData;
}

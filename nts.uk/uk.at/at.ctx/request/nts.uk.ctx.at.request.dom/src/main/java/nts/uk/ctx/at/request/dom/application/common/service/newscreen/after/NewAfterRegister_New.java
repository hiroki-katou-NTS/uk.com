package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 
 * 2-3.新規画面登録後の処理
 *
 */
public interface NewAfterRegister_New {
	
	/**
	 * 2-3.新規画面登録後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 */
	public ProcessResult processAfterRegister(Application_New application);
	
}

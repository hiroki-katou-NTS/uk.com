package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
/**
 * 
 * 4-2.詳細画面登録後の処理
 *
 */
public interface DetailAfterUpdate {
	/**
	 * 4-2.詳細画面登録後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 */
	public ProcessResult processAfterDetailScreenRegistration(Application_New application);
}

package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application_New;
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
	public List<String> processAfterDetailScreenRegistration(Application_New application);
}

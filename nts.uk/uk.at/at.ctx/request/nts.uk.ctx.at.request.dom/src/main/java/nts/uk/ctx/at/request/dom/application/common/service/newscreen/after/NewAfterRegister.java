package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * 
 * 2-3.新規画面登録後の処理
 *
 */
public interface NewAfterRegister {
	
	/**
	 * 2-3.新規画面登録後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 */
	public String processAfterRegister(Application application);
	
	/**
	 * 1.送信先リストの取得
	 * @param application 取得したドメインモデル「申請」
	 * @return destination list 送信先一覧
	 */
	public List<String> acquireDestinationList(Application application);
}

package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.12.詳細画面取消の処理(cancel)
 * @author Doan Duy Hung
 *
 */
public interface ProcessCancel {
	/**
	 * refactor 4
	 * 12.詳細画面取消の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param application 申請データの内容
	 */
	public void detailScreenCancelProcess(String companyID, String appID, Application application);
}

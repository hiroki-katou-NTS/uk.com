package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.4-2.詳細画面登録後の処理(afterUpdate)
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterUpdate {
	/**
	 * 4-2.詳細画面登録後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param appDispInfoStartupOutput 申請表示情報
	 */
	public ProcessResult processAfterDetailScreenRegistration(String companyID, String appID, AppDispInfoStartupOutput appDispInfoStartupOutput);
}

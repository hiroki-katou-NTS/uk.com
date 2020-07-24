package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.10-2.詳細画面解除後の処理(afterRelease)
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterRelease {
	
	/**
	 * 10-2.詳細画面解除後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param application 申請データの内容
	 * @return
	 */
	public ProcessResult detailAfterRelease(String companyID, String appID, Application application);
	
}

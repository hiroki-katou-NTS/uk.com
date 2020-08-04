package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.8-2.詳細画面承認後の処理(afterApprove)
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterApproval_New {
	
	/**
	 * 8-2.詳細画面承認後の処理 
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param application 申請データの内容
	 * @param appDispInfoStartupOutput 申請表示情報
	 * @param memo 承認コメント
	 * @return
	 */
	public ProcessResult doApproval(String companyID, String appID, Application application, 
			AppDispInfoStartupOutput appDispInfoStartupOutput, String memo);
	
}

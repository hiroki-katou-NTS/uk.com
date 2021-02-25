package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenAppData;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenApprovalData;

public interface DetailScreenBefore {
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.15.詳細画面申請データを取得する(getApplicationData).15.詳細画面申請データを取得する
	 * @param appID
	 * @return
	 */
	public DetailScreenAppData getDetailScreenAppData(String appID);
	
	/**
	 * 15-1.詳細画面の承認コメントを取得する
	 * @param appID 申請ID
	 * @return
	 */
	public DetailScreenApprovalData getApprovalDetail(String appID);
	
}

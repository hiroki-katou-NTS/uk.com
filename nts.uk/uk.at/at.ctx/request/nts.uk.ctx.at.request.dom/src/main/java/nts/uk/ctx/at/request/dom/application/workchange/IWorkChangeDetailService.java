package nts.uk.ctx.at.request.dom.application.workchange;

public interface IWorkChangeDetailService {
	/**
	 * アルゴリズム「勤務変更申請基本データ（更新）」を実行する
	 * @param cid: 会社ID
	 * @param appId: 申請者ID
	 * @return 勤務変更申請基本データ
	 */
	WorkChangeDetail getWorkChangeDetailById(String cid, String appId);
}

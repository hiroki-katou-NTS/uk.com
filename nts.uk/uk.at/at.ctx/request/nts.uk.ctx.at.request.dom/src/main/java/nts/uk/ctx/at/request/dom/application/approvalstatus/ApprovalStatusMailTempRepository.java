package nts.uk.ctx.at.request.dom.application.approvalstatus;

import java.util.Optional;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public interface ApprovalStatusMailTempRepository {
	/**
	 * ドメインモデル「承認状況メールテンプレート」を取得する
	 * 
	 * @param cid
	 *            会社ID
	 * @param type
	 *            メール種類
	 * @return ドメイン：承認状況メールテンプレート
	 */
	Optional<ApprovalStatusMailTemp> getApprovalStatusMailTempById(String cid, int type);

	void add(ApprovalStatusMailTemp domain);

	void update(ApprovalStatusMailTemp domain);
}

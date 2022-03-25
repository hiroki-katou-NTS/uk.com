package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.Optional;

/**
 * 自分の承認者の運用設定Repository
 */
public interface ApproverOperationSettingsRepository {

	/**
	 * [1] Insert(自分の承認者の運用設定)
	 */
	public void insert(ApproverOperationSettings domain);
	
	/**
	 * [2] Update(自分の承認者の運用設定)
	 */
	public void update(ApproverOperationSettings domain);
	
	/**
	 * [3]get - 会社IDから自分の承認者の運用設定を取得する
	 * @param cid 会社ID
	 * @return 自分の承認者の運用設定
	 */
	public Optional<ApproverOperationSettings> get(String cid);
	
	/**
	 * [4] 承認者登録の運用を取得する
	 * @param cid 会社ID
	 * @return 運用モード
	 */
	public Optional<OperationMode> getOperationOfApproverRegis(String cid);
	
}

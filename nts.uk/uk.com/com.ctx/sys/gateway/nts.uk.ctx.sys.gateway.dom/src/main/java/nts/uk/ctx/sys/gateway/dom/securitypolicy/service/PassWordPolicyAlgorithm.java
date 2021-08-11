package nts.uk.ctx.sys.gateway.dom.securitypolicy.service;

public interface PassWordPolicyAlgorithm {

	/**
	 * パスワードの期限切れチェック
	 * @param ユーザID userId
	 * @param パスワード有効期限 validityPeriod
	 * @return true: エラーあり; false: エラーなし
	 * @author hoatt
	 */
	public boolean checkExpiredPass(String userId, int validityPeriod);
	/**
	 * パスワード変更通知チェック
	 * @param userId
	 * @param validityPeriod
	 * @param notiPwChange
	 * @return 終了状態  and 【残り何日】
	 * @author hoatt
	 */
	public NotifyResult checkNotifyChangePass(String userId, int validityPeriod, int notiPwChange);
}

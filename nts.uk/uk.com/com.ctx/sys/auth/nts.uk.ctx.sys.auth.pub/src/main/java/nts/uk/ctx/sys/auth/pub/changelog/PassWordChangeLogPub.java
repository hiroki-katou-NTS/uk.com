package nts.uk.ctx.sys.auth.pub.changelog;

import java.util.List;

public interface PassWordChangeLogPub {

	/**
	 * get list パスワード変更ログ
	 * @param ユーザID userId
	 * @return
	 * @author hoatt
	 */
	List<PasswordChangeLogOut> getListPwChangeLog(String userId);
}

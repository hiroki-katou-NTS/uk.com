package nts.uk.ctx.sys.auth.pub.changelog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
@AllArgsConstructor
public class PasswordChangeLogOut {
	//ログID
	private String logID;
	//ユーザID
	private String userID;
	//変更日時
	private GeneralDateTime modifiedDate;
	//パスワード
	private String password;
}

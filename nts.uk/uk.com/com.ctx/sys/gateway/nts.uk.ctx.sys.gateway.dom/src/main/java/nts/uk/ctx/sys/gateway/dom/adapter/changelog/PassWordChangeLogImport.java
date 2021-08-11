package nts.uk.ctx.sys.gateway.dom.adapter.changelog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
@AllArgsConstructor
public class PassWordChangeLogImport {

	//ログID
	private String logID;
	//ユーザID
	private String userID;
	//変更日時
	private GeneralDateTime modifiedDate;
	//パスワード
	private String password;
}

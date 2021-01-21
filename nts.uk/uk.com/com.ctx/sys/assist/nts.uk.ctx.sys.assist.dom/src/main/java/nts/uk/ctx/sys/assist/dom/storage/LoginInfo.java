package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.補助機能.データ保存.データ保存の保存結果.データ保存の保存結果.ログイン情報
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
	
	//field IPアドレス
	private String ipAddress;
	
	//field PC名
	private String pcName;
	
	//field アカウント
	private String account;
}

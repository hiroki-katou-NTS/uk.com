package nts.uk.ctx.at.request.dom.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.送信・送信後チェック.承認者情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SendMailApproverInfoParam {
	/**
	 * 承認者ID
	 */
	private String approverID;
	
	/**
	 * 承認者のメールアドレス
	 */
	private String approverMail;
	
	/**
	 * 承認者名
	 */
	private String approverName;
}

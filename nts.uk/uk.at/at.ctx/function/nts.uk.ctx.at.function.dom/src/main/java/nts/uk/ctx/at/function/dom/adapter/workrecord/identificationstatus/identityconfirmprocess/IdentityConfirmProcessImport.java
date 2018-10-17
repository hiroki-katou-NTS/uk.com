package nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess;

import lombok.Value;

/**
 * 
 * @author thuongtv
 *
 */
@Value
public class IdentityConfirmProcessImport {

	
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 日の本人確認を利用する
	 */
	private int useDailySelfCk;

	/**
	 * 月の本人確認を利用する
	 */
	private int useMonthSelfCK;

	/**
	 * エラーがある場合の本人確認
	 */
	private Integer yourselfConfirmError;
}

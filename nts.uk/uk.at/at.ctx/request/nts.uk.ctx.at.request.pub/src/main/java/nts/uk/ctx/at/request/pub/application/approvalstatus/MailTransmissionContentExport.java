package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * メール送信内容
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class MailTransmissionContentExport {
	/**
	 * 社員ID
	 */
	private String sId;

	/**
	 * 社員名
	 */
	private String sName;

	/**
	 * メールアドレス
	 */
	private String mailAddr;

	/**
	 * 件名
	 */
	private String subject;

	/**
	 * 送信本文
	 */
	private String text;
}

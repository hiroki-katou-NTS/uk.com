package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 */
@Value
@AllArgsConstructor
public class MailTransmissionContentOutput {
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

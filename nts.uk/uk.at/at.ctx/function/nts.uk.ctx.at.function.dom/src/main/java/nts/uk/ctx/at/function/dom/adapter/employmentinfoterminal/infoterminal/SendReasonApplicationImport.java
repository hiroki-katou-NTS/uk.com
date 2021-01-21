package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         申請理由送信Import
 */
@Value
public class SendReasonApplicationImport {

	/**
	 * 申請理由番号
	 */
	private final String appReasonNo;

	/**
	 * 申請理由名称
	 */
	private final String appReasonName;

	public SendReasonApplicationImport(String appReasonNo, String appReasonName) {
		this.appReasonNo = appReasonNo;
		this.appReasonName = appReasonName;
	}
}

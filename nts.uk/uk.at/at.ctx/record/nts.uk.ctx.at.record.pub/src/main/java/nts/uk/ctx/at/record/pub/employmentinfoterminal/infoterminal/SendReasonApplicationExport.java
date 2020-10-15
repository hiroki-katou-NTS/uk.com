package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         申請理由送信Export
 */
@Value
public class SendReasonApplicationExport implements SendNRInfoDataExport {

	/**
	 * 申請理由番号
	 */
	private final String appReasonNo;

	/**
	 * 申請理由名称
	 */
	private final String appReasonName;

	public SendReasonApplicationExport(String appReasonNo, String appReasonName) {
		super();
		this.appReasonNo = appReasonNo;
		this.appReasonName = appReasonName;
	}
}

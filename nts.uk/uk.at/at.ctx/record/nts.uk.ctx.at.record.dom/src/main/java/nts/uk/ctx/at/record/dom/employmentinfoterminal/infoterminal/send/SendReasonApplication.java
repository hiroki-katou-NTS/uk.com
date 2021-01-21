package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         申請理由送信
 */
@Value
public class SendReasonApplication implements DomainValue {

	/**
	 * 申請理由番号
	 */
	private final String appReasonNo;

	/**
	 * 申請理由名称
	 */
	private final String appReasonName;

	public SendReasonApplication(String appReasonNo, String appReasonName) {
		super();
		this.appReasonNo = appReasonNo;
		this.appReasonName = appReasonName;
	}

}

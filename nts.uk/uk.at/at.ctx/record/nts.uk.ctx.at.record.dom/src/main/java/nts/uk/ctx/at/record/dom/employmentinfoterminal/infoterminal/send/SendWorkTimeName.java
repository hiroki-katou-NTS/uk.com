package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         就業時間帯送信
 */
@Value
public class SendWorkTimeName implements DomainValue {

	/**
	 * 就業時間帯番号
	 */
	private final String workTimeNumber;

	/**
	 * 就業時間帯名
	 */
	private final String workTimeName;

	/**
	 * 開始終了時刻
	 */
	private final String time;

	public SendWorkTimeName(String workTimeNumber, String workTimeName, String time) {
		super();
		this.workTimeNumber = workTimeNumber;
		this.workTimeName = workTimeName;
		this.time = time;
	}

}

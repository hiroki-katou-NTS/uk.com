package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         就業時間帯送信Import
 */
@Value
public class SendWorkTimeNameImport {

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

	public SendWorkTimeNameImport(String workTimeNumber, String workTimeName, String time) {
		super();
		this.workTimeNumber = workTimeNumber;
		this.workTimeName = workTimeName;
		this.time = time;
	}

}

package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         就業時間帯送信Export
 */
@Value
public class SendWorkTimeNameExport implements SendNRInfoDataExport {

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

	public SendWorkTimeNameExport(String workTimeNumber, String workTimeName, String time) {
		super();
		this.workTimeNumber = workTimeNumber;
		this.workTimeName = workTimeName;
		this.time = time;
	}

}

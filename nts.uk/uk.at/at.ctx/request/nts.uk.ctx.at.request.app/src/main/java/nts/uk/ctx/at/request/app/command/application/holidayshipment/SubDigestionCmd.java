package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SubDigestionCmd {

	/**
	 * 使用日数
	 */
	private int daysUsedNo;

	/**
	 * 振出管理データ
	 */
	private String payoutMngDataID;

	/**
	 * 振出状態
	 */
	private int pickUpState;

	/**
	 * 振休発生日
	 */
	private GeneralDate occurrenceDate;

	/**
	 * 日付不明
	 */
	private int unknownDate;
}

package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SubTargetDigestionCmd {
	/**
	 * 使用時間数
	 */
	private GeneralDate hoursUsed;

	/**
	 * 休出管理データ
	 */
	private String leaveMngDataID;
	/**
	 * 休出発生日
	 */
	private GeneralDate breakOutDate;

	/**
	 * 休出状態
	 */
	private int restState;

	/**
	 * 日付不明
	 */
	private int unknownDate;
}

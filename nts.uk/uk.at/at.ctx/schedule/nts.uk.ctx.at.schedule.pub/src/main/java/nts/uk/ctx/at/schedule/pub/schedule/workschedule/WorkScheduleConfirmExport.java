package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author thanh_nx
 *
 *         勤務予定の確定状態
 */
@AllArgsConstructor
@Getter
public class WorkScheduleConfirmExport {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The date. */
	// 年月日
	private GeneralDate date;

	// 予定確定区分
	private SCConfirmedAtrExport confirmedAtr;

	/**
	 * 予定確定区分
	 */
	@AllArgsConstructor
	public static enum SCConfirmedAtrExport {

		/** The unsettled. */
		// 未確定
		UNSETTLED(0, "未確定"),
		// 確定済み
		CONFIRMED(1, "確定済み");

		/** The value. */
		public final int value;

		/** The description. */
		public final String description;
	}
}

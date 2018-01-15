package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftAlarm {
	/**
	 * カテゴリ
	 */
	private int category;
	/**
	 * メッセージ
	 */
	private String message;
	/**
	 * 条件
	 */
	private int conditions;
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
}

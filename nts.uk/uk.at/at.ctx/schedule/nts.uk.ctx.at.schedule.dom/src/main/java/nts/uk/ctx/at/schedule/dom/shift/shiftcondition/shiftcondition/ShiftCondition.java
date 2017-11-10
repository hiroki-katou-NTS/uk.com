package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * シフト条件
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class ShiftCondition extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * カテゴリNO
	 */
	private int categoryNo;
	/**
	 * 条件NO
	 */
	private int conditionNo;
	/**
	 * 条件エラーメッセージ
	 */
	private ShiftConditionErrorMessage conditionErrorMessage;
	/**
	 * 条件名称
	 */
	private ShiftConditionName conditionName;
	/**
	 * 条件詳細NO
	 */
	private int conditionDetailsNo;

	public static ShiftCondition createFromJavaType(String companyId, int categoryNo, int conditionNo,
			String conditionErrorMessage, String conditionName, int conditionDetailsNo) {
		return new ShiftCondition(companyId, categoryNo, conditionNo,
				new ShiftConditionErrorMessage(conditionErrorMessage), new ShiftConditionName(conditionName),
				conditionDetailsNo);
	}
}

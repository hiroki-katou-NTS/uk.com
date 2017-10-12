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
	private String companyId;
	private int categoryNo;
	private int conditionNo;
	private ShiftConditionErrorMessage conditionErrorMessage;
	private ShiftConditionName conditionName;
	private int conditionDetailsNo;

	public static ShiftCondition createFromJavaType(String companyId, int categoryNo, int conditionNo,
			String conditionErrorMessage, String conditionName, int conditionDetailsNo) {
		return new ShiftCondition(companyId, categoryNo, conditionNo,
				new ShiftConditionErrorMessage(conditionErrorMessage), new ShiftConditionName(conditionName),
				conditionDetailsNo);
	}
}

package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.InputCheckCondition;

/**
 * 
 * @author HungTT - 入力チェック
 *
 */

@Getter
@AllArgsConstructor
public class InputCheck extends CheckedCondition {

	private InputCheckCondition inputCheckCondition;

}

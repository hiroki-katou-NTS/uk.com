package nts.uk.ctx.at.shared.dom.alarmList.extractionResult.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.enums.InputCheckCondition;

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

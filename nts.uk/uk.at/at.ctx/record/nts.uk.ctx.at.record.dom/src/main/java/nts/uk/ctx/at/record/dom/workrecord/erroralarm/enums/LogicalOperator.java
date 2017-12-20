/**
 * 5:02:27 PM Nov 3, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
//論理演算子
@AllArgsConstructor
public enum LogicalOperator {

	//AND
	AND(0, "Enum_LogicalOperator_And"),
	
	//OR
	OR(1, "Enum_LogicalOperator_Or");
	
	public final int value;

	public final String nameId;
}

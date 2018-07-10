/**
 * 11:20:07 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import lombok.AllArgsConstructor;

/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
public enum CheckOperatorType {

	SINGLE_VALUE(0, "single"),
	RANGE_VALUE(1, "range");	

	public final int value;

	public final String nameId;
	
}

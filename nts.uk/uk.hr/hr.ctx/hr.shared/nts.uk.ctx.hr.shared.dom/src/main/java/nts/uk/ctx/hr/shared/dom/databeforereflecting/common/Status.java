/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import lombok.AllArgsConstructor;

/**
 * ステータス
 *
 */
@AllArgsConstructor
public enum Status {
	Unregistered(0),

	Registered(1),
	
	WaitingReflection(2),
	
	Reflected(3);

	public final int value;
}

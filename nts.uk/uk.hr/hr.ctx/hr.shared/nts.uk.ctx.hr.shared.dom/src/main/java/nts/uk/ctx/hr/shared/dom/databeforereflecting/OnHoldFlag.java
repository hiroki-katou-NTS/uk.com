/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting;

import lombok.AllArgsConstructor;

/**
 * 保留
 */
@AllArgsConstructor
public enum OnHoldFlag {
	
	Normal(0),

	OnHold(1);

	public final int value;
}

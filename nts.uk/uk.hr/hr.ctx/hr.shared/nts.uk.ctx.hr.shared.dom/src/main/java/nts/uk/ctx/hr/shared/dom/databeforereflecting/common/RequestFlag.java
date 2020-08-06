/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

import lombok.AllArgsConstructor;

/**
 * 届出区分
 */
@AllArgsConstructor
public enum RequestFlag {

	Normal(0), //通常

	Report(1); //届出

	public final int value;

}

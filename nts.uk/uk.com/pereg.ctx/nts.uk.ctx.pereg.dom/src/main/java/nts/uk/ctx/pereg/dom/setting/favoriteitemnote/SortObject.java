/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteitemnote;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public enum SortObject {
	
	ASCENDING(0),
	
	DESCENDING(1),

	NOT_COVERED(2);
	
	public final int value;
}

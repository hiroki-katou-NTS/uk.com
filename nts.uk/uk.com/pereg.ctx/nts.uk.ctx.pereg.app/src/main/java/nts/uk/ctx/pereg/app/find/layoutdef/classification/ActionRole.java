/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public enum ActionRole {
	
	HIDDEN(1),
	
	VIEW_ONLY(2),
	
	EDIT(3);
	
	public final int value;
	
}

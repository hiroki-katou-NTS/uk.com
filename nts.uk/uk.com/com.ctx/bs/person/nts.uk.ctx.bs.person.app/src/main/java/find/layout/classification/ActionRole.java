/**
 * 
 */
package find.layout.classification;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public enum ActionRole {
	
	HIDDEN(0),
	
	VIEW_ONLY(1),
	
	EDIT(2);
	
	public final int value;
	
}

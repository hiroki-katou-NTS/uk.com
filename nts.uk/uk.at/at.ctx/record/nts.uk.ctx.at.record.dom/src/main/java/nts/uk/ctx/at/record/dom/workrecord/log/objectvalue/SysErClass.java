/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public enum SysErClass {
	
	NoError(0),
	
	DBAccessFailure(1),
	
	ManMasShortage(2);
	
	public final int value;

}

/**
 * 10:23:42 AM Nov 3, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum FilterByCompare {
	
	DO_NOT_COMPARE(0, "ENUM_DO_NOT_COMPARE"),
	
	EXTRACT_SAME(1, "ENUM_EXTRACT_SAME"),
	
	EXTRACT_DIFFERENT(2, "ENUM_EXTRACT_DIFFERENT");

	public final int value;

	public final String nameId;
	
}

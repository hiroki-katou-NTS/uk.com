/**
 * 10:23:42 AM Nov 3, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
//予実比較による絞り込み
@AllArgsConstructor
public enum FilterByCompare {
	
	//全て
	ALL(0, "Enum_TargetServiceType_All"),
	
	//選択
	SELECTED(1, "Enum_TargetServiceType_Selection"),
	
	//選択以外
	NOT_SELECTED(2, "Enum_TargetServiceType_OtherSelection");

	public final int value;

	public final String nameId;
	
}

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
	
	//予定と実績の比較をしない
	DO_NOT_COMPARE(0, "Enum_FilterByCompare_NotCompare"),
	
	//予定と実績が同じものを抽出する
	EXTRACT_SAME(1, "Enum_FilterByCompare_Extract_Same"),
	
	//予定と実績が異なるものを抽出する
	EXTRACT_DIFFERENT(2, "Enum_FilterByCompare_Extract_Different");

	public final int value;

	public final String nameId;
	
}

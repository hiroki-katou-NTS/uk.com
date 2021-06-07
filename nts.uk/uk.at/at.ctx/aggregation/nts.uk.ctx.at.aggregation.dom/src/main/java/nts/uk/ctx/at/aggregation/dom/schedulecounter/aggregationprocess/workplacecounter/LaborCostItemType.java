/**
 * 
 */
package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 職場計の人件費項目種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.人件費・時間.職場計の人件費項目種類
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum LaborCostItemType {
	/** 金額 **/
	AMOUNT(0),
	/** 時間**/
	TIME(1),
	/** 予算**/
	BUDGET(2);
	
	public final int value;
	
	public static LaborCostItemType of(int value) {
		return EnumAdaptor.valueOf(value, LaborCostItemType.class);
	}
}

package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 勤務計画実施表の出力設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の出力設定
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class ScheduleDailyTablePrintSetting implements DomainAggregate {
	
	/**
	 * コード
	 */
	private final ScheduleDailyTableCode code;
	
	/**
	 * 名称	
	 */
	private ScheduleDailyTableName name;
	
	/**
	 * 項目設定
	 */
	private ScheduleDailyTableItemSetting itemSetting;

}

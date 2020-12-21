package nts.uk.ctx.at.function.dom.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 勤務計画実施表の出力設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤務計画実施表.勤務計画実施表の出力設定
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class ScheduleDailyTablePrintSetting implements DomainAggregate {
	
	private final ScheduleDailyTableCode code;
	
	private ScheduleDailyTableDisplayInfo displayInfo;
	
	private ScheduleDailyTableItemSetting itemSetting;

}

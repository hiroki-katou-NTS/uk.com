package nts.uk.ctx.at.function.dom.scheduledailytable;

import java.util.Optional;

import lombok.Value;

/**
 * 勤務計画実施表の表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤務計画実施表.勤務計画実施表の表示情報
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableDisplayInfo {
	
	private ScheduleDailyTableName name;
	
	private Optional<ScheduleDailyTableComment> comment;
	
}

package nts.uk.ctx.at.function.dom.scheduledailytable;

import java.util.List;

import lombok.Value;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務計画実施表の印鑑欄
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤務計画実施表.勤務計画実施表の印鑑欄
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableInkanRow {
	
	private NotUseAtr notUseAtr;
	
	private List<ScheduleDailyTableInkanName> targetNames;

}

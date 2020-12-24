package nts.uk.ctx.at.function.dom.scheduledailytable;

import java.util.List;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務計画実施表の印鑑欄
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤務計画実施表.勤務計画実施表の印鑑欄
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableInkanRow {
	
	/**
	 * 使用区分
	 */
	private NotUseAtr notUseAtr;
	
	/**
	 * 見出しリスト
	 */
	private List<ScheduleDailyTableInkanName> titleList;
	
	/**
	 * 作る
	 * @param notUseAtr 使用区分
	 * @param titleList 見出しリスト
	 * @return
	 */
	public static ScheduleDailyTableInkanRow create(
			NotUseAtr notUseAtr,
			List<ScheduleDailyTableInkanName> titleList) {
		if ( titleList.size() > 6 ) {
			throw new BusinessException("Msg_2085");
		}
		
		return new ScheduleDailyTableInkanRow(notUseAtr, titleList);
	}

}

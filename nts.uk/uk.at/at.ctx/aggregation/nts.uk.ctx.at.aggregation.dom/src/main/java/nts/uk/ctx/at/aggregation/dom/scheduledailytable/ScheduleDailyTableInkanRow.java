package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.util.Collections;
import java.util.List;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務計画実施表の印鑑欄
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の印鑑欄
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableInkanRow {
	
	/**
	 * 使用区分
	 */
	private final NotUseAtr notUseAtr;
	
	/**
	 * 見出しリスト
	 */
	private final List<ScheduleDailyTableInkanTitle> titleList;
	
	/**
	 * 作る
	 * @param notUseAtr 使用区分
	 * @param titleList 見出しリスト
	 * @return
	 */
	public static ScheduleDailyTableInkanRow create(
			NotUseAtr notUseAtr,
			List<ScheduleDailyTableInkanTitle> titleList) {
		if ( titleList.size() > 6 ) {
			throw new BusinessException("Msg_2085");
		}
		
		if(notUseAtr == NotUseAtr.USE && titleList.isEmpty()) {
			throw new BusinessException("Msg_2222");
		}
		
		return new ScheduleDailyTableInkanRow(notUseAtr, titleList);
	}
	
	/**
	 * 印鑑欄見出しを取得する
	 * @return
	 */
	public List<ScheduleDailyTableInkanTitle> getInkanRowTitle(){
		return this.notUseAtr == NotUseAtr.USE? this.titleList: Collections.emptyList();
	} 
	
	/**
	 * 複製する
	 * @return
	 */
	public ScheduleDailyTableInkanRow reproduce() {
		return new ScheduleDailyTableInkanRow(this.notUseAtr, this.titleList);
	}

}

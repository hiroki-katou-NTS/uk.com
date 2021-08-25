package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	
	
	public static ScheduleDailyTablePrintSetting create(ScheduleDailyTableCode code
			,	ScheduleDailyTableName name
			,	ScheduleDailyTableItemSetting itemSetting) {
		
		if ( itemSetting.getPersonalCounter().size() > 10 ) {
			throw new BusinessException("Msg_2083");
		}
		
		if ( itemSetting.getWorkplaceCounter().size() > 5 ) {
			throw new BusinessException("Msg_2084");
		}
		
		if(itemSetting.getInkanRow().getTitleList().size() > 6) {
			throw new BusinessException("Msg_2085");
		}
		
		if (itemSetting.getInkanRow().getNotUseAtr() == NotUseAtr.USE
				&& itemSetting.getInkanRow().getTitleList().isEmpty()) {
			throw new BusinessException("Msg_2222");
		}
		
		return new ScheduleDailyTablePrintSetting(code, name, itemSetting);
	}
	
	/**
	 * 複製する
	 * @param detinationCode 複製先のコード
	 * @param destinationName 複製先の名称
	 * @return
	 */
	public ScheduleDailyTablePrintSetting clone(ScheduleDailyTableCode detinationCode
			,	ScheduleDailyTableName destinationName) {
		return new ScheduleDailyTablePrintSetting(
					detinationCode
				,	destinationName
				,	this.itemSetting.clone());
	}

}

package nts.uk.ctx.at.schedule.dom.shift.specificdayset;


import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 一日の特定日リスト
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.特定日設定.一日の特定日リスト
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class OneDaySpecificItem implements DomainValue {

	/** 特定日項目リスト **/
	private final List<SpecificDateItemNo> specificDayItems;
	
	public static OneDaySpecificItem create( List<SpecificDateItemNo> specificDayItems ) {
		
		if( specificDayItems.isEmpty() || specificDayItems.size() > 10 ) {
			
			throw new BusinessException( "Msg_2327" );
			
		}
		
		val itemFilters = specificDayItems.stream().distinct().collect(Collectors.toList());
		
		if( itemFilters.size() != specificDayItems.size() ) {
			
			throw new BusinessException( "Msg_2328" );
		}
		
		return new OneDaySpecificItem(	specificDayItems.stream()
										.sorted()
										.collect(Collectors.toList())
										);
	}
}

package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * AggregateRoot: 職場計の人件費・時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.人件費・時間.職場計の人件費・時間
 * @author dan_pv
 *
 */
@AllArgsConstructor
public class WorkplaceCounterLaborCostAndTime implements DomainAggregate{

	/**
	 * 人件費・時間リスト
	 */
	@Getter
	private Map<LaborCostAndTimeType, LaborCostAndTime> laborCostAndTimeList;
	
	/**
	 * @param list
	 * @return
	 */
	public static WorkplaceCounterLaborCostAndTime create(Map<LaborCostAndTimeType, LaborCostAndTime> list) {
		
		if ( list.isEmpty()) {
			throw new RuntimeException("invalid data");
		}
		
		boolean allNotUse = list.values().stream()
				.allMatch(e -> e.getUseClassification() == NotUseAtr.NOT_USE);
		
		if ( allNotUse ) {
			throw new BusinessException( "Msg_1836" );
		}
		
		return new WorkplaceCounterLaborCostAndTime(list);
	}
	
}

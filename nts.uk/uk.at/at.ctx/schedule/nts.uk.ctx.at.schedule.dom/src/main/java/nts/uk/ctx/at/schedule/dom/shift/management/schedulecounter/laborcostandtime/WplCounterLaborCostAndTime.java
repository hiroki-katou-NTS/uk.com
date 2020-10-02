package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 職場計の人件費・時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.人件費・時間.職場計の人件費・時間
 * @author dan_pv
 *
 */
@AllArgsConstructor
public class WplCounterLaborCostAndTime implements DomainAggregate{

	/**
	 * 人件費・時間リスト
	 */
	@Getter
	private Map<LaborCostAndTimeType, LaborCostAndTime> laborCostAndTimeList;
	
	/**
	 * @param list
	 * @return
	 */
	public static WplCounterLaborCostAndTime create(Map<LaborCostAndTimeType, LaborCostAndTime> list) {
		
		LaborCostAndTime total = list.get(LaborCostAndTimeType.TOTAL);
		LaborCostAndTime workingHours = list.get(LaborCostAndTimeType.WORKING_HOURS);
		LaborCostAndTime overtime = list.get(LaborCostAndTimeType.OVERTIME);
		
		boolean valid = total.getUseClassification() == NotUseAtr.USE ||
				workingHours.getUseClassification() == NotUseAtr.USE ||
				overtime.getUseClassification() == NotUseAtr.USE;
		if ( !valid ) {
			throw new BusinessException( "Msg_1836" );
		}
		
		return new WplCounterLaborCostAndTime(list);
	}
	
}

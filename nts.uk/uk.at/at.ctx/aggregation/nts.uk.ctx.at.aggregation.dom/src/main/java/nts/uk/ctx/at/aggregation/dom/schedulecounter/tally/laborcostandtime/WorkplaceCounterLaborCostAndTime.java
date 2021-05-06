package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 職場計の人件費・時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.人件費・時間.職場計の人件費・時間
 * @author dan_pv
 *
 */
@AllArgsConstructor
public class WorkplaceCounterLaborCostAndTime implements DomainAggregate{

	/** 人件費・時間リスト */
	@Getter
	private Map<AggregationUnitOfLaborCosts, LaborCostAndTime> laborCostAndTimeList;

	/**
	 * 作る
	 * @param list
	 * @return
	 */
	public static WorkplaceCounterLaborCostAndTime create(Map<AggregationUnitOfLaborCosts, LaborCostAndTime> list) {

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

package nts.uk.ctx.at.aggregation.dom.schedulecounter.laborcostandtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WorkplaceCounterLaborCostAndTimeTest {
	
	@Test
	public void create_with_businessException () {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregationUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.WITHIN, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.EXTRA, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		
		NtsAssert.businessException( "Msg_1836" , () -> {
			WorkplaceCounterLaborCostAndTime.create(list);
		});
	}
	
	@Test
	public void create_successfully () {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregationUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.WITHIN, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.EXTRA, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE));
		
		WorkplaceCounterLaborCostAndTime result = WorkplaceCounterLaborCostAndTime.create(list);
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> resultMap = result.getLaborCostAndTimeList();
		assertThat(resultMap.get(AggregationUnitOfLaborCosts.TOTAL).getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(resultMap.get(AggregationUnitOfLaborCosts.WITHIN).getUseClassification()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(resultMap.get(AggregationUnitOfLaborCosts.EXTRA).getUseClassification()).isEqualTo(NotUseAtr.USE);
		
	}
	
	@Test
	public void getters() {
		
		Map<AggregationUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregationUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.WITHIN, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregationUnitOfLaborCosts.EXTRA, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE));
		
		WorkplaceCounterLaborCostAndTime target = WorkplaceCounterLaborCostAndTime.create(list);
		
		NtsAssert.invokeGetters(target);
	}

}

package nts.uk.ctx.at.aggregation.dom.schedulecounter.laborcostandtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregateUnitOfLaborCosts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WorkplaceCounterLaborCostAndTimeTest {
	
	@Test
	public void create_with_businessException () {
		
		Map<AggregateUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregateUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.WORKING_HOURS, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.OVERTIME, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		
		NtsAssert.businessException( "Msg_1836" , () -> {
			WorkplaceCounterLaborCostAndTime.create(list);
		});
	}
	
	@Test
	public void create_successfully () {
		
		Map<AggregateUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregateUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.WORKING_HOURS, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.OVERTIME, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE));
		
		WorkplaceCounterLaborCostAndTime result = WorkplaceCounterLaborCostAndTime.create(list);
		
		Map<AggregateUnitOfLaborCosts, LaborCostAndTime> resultMap = result.getLaborCostAndTimeList();
		assertThat(resultMap.get(AggregateUnitOfLaborCosts.TOTAL).getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(resultMap.get(AggregateUnitOfLaborCosts.WORKING_HOURS).getUseClassification()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(resultMap.get(AggregateUnitOfLaborCosts.OVERTIME).getUseClassification()).isEqualTo(NotUseAtr.USE);
		
	}
	
	@Test
	public void getters() {
		
		Map<AggregateUnitOfLaborCosts, LaborCostAndTime> list = new HashMap<>();
		list.put(AggregateUnitOfLaborCosts.TOTAL, LaborCostAndTime.createWithBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.WORKING_HOURS, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(AggregateUnitOfLaborCosts.OVERTIME, LaborCostAndTime.createWithoutBudget(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE));
		
		WorkplaceCounterLaborCostAndTime target = WorkplaceCounterLaborCostAndTime.create(list);
		
		NtsAssert.invokeGetters(target);
	}

}

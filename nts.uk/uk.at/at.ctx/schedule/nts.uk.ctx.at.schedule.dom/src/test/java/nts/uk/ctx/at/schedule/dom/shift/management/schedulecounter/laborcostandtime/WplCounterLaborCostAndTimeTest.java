package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTimeType;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WplCounterLaborCostAndTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WplCounterLaborCostAndTimeTest {
	
	@Test
	public void create_with_businessException () {
		
		Map<LaborCostAndTimeType, LaborCostAndTime> list = new HashMap<>();
		list.put(LaborCostAndTimeType.TOTAL, LaborCostAndTime.createTotal(
				NotUseAtr.NOT_USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(LaborCostAndTimeType.WORKING_HOURS, LaborCostAndTime.create(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(LaborCostAndTimeType.OVERTIME, LaborCostAndTime.create(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		
		NtsAssert.businessException( "Msg_1836" , () -> {
			WplCounterLaborCostAndTime.create(list);
		});
	}
	
	@Test
	public void create_successfully () {
		
		Map<LaborCostAndTimeType, LaborCostAndTime> list = new HashMap<>();
		list.put(LaborCostAndTimeType.TOTAL, LaborCostAndTime.createTotal(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(LaborCostAndTimeType.WORKING_HOURS, LaborCostAndTime.create(
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE));
		list.put(LaborCostAndTimeType.OVERTIME, LaborCostAndTime.create(
				NotUseAtr.USE,
				NotUseAtr.USE,
				NotUseAtr.NOT_USE));
		
		WplCounterLaborCostAndTime result = WplCounterLaborCostAndTime.create(list);
		
		Map<LaborCostAndTimeType, LaborCostAndTime> resultMap = result.getLaborCostAndTimeList();
		assertThat(resultMap.get(LaborCostAndTimeType.TOTAL).getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(resultMap.get(LaborCostAndTimeType.WORKING_HOURS).getUseClassification()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(resultMap.get(LaborCostAndTimeType.OVERTIME).getUseClassification()).isEqualTo(NotUseAtr.USE);
		
	}

}

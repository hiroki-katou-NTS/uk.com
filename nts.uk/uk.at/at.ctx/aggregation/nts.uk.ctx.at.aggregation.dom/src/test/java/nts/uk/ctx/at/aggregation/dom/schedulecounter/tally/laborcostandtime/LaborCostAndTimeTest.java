package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.LaborCostItemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;


public class LaborCostAndTimeTest {

	@Test
	public void test_createTotal_with_Error() {

		NtsAssert.businessException("Msg_1953", () -> {

			LaborCostAndTime.createWithBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		});

	}

	@Test
	public void test_createTotal_successfully() {

		val result = LaborCostAndTime.createWithBudget(NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);

		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getBudget().get()).isEqualTo(NotUseAtr.USE);

	}

	@Test
	public void test_create_with_Error() {

		NtsAssert.businessException("Msg_1953", () -> {

			LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		});

	}


	@Test
	public void test_create_successfully() {

		val result = LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);

		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.USE);
	}

	@Test
	public void getters() {

		val target = LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		NtsAssert.invokeGetters(target);
	}
	
	
	@Test
	public void test_isTargetAggregation_false() {
		
		val target_not_use = new LaborCostAndTime(NotUseAtr.NOT_USE, NotUseAtr.USE, NotUseAtr.USE, Optional.empty());		
		val target_Time = Helper.createLaborCostAndTimeByTime(NotUseAtr.NOT_USE);
		val target_Amount = Helper.createLaborCostAndTimeByAmount(NotUseAtr.NOT_USE);
		val target_Budget = Helper.createLaborCostAndTimeByBudget(NotUseAtr.NOT_USE);
		
		assertThat(target_not_use.isTargetAggregation(LaborCostItemType.AMOUNT)).isFalse();
		assertThat(target_Time.isTargetAggregation(LaborCostItemType.TIME)).isFalse();
		assertThat(target_Time.isTargetAggregation(LaborCostItemType.AMOUNT)).isFalse();
		assertThat(target_Time.isTargetAggregation(LaborCostItemType.BUDGET)).isFalse();

		assertThat(target_Amount.isTargetAggregation(LaborCostItemType.AMOUNT)).isFalse();
		assertThat(target_Amount.isTargetAggregation(LaborCostItemType.TIME)).isFalse();
		assertThat(target_Amount.isTargetAggregation(LaborCostItemType.BUDGET)).isFalse();
		
		assertThat(target_Budget.isTargetAggregation(LaborCostItemType.BUDGET)).isFalse();
		assertThat(target_Budget.isTargetAggregation(LaborCostItemType.TIME)).isFalse();
		assertThat(target_Budget.isTargetAggregation(LaborCostItemType.AMOUNT)).isFalse();
	}
	
	@Test
	public void test_isTargetAggregation_true() {		
		val target_Time = Helper.createLaborCostAndTimeByTime(NotUseAtr.USE);
		val target_Amount = Helper.createLaborCostAndTimeByAmount(NotUseAtr.USE);
		val target_Budget = Helper.createLaborCostAndTimeByBudget(NotUseAtr.USE);
		
		assertThat(target_Time.isTargetAggregation(LaborCostItemType.TIME)).isTrue();
		assertThat(target_Amount.isTargetAggregation(LaborCostItemType.AMOUNT)).isTrue();
		assertThat(target_Budget.isTargetAggregation(LaborCostItemType.BUDGET)).isTrue();
	}
	
	static class Helper{
		
		public static LaborCostAndTime createLaborCostAndTimeByTime(NotUseAtr time) {
			return new LaborCostAndTime(NotUseAtr.USE, time, NotUseAtr.NOT_USE, Optional.empty());
		}
		
		public static LaborCostAndTime createLaborCostAndTimeByAmount(NotUseAtr amount) {
			return new LaborCostAndTime(NotUseAtr.USE, NotUseAtr.NOT_USE, amount, Optional.empty());
		}
		
		public static LaborCostAndTime createLaborCostAndTimeByBudget(NotUseAtr budget) {
			return new LaborCostAndTime(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, Optional.of(budget));
		}
	}
}

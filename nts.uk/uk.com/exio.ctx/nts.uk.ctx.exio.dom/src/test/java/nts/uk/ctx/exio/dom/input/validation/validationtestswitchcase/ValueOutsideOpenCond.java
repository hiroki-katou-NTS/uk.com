package nts.uk.ctx.exio.dom.input.validation.validationtestswitchcase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.ValidationHelper.DUMMY;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.integer.ImportingConditionInteger;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.integer.IntegerCondition;

public class ValueOutsideOpenCond {

	@Test
	public void success_Value1_LessThan_Condition1() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(-1);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isTrue();
	}
	
	
	@Test
	public void failed_Value1_Equal_Condition1() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(0);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}
	
	@Test
	public void failed_Value1_MoreThan_Condition1() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(25);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}
	
	
	@Test
	public void success_Value1_LessThan_Condition2() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(25);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}
	
	
	@Test
	public void failed_Value1_Equal_Condition2() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(50);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}
	
	@Test
	public void failed_Value1_MoreThan_Condition2() {
		Long conditionValue1 = Long.valueOf(0);
		Long conditionValue2 = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(51);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_OUTSIDE_OPEN_COND,
				Optional.of(new ImportingConditionInteger(conditionValue1)),
				Optional.of(new ImportingConditionInteger(conditionValue2)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isTrue();
	}
}

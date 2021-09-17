package nts.uk.ctx.exio.dom.input.validation.validationtestswitchcase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.ValidationHelper.DUMMY;
import nts.uk.ctx.exio.dom.input.validation.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.user.Validation;
import nts.uk.ctx.exio.dom.input.validation.user.type.numeric.integer.ImportingConditionInteger;
import nts.uk.ctx.exio.dom.input.validation.user.type.numeric.integer.IntegerCondition;

public class TargetEqualCond1 {

	@Test
	public void failed_Condition_MoreThen_Value1() {
		Long conditionValue = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(1);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_EQUAL_COND1,
				Optional.of(new ImportingConditionInteger(conditionValue)),
				Optional.of(new ImportingConditionInteger(DUMMY.LONG)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}
	
	@Test
	public void success_Condition_Equal_Value1() {
		Long conditionValue = Long.valueOf(50);
		Long validateValue1 = Long.valueOf(50);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_EQUAL_COND1,
				Optional.of(new ImportingConditionInteger(conditionValue)),
				Optional.of(new ImportingConditionInteger(DUMMY.LONG)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isTrue();
	}		
	
	@Test
	public void failed_Condition_LessThen_Value1() {
		Long conditionValue = Long.valueOf(1);
		Long validateValue1 = Long.valueOf(50);
		
		Validation validation = new IntegerCondition(
				CompareValueCondition.TARGET_EQUAL_COND1,
				Optional.of(new ImportingConditionInteger(conditionValue)),
				Optional.of(new ImportingConditionInteger(DUMMY.LONG)));
		
		DataItem dummyItem = new DataItem(DUMMY.ITEM_NO, validateValue1);
		
		assertThat(validation.validate(dummyItem)).isFalse();
	}	

}

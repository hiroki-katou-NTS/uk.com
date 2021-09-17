package nts.uk.ctx.exio.dom.input.validation.condition.user.type.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.string.Helper.DUMMY;
import nts.uk.ctx.exio.dom.input.validation.user.CompareStringCondition;
import nts.uk.ctx.exio.dom.input.validation.user.Validation;

public class StringConditionTest {

	@Test
	public void notCheck() {
		Validation validation  = Helper.notCompare;
		val result = validation.validate(null);
		assertThat(result).isTrue();
	}
	
	@Test
	public void condition_Equal_Result_Equal() {
		Validation validation  = Helper.DUMMY_VALIdATION(CompareStringCondition.EQUAL, DUMMY.VALUE1);
		val result = validation.validate(Helper.DUMMY_ITEM(DUMMY.VALUE1));
		assertThat(result).isTrue();
	}
	
	@Test
	public void condition_Equal_Result_NotEqual() {
		Validation validation  = Helper.DUMMY_VALIdATION(CompareStringCondition.EQUAL, DUMMY.VALUE1);
		val result = validation.validate(Helper.DUMMY_ITEM(DUMMY.VALUE2));
		assertThat(result).isFalse();
	}
	
	
	@Test
	public void condition_NotEqual_Result_NotEqual() {
		Validation validation  = Helper.DUMMY_VALIdATION(CompareStringCondition.NOT_EQUAL, DUMMY.VALUE1);
		val result = validation.validate(Helper.DUMMY_ITEM(DUMMY.VALUE2));
		assertThat(result).isTrue();
	}
	
	@Test
	public void condition_NotEqual_Result_Equal() {
		Validation validation  = Helper.DUMMY_VALIdATION(CompareStringCondition.NOT_EQUAL, DUMMY.VALUE2);
		val result = validation.validate(Helper.DUMMY_ITEM(DUMMY.VALUE2));
		assertThat(result).isFalse();
	}
}

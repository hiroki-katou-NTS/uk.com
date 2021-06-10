package nts.uk.ctx.exio.dom.input.validation.condition.system;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.DomainConstraint;
import nts.uk.ctx.exio.dom.input.validation.condition.system.helper.TestStringPrimitive;

public class ValidationPrimitiveValueTest {

	@Test
	public void success() {
		String fqn = TestStringPrimitive.class.getName();
		
		String lengthThree = "123";
		new DomainConstraint(CheckMethod.PRIMITIVE_VALUE, fqn).validate(lengthThree);
	}
	
	@Test
	public void error_overAnnotatoinLength() {
		String fqn = TestStringPrimitive.class.getName();
		
		String lengthFour = "1234";
		try {
			new DomainConstraint(CheckMethod.PRIMITIVE_VALUE, fqn).validate(lengthFour);
			//Exceptionが飛ぶことを想定しているため、
			//処理が正常終了したらテストは失敗
			assertThat(true).isFalse();
		}
		catch(RuntimeException e) {
			assertThat(e.getMessage()).isEqualTo("PrimitiveValueの検証　仮置きエクスセプションです。");
		}
	}

}

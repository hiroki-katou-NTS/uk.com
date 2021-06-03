package nts.uk.ctx.exio.dom.input.validation.classtype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.validation.condition.system.ValidationPrimitiveValue;

public class ValidationPrimitiveValueTest {

	@Test
	public void success() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.TestStringPrimitive";
		
		String lengthThree = "123";
		ValidationPrimitiveValue.run(fqn, lengthThree);
	}
	
	@Test
	public void error_overAnnotatoinLength() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.TestStringPrimitive";
		
		String lengthFour = "1234";
		try {
			ValidationPrimitiveValue.run(fqn, lengthFour);
			//Exceptionが飛ぶことを想定しているため、
			//処理が正常終了したらテストは失敗
			assertThat(true).isFalse();
		}
		catch(RuntimeException e) {
			assertThat(e.getMessage()).isEqualTo("PrimitiveValueの検証　仮置きエクスセプションです。");
		}
	}

}

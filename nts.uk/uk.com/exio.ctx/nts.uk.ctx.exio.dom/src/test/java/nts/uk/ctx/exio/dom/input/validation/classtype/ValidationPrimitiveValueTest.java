package nts.uk.ctx.exio.dom.input.validation.classtype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.validation.condition.system.ValidationPrimitiveValue;

public class ValidationPrimitiveValueTest {

	@Test
	public void success() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.StringTestPrimitive";
		
		String lengthThree = "123";
		ValidationPrimitiveValue.run(fqn, lengthThree);
	}
	
	@Test
	public void error_overAnnotatoinLength() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.StringTestPrimitive";
		
		String lengthFour = "1234";
		try {
			ValidationPrimitiveValue.run(fqn, lengthFour);
		}
		catch(RuntimeException e) {
			assertThat(e.getMessage()).isEqualTo("くすくす");
		}
	}

}

package nts.uk.ctx.exio.dom.input.validation.classtype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.validation.systemrange.ValidationEnum;

public class ValidationEnumTest {

	@Test
	public void success() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.TestEnum";
		
		int existValue = 1;
		ValidationEnum.run(fqn, existValue);
	}

	@Test
	public void error_notExistValue() {
		String fqn = "nts.uk.ctx.exio.dom.input.validation.classtype.helper.TestEnum";
		
		int notExistValue = 2;
		try {
			ValidationEnum.run(fqn, notExistValue);
			assertThat(true).isFalse();
		}
		catch(RuntimeException ex) {
			assertThat(ex.getMessage()).isEqualTo("Enumの検証　仮置きエクスセプションです。");
		}
	}
}

package nts.uk.ctx.exio.dom.input.validation.condition.system;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.DomainConstraint;
import nts.uk.ctx.exio.dom.input.validation.condition.system.helper.TestEnum;

public class ValidationEnumTest {

	@Test
	public void success() {
		String fqn = TestEnum.class.getName();
		
		int existValue = 1;
		
		new DomainConstraint(CheckMethod.ENUM, fqn).validate(existValue);
	}

	@Test
	public void error_notExistValue() {
		String fqn = TestEnum.class.getName();
		
		int notExistValue = 2;
		try {
			new DomainConstraint(CheckMethod.ENUM, fqn).validate(notExistValue);
			assertThat(true).isFalse();
		}
		catch(RuntimeException ex) {
			assertThat(ex.getMessage()).isEqualTo("Enumの検証　仮置きエクスセプションです。");
		}
	}
}

package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.Optional;

import lombok.SneakyThrows;
import lombok.Value;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;

/**
 *  値のドメイン制約
 */
@Value
public class DomainConstraint {
	
	private CheckMethod checkMethod;
	private String fqn;

	public Optional<ErrorMessage> validate(Object value) {
		return this.checkMethod.validate(getConstraintClass(), value);
	}
	
	@SneakyThrows
	public Class<?> getConstraintClass() {
		return Class.forName(fqn);
	}
}

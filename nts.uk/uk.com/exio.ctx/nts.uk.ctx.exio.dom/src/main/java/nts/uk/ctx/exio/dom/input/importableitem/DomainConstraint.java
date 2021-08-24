package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;

/**
 *  値のドメイン制約
 */
@AllArgsConstructor
@Getter
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

package nts.uk.shr.sample.passwordhash;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
public class PasswordHashSource {

	private String passwordPlainText;
	private String salt;
}

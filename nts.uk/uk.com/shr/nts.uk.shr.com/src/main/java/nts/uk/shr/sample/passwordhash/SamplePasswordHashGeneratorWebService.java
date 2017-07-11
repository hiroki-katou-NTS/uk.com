package nts.uk.shr.sample.passwordhash;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.gul.security.hash.password.PasswordHash;

@Path("/sample/passwordhash")
@Produces("application/json")
public class SamplePasswordHashGeneratorWebService {

	@POST
	@Path("generate")
	public JavaTypeResult<String> generate(PasswordHashSource source) {
		String hash = PasswordHash.generate(source.getPasswordPlainText(), source.getSalt());
		return new JavaTypeResult<>(hash);
	}
}

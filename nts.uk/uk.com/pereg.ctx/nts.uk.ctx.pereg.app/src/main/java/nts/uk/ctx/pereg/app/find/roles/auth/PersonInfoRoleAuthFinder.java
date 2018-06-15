package nts.uk.ctx.pereg.app.find.roles.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

/**
 * The Class PersonInfoRoleAuthFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoRoleAuthFinder {

	public List<PersonInfoRoleAuthDto> getAllPersonInfoRoleAuth() {
		return new ArrayList<>();
	}

	public Optional<PersonInfoRoleAuthDto> getDetailPersonRoleAuth(String roleId) {
		return Optional.empty();
	}
}

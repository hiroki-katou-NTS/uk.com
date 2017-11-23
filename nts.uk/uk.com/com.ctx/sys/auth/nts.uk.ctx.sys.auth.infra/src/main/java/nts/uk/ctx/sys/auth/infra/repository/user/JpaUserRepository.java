package nts.uk.ctx.sys.auth.infra.repository.user;

import java.util.Optional;

import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

public class JpaUserRepository extends JpaEntity implements UserRepository {

	@Override
	public Optional<User> getByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getByAssociatedPersonId(String associatedPersonId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getByUserID(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}

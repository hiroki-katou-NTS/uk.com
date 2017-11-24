package nts.uk.ctx.sys.auth.infra.repository.user;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
@Stateless
public class JpaUserRepositoryAuth extends JpaRepository implements UserRepository {

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

	@Override
	public List<User> getByListUser(List<String> userID) {
		// TODO Auto-generated method stub
		return null;
	}




}

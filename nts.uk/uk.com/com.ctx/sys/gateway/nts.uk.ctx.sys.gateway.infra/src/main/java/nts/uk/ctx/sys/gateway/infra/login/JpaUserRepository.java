/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;
@Stateless
public class JpaUserRepository extends JpaRepository implements UserRepository{

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

}

package nts.uk.ctx.sys.auth.pubimp.spr;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub;
import nts.uk.pub.spr.login.UserSprService;
import nts.uk.pub.spr.login.output.UserSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class UserSprImpl implements UserSprService {

	@Inject
	private UserRepository userRepository;
	
	@Inject
	private RoleFromUserIdPub roleFromUserIdPub;
	
	@Override
	public Optional<UserSpr> getUserSpr(String personID) {
		return userRepository.getByAssociatedPersonId(personID)
		.map(x -> new UserSpr(
				x.getUserID(), 
				x.getLoginID().v(), 
				x.getUserName().v(), 
				x.getAssociatedPersonID(), 
				x.getMailAddress().v(), 
				x.getPassword().v()));
	}

	@Override
	public String getRoleFromUserId(String userId, int roleType, GeneralDate baseDate) {
		return roleFromUserIdPub.getRoleFromUserId(userId, roleType, baseDate);
	}

}

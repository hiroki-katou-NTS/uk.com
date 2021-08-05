package nts.uk.ctx.at.auth.ac.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.adapter.login.IGetInfoForLogin;
import nts.uk.ctx.sys.auth.pub.spr.UserSprPub;

@Stateless
public class GetInfoForLoginAc implements IGetInfoForLogin{

	@Inject
	private UserSprPub usertLogin;
	
	@Override
	public Optional<String> getUserIdFromLoginId(String perId) {
		return usertLogin.getUserSpr(perId).map(x -> x.getUserID());
	}

}

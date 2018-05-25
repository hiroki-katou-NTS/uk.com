package nts.uk.ctx.sys.auth.infra.repository.password.changelog;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.auth.dom.password.changelog.LoginId;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogGetMemento;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;

public class JpaPasswordChangeLogGetMemento implements PasswordChangeLogGetMemento{

	
	@Override
	public LoginId getLoginId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDateTime getModifiedDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashPassword getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}

package nts.uk.ctx.sys.gateway.app.command.login.password.userpassword;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.ChangeLoginPasswordOfUser.Require;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ChangeLoginPasswordCommandRequire {
	
	public Require create() {
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	@RequiredArgsConstructor
	public class RequireImpl implements Require {
		
		@Override
		public PasswordPolicy getPasswordPolicy() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
		
		@Override
		public Optional<LoginPasswordOfUser> getLoginPasswordOfUser(String userId) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
		
		@Override
		public void save(LoginPasswordOfUser password) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
}

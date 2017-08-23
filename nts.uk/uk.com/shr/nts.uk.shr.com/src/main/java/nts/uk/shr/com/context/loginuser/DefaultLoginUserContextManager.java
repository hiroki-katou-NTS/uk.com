package nts.uk.shr.com.context.loginuser;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.scoped.session.SessionContextProvider;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DefaultLoginUserContextManager implements LoginUserContextManager {

	@Override
	public void loggedInAsEmployee(
			String userId,
			String loginCode,
			String personId,
			String contractCode,
			String companyId,
			String companyCode,
			String employeeId,
			String employeeCode) {
		
		val context = new DefaultLoginUserContext(userId, true);
		context.setLoginCode(loginCode);
		context.setPersonId(personId);
		context.setContractCode(contractCode);
		context.setCompanyId(companyId);
		context.setCompanyCode(companyCode);
		context.setEmployeeId(employeeId);
		context.setEmployeeCode(employeeCode);
		
		SessionContextProvider.get().put(LoginUserContext.KEY_SESSION_SCOPED, context);
	}

}

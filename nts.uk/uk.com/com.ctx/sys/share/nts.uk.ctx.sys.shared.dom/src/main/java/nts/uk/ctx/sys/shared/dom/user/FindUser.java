package nts.uk.ctx.sys.shared.dom.user;

import java.util.Optional;

import lombok.val;

public class FindUser {
	
	public static Optional<User> byEmployeeCode(Require require, String companyId, String employeeCode){
		
		val personIdOpt = require.getPersonalId(companyId, employeeCode);
		
		if (!personIdOpt.isPresent()) {
			return Optional.empty();
		}
		
		return require.getUser(personIdOpt.get());
	}
	
	public static interface Require{
		Optional<String> getPersonalId(String companyId, String employeeCode);
		
		Optional<User> getUser(String personalId);
	}
}

package nts.uk.ctx.sys.auth.dom.registration.user.service;

import nts.arc.time.GeneralDate;

public interface RegistrationUserService {
	
	boolean checkSystemAdmin(String userID, GeneralDate validityPeriod);
	CheckBeforeChangePassOutput checkPasswordPolicy(String userId, String pass, String contractCode);

}

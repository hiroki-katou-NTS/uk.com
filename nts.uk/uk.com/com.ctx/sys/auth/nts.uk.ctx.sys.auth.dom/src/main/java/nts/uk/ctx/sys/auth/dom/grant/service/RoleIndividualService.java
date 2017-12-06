package nts.uk.ctx.sys.auth.dom.grant.service;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RoleIndividualService {
	
	boolean checkSysAdmin(String userID, DatePeriod validPeriod);

}

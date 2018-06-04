package nts.uk.ctx.sys.log.dom.logbasicinfo;

import java.util.List;

import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/**
 * 
 * @author HungTT
 *
 */

public interface LogBasicInfoRepository {

	List<LogBasicInformation> getAllLogBasicInfo(String companyId, String loginEmployeeId);
	
}

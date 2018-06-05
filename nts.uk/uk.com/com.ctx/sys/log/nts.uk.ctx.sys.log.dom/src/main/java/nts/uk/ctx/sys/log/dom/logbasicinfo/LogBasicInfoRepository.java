package nts.uk.ctx.sys.log.dom.logbasicinfo;

import java.util.Optional;

import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/**
 * 
 * @author HungTT
 *
 */

public interface LogBasicInfoRepository {

	Optional<LogBasicInformation> getLogBasicInfo(String companyId, String operationId);
	
}

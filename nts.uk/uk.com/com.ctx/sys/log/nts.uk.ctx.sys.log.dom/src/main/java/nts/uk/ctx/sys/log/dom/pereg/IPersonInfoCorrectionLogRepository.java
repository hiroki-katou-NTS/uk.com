package nts.uk.ctx.sys.log.dom.pereg;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author vuongnv
 * 
 */
public interface IPersonInfoCorrectionLogRepository {
	Optional<PersonInfoCorrectionLog> getPeregLog(String operationId);

	List<PersonInfoCorrectionLog> findByTargetAndDate(String operationId, List<String> listEmployeeId,
			DatePeriod period);
}

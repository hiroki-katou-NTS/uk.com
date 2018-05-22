package nts.uk.ctx.sys.log.dom.datacorrectionlog;

import java.time.Year;
import java.util.List;

import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

public interface DataCorrectionLogRepository {

	List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId, DatePeriod datePeriod);

	List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId, YearMonthPeriod ymPeriod);

	List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId, Year yearStart, Year yearEnd);

}

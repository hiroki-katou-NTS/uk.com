package nts.uk.ctx.sys.log.infra.repository.datacorrectionlog;

import java.time.Year;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaDataCorrectionLogRepository extends JpaRepository implements DataCorrectionLogRepository {

	@Override
	public List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId,
			DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId,
			YearMonthPeriod ymPeriod) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<DataCorrectionLog> getAllLogData(String operationId, List<String> listEmployeeId, Year yearStart,
			Year yearEnd) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

}

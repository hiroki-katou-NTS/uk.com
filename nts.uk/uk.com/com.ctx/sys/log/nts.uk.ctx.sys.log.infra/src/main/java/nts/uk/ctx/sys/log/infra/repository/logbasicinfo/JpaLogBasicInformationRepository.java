package nts.uk.ctx.sys.log.infra.repository.logbasicinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.ctx.sys.log.infra.entity.logbasicinfo.SrcdtLogBasicInfo;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 *
 * @author HungTT
 *
 */

@Stateless
public class JpaLogBasicInformationRepository extends JpaRepository implements LogBasicInfoRepository, LogBasicInformationWriter {

	@Override
	public Optional<LogBasicInformation> getLogBasicInfo(String companyId, String operationId) {
		String query = "SELECT a FROM SrcdtLogBasicInfo a WHERE a.companyId = :companyId AND a.operationId = :operationId";
		return this.queryProxy().query(query, SrcdtLogBasicInfo.class).setParameter("companyId", companyId)
				.setParameter("operationId", operationId).getSingle(item -> item.toDomain());
	}

	@Override
	public List<LogBasicInformation> findByOperatorsAndDate(String companyId, List<String> listEmployeeId,
			GeneralDateTime start, GeneralDateTime end) {
		/*GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);
		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);*/
		if (listEmployeeId == null || listEmployeeId.isEmpty()) {
			String query = "SELECT a FROM SrcdtLogBasicInfo a WHERE a.companyId = :companyId"
					+ " AND a.modifiedDateTime BETWEEN :startPeriod AND :endPeriod ORDER BY a.modifiedDateTime DESC";
			return this.queryProxy().query(query, SrcdtLogBasicInfo.class).setParameter("companyId", companyId)
					.setParameter("startPeriod", start)
					.setParameter("endPeriod", end).getList(i -> i.toDomain());
		} else {
			String query = "SELECT a FROM SrcdtLogBasicInfo a WHERE a.companyId = :companyId AND a.employeeId IN :employeeId "
					+ "AND a.modifiedDateTime BETWEEN :startPeriod AND :endPeriod ORDER BY a.modifiedDateTime DESC";
			return this.queryProxy().query(query, SrcdtLogBasicInfo.class).setParameter("companyId", companyId)
					.setParameter("employeeId", listEmployeeId).setParameter("startPeriod", start)
					.setParameter("endPeriod", end).getList(i -> i.toDomain());
		}
	}

	@Override
	public void save(LogBasicInformation basicInfo) {
		this.commandProxy().insert(SrcdtLogBasicInfo.fromDomain(basicInfo));
	}

}

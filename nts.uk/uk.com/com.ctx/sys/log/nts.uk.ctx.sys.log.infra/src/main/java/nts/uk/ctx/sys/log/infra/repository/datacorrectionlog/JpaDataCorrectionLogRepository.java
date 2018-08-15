package nts.uk.ctx.sys.log.infra.repository.datacorrectionlog;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.ctx.sys.log.infra.entity.datacorrectionlog.SrcdtDataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogWriter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaDataCorrectionLogRepository extends JpaRepository implements DataCorrectionLogRepository, DataCorrectionLogWriter {

	@Override
	public List<DataCorrectionLog> getAllLogData(TargetDataType targetDataType, List<String> listEmployeeId, DatePeriod datePeriod) {
		if (targetDataType == null)
			return Collections.emptyList();
		String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.targetDataType = :targetDataType AND a.employeeId IN :listEmpId AND a.ymdKey >= :startYmd AND a.ymdKey <= :endYmd";
		return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
				.setParameter("targetDataType", targetDataType.value)
				.setParameter("listEmpId", listEmployeeId)
				.setParameter("startYmd", datePeriod.start())
				.setParameter("endYmd", datePeriod.end()).getList(c -> c.toDomainToView());
	}

	@Override
	public List<DataCorrectionLog> getAllLogData(TargetDataType targetDataType, List<String> listEmployeeId, YearMonthPeriod ymPeriod) {
		if (targetDataType == null)
			return Collections.emptyList();
		String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.targetDataType = :targetDataType AND a.employeeId IN :listEmpId "
				+ "AND a.ymKey >= :startYm AND a.ymKey <= :endYm";
		return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
				.setParameter("targetDataType", targetDataType.value)
				.setParameter("listEmpId", listEmployeeId)
				.setParameter("startYm", ymPeriod.start().v())
				.setParameter("endYm", ymPeriod.end().v()).getList(c -> c.toDomainToView());
	}

	@Override
	public List<DataCorrectionLog> getAllLogData(TargetDataType targetDataType, List<String> listEmployeeId, Year yearStart, Year yearEnd) {
		if (targetDataType == null)
			return Collections.emptyList();
		String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.targetDataType = :targetDataType AND a.employeeId IN :listEmpId "
				+ "AND a.yKey >= :startY AND a.yKey <= :endY";
		return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
				.setParameter("targetDataType", targetDataType.value)
				.setParameter("listEmpId", listEmployeeId)
				.setParameter("startY", yearStart.getValue())
				.setParameter("endY", yearEnd.getValue()).getList(c -> c.toDomainToView());
	}

	@Override
	public List<DataCorrectionLog> findByTargetAndDate(String operationId, List<String> listEmployeeId,
			DatePeriod period) {
		if (listEmployeeId == null || listEmployeeId.isEmpty()) {
			if (period.start() == null) {
				String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.operationId = :operationId ORDER BY a.employeeId, a.ymdKey, a.ymKey, a.yKey";
				return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
						.setParameter("operationId", operationId).getList(c -> c.toDomainToView());
			} else {
				String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.operationId = :operationId AND a.ymdKey >= :startYmd AND a.ymdKey <= :endYmd ORDER BY a.employeeId, a.ymdKey, a.ymKey, a.yKey";
				return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
						.setParameter("operationId", operationId)
						.setParameter("startYmd", period.start())
						.setParameter("endYmd", period.end()).getList(c -> c.toDomainToView());
			}
		} else {
			if (period.start() == null) {
				String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.operationId = :operationId AND a.employeeId IN :listEmpId ORDER BY a.employeeId, a.ymdKey, a.ymKey, a.yKey";
				return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
						.setParameter("operationId", operationId)
						.setParameter("listEmpId", listEmployeeId)
						.getList(c -> c.toDomainToView());
			} else {
				String query = "SELECT a FROM SrcdtDataCorrectionLog a WHERE a.pk.operationId = :operationId AND a.employeeId IN :listEmpId AND a.ymdKey >= :startYmd AND a.ymdKey <= :endYmd ORDER BY a.employeeId, a.ymdKey, a.ymKey, a.yKey";
				return this.queryProxy().query(query, SrcdtDataCorrectionLog.class)
						.setParameter("operationId", operationId)
						.setParameter("listEmpId", listEmployeeId)
						.setParameter("startYmd", period.start())
						.setParameter("endYmd", period.end()).getList(c -> c.toDomainToView());
			}
		}
	}

	@Override
	public void save(List<DataCorrectionLog> dataCorrectionLog) {
		List<SrcdtDataCorrectionLog> entities = dataCorrectionLog.stream().map(x -> SrcdtDataCorrectionLog.fromDomain(x)).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

}

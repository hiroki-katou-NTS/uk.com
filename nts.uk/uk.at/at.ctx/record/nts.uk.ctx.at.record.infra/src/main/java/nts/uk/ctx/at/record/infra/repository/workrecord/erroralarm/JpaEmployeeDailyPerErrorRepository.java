package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcdtSyainDpErList;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmployeeDailyPerErrorRepository extends JpaRepository implements EmployeeDailyPerErrorRepository {

	private static final String FIND_ERROR_CODE;

	private static final String FIND_ERROR_CODE_BY_PERIOD;

	private static final String FIND_BY_PERIOD_ORDER_BY_YMD;

	private static final String REMOVE_DATA;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.processingDate = :processingDate ");
		builderString.append("AND a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.errorCode = :errorCode ");
		FIND_ERROR_CODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate >= :start ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate <= :end ");
		builderString.append("AND a.krcdtSyainDpErListPK.errorCode = :errorCode ");
		FIND_ERROR_CODE_BY_PERIOD = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate >= :start ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate <= :end ");
		builderString.append("ORDER BY a.krcdtSyainDpErListPK.processingDate ");
		FIND_BY_PERIOD_ORDER_BY_YMD = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate = :start ");
		REMOVE_DATA = builderString.toString();

	}

	@Override
	public void insert(EmployeeDailyPerError employeeDailyPerformanceError) {
		if (KrcdtSyainDpErList.toEntity(employeeDailyPerformanceError).size() > 1) {
			this.commandProxy().insert(KrcdtSyainDpErList.toEntity(employeeDailyPerformanceError).get(0));
		} else {
			KrcdtSyainDpErList.toEntity(employeeDailyPerformanceError).forEach(f -> this.commandProxy().insert(f));
		}
		this.getEntityManager().flush();
	}

	@Override
	public boolean checkExistErrorCode(String employeeID, GeneralDate processingDate, String errorCode) {
		return this.queryProxy().query(FIND_ERROR_CODE, long.class).setParameter("processingDate", processingDate)
				.setParameter("employeeId", employeeID).setParameter("errorCode", errorCode).getSingle().get() > 0;
	}

	@Override
	public boolean checkExistErrorCodeByPeriod(String employeeID, DatePeriod datePeriod, String errorCode) {
		return this.queryProxy().query(FIND_ERROR_CODE_BY_PERIOD, long.class).setParameter("employeeId", employeeID)
				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
				.setParameter("errorCode", errorCode).getSingle().get() > 0;
	}

	@Override
	public EmployeeDailyPerError find(String employeeID, GeneralDate processingDate) {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate = :ymd ");
		List<KrcdtSyainDpErList> result = this.queryProxy().query(builderString.toString(), KrcdtSyainDpErList.class)
				.setParameter("employeeId", employeeID).setParameter("ymd", processingDate).getList();
		if (!result.isEmpty()) {
			return toDomain(result);
		}

		return null;
	}

	public EmployeeDailyPerError toDomain(List<KrcdtSyainDpErList> result) {
		return new EmployeeDailyPerError(result.get(0).krcdtSyainDpErListPK.companyID,
				result.get(0).krcdtSyainDpErListPK.employeeId, result.get(0).krcdtSyainDpErListPK.processingDate,
				new ErrorAlarmWorkRecordCode(result.get(0).krcdtSyainDpErListPK.errorCode),
				result.stream().map(c -> c.attendanceItemId).collect(Collectors.toList()),
				result.get(0).errorCancelable);
	}

	@Override
	public void update(EmployeeDailyPerError employeeDailyPerformanceError) {
		this.commandProxy().updateAll(KrcdtSyainDpErList.toEntity(employeeDailyPerformanceError));
		this.getEntityManager().flush();
	}

	@Override
	public List<EmployeeDailyPerError> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD, KrcdtSyainDpErList.class)
				.setParameter("employeeId", employeeId).setParameter("start", datePeriod.start())
				.setParameter("end", datePeriod.end()).getList().stream()
				.collect(Collectors.groupingBy(
						c -> c.krcdtSyainDpErListPK.employeeId + c.krcdtSyainDpErListPK.processingDate.toString()))
				.entrySet().stream().map(c -> toDomain(c.getValue())).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDailyPerError> finds(List<String> employeeID, DatePeriod processingDate) {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId IN :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate <= :end ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate >= :start ");
		return this.queryProxy().query(builderString.toString(), KrcdtSyainDpErList.class)
				.setParameter("employeeId", employeeID).setParameter("end", processingDate.end())
				.setParameter("start", processingDate.start()).getList().stream()
				.collect(Collectors.groupingBy(
						c -> c.krcdtSyainDpErListPK.employeeId + c.krcdtSyainDpErListPK.processingDate.toString()))
				.entrySet().stream().map(c -> toDomain(c.getValue())).collect(Collectors.toList());
	}

	@Override
	public void removeParam(String sid, GeneralDate date) {
		this.getEntityManager().createQuery(REMOVE_DATA, KrcdtSyainDpErList.class).setParameter("employeeId", sid)
				.setParameter("start", date).executeUpdate();
	}

	@Override
	public List<EmployeeDailyPerError> findList(String companyID, String employeeID) {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.companyID = :companyId ");
		return this.queryProxy().query(builderString.toString(), KrcdtSyainDpErList.class)
				.setParameter("employeeId", employeeID).setParameter("companyId", companyID)
				.getList(x -> this.toDomain(x));
	}

	EmployeeDailyPerError toDomain(KrcdtSyainDpErList krcdtSyainDpErList) {
		List<Integer> lstAttendanceItemId = new ArrayList<Integer>();
		lstAttendanceItemId.add(krcdtSyainDpErList.attendanceItemId);
		return new EmployeeDailyPerError(krcdtSyainDpErList.krcdtSyainDpErListPK.companyID,
				krcdtSyainDpErList.krcdtSyainDpErListPK.employeeId,
				krcdtSyainDpErList.krcdtSyainDpErListPK.processingDate,
				new ErrorAlarmWorkRecordCode(krcdtSyainDpErList.krcdtSyainDpErListPK.errorCode), lstAttendanceItemId,
				krcdtSyainDpErList.errorCancelable.intValue());
	}

	@Override
	public List<EmployeeDailyPerError> findAll(String employeeID, GeneralDate processingDate) {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtSyainDpErList a ");
		builderString.append("WHERE a.krcdtSyainDpErListPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtSyainDpErListPK.processingDate = :ymd ");
		return this.queryProxy().query(builderString.toString(), KrcdtSyainDpErList.class)
				.setParameter("employeeId", employeeID).setParameter("ymd", processingDate).getList(x -> toDomain(x));

	}
	

}

package nts.uk.ctx.at.record.infra.repository.workinformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfo;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfoPK;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtWorkScheduleTime;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtWorkScheduleTimePK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class JpaWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String DEL_BY_KEY;
	
	private static final String FIND_BY_PERIOD_ORDER_BY_YMD;
	
	private static final String FIND_BY_PERIOD_ORDER_BY_YMD_DESC;
	
	private static final String FIND_BY_LIST_SID_AND_PERIOD;
	
	private static final String DEL_BY_KEY_ID;

	private static final String FIND_BY_ID = "SELECT a FROM KrcdtDaiPerWorkInfo a "
			+ " WHERE a.krcdtDaiPerWorkInfoPK.employeeId = :employeeId " + " AND a.krcdtDaiPerWorkInfoPK.ymd = :ymd ";

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiPerWorkInfo a ");
		builderString.append("WHERE a.krcdtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiPerWorkInfoPK.ymd = :ymd ");
		DEL_BY_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtWorkScheduleTime a ");
		builderString.append("WHERE a.krcdtWorkScheduleTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtWorkScheduleTimePK.ymd = :ymd ");
		DEL_BY_KEY_ID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiPerWorkInfo a ");
		builderString.append("WHERE a.krcdtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiPerWorkInfoPK.ymd >= :startDate ");
		builderString.append("AND a.krcdtDaiPerWorkInfoPK.ymd <= :endDate ");
		builderString.append("ORDER BY a.krcdtDaiPerWorkInfoPK.ymd ");
		FIND_BY_PERIOD_ORDER_BY_YMD = builderString.toString();
		builderString.append(" DESC");
		FIND_BY_PERIOD_ORDER_BY_YMD_DESC = builderString.toString();
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiPerWorkInfo a ");
		builderString.append("WHERE a.krcdtDaiPerWorkInfoPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiPerWorkInfoPK.ymd >= :startDate ");
		builderString.append("AND a.krcdtDaiPerWorkInfoPK.ymd <= :endDate ");
		FIND_BY_LIST_SID_AND_PERIOD = builderString.toString();
	}

	@Override
	public Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_ID, KrcdtDaiPerWorkInfo.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(c -> c.toDomain());
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DEL_BY_KEY_ID).setParameter("employeeId", employeeId).setParameter("ymd", ymd)
		.executeUpdate();
		
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("employeeId", employeeId).setParameter("ymd", ymd)
				.executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD, KrcdtDaiPerWorkInfo.class)
				.setParameter("employeeId", employeeId).setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end()).getList(f -> f.toDomain());
	}
	
	@Override
	public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmdDesc(String employeeId, DatePeriod datePeriod) {
		return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD_DESC, KrcdtDaiPerWorkInfo.class)
				.setParameter("employeeId", employeeId).setParameter("startDate", datePeriod.start())
				.setParameter("endDate", datePeriod.end()).getList(f -> f.toDomain());
	}
	
	@Override
	public List<WorkInfoOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, DatePeriod ymds) {
		List<WorkInfoOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT af FROM KrcdtDaiPerWorkInfo af ");
		query.append("WHERE af.krcdtDaiPerWorkInfoPK.employeeId IN :employeeId ");
		query.append("AND af.krcdtDaiPerWorkInfoPK.ymd <= :end AND af.krcdtDaiPerWorkInfoPK.ymd >= :start");
		TypedQueryWrapper<KrcdtDaiPerWorkInfo> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiPerWorkInfo.class);
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
								.setParameter("start", ymds.start())
								.setParameter("end", ymds.end()).getList(af -> af.toDomain()));
		});
		return result;
	}

	@Override
	public void updateByKey(WorkInfoOfDailyPerformance domain) {
		Optional<KrcdtDaiPerWorkInfo> dataOpt = this.queryProxy().query(FIND_BY_ID, KrcdtDaiPerWorkInfo.class).setParameter("employeeId", domain.getEmployeeId())
				.setParameter("ymd", domain.getYmd()).getSingle();
		KrcdtDaiPerWorkInfo data = dataOpt.isPresent() ? dataOpt.get() : new KrcdtDaiPerWorkInfo(new KrcdtDaiPerWorkInfoPK(domain.getEmployeeId(), domain.getYmd()));
		if(domain != null){
			if(domain.getRecordInfo() != null){
				data.recordWorkWorktimeCode = domain.getRecordInfo().getWorkTimeCode() == null 
						? null : domain.getRecordInfo().getWorkTimeCode().v();
				data.recordWorkWorktypeCode = domain.getRecordInfo().getWorkTypeCode().v();
			}
			if(domain.getScheduleInfo() != null){
				data.scheduleWorkWorktimeCode = domain.getScheduleInfo().getWorkTimeCode() == null 
						? null : domain.getScheduleInfo().getWorkTimeCode().v();
				data.scheduleWorkWorktypeCode = domain.getScheduleInfo().getWorkTypeCode().v();
			}
			data.calculationState = domain.getCalculationState().value;
			data.backStraightAttribute = domain.getBackStraightAtr().value;
			data.goStraightAttribute = domain.getGoStraightAtr().value;
			data.dayOfWeek = domain.getDayOfWeek().value;
			if(data.scheduleTimes != null && !data.scheduleTimes.isEmpty()){
				this.commandProxy().removeAll(data.scheduleTimes);
				this.getEntityManager().flush();
			}
			data.scheduleTimes = domain.getScheduleTimeSheets().stream().map(c -> 
								new KrcdtWorkScheduleTime(
										new KrcdtWorkScheduleTimePK(
												domain.getEmployeeId(), domain.getYmd(), c.getWorkNo().v()),
												c.getAttendance().valueAsMinutes(), c.getLeaveWork().valueAsMinutes()))
										.collect(Collectors.toList());
			this.commandProxy().update(data);
			this.commandProxy().updateAll(data.scheduleTimes);
		}
	}

	@Override
	public void insert(WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		this.commandProxy().insert(KrcdtDaiPerWorkInfo.toEntity(workInfoOfDailyPerformance));
	}

	@Override
	public void updateByKeyFlush(WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		this.updateByKey(workInfoOfDailyPerformance);
		this.getEntityManager().flush();
		
	}

	@Override
	public List<WorkInfoOfDailyPerformance> finds(Map<String, GeneralDate> param) {
		List<WorkInfoOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT af FROM KrcdtDaiPerWorkInfo af ");
		query.append("WHERE af.krcdtDaiPerWorkInfoPK.employeeId IN :employeeId ");
		query.append("AND af.krcdtDaiPerWorkInfoPK.ymd IN :date");
		TypedQueryWrapper<KrcdtDaiPerWorkInfo> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiPerWorkInfo.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
								.setParameter("date", new HashSet<>(p.values()))
								.getList().stream()
								.filter(c -> c.krcdtDaiPerWorkInfoPK.ymd.equals(p.get(c.krcdtDaiPerWorkInfoPK.employeeId)))
								.map(af -> af.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}

}
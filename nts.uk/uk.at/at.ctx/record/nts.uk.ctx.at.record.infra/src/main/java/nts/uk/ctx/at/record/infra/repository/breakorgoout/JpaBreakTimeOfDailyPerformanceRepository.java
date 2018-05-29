package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDaiBreakTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaBreakTimeOfDailyPerformanceRepository extends JpaRepository
		implements BreakTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String DEL_BY_LIST_KEY;

	private static final String SELECT_BY_EMPLOYEE_AND_DATE;

	private static final String FIND;

	private static final String REMOVE_BY_BREAKTYPE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd = :ymd ");
		SELECT_BY_EMPLOYEE_AND_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiBreakTimePK.breakType = :breakType ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiBreakTimePK.breakType = :breakType ");
		REMOVE_BY_BREAKTYPE = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public List<BreakTimeOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		List<KrcdtDaiBreakTime> krcdtDaiBreakTimes = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_AND_DATE, KrcdtDaiBreakTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getList();

		if (krcdtDaiBreakTimes == null || krcdtDaiBreakTimes.isEmpty()) {
			return new ArrayList<>();
		}

		return group(krcdtDaiBreakTimes);
	}

	private List<BreakTimeOfDailyPerformance> group(List<KrcdtDaiBreakTime> krcdtDaiBreakTimes) {
		return krcdtDaiBreakTimes.stream().collect(Collectors.groupingBy(item -> item.krcdtDaiBreakTimePK.breakType))
				.entrySet().stream().map(c -> toDtomain(c.getKey(), c.getValue())).collect(Collectors.toList());
	}

	private BreakTimeOfDailyPerformance toDtomain(Integer type, List<KrcdtDaiBreakTime> value) {
		return new BreakTimeOfDailyPerformance(value.get(0).krcdtDaiBreakTimePK.employeeId,
				EnumAdaptor.valueOf(type, BreakType.class),
				value.stream()
						.map(item -> new BreakTimeSheet(new BreakFrameNo(item.krcdtDaiBreakTimePK.breakFrameNo),
								toTimeWithDayAttr(item.startStampTime), toTimeWithDayAttr(item.endStampTime),
								new AttendanceTime(0)))
						.collect(Collectors.toList()),
				value.get(0).krcdtDaiBreakTimePK.ymd);
	}

	private TimeWithDayAttr toTimeWithDayAttr(Integer time) {
		return time == null ? null : new TimeWithDayAttr(time);
	}

	@Override
	public void insert(BreakTimeOfDailyPerformance breakTimes) {
		commandProxy().insertAll(KrcdtDaiBreakTime.toEntity(breakTimes));
		this.getEntityManager().flush();
	}

	@Override
	public void insert(List<BreakTimeOfDailyPerformance> breakTimes) {
		commandProxy().insertAll(breakTimes.stream().map(c -> KrcdtDaiBreakTime.toEntity(c)).flatMap(List::stream)
				.collect(Collectors.toList()));
		this.getEntityManager().flush();
	}

	@Override
	public void update(BreakTimeOfDailyPerformance breakTimes) {
		if(breakTimes == null){ return;}
		List<KrcdtDaiBreakTime> all = KrcdtDaiBreakTime.toEntity(breakTimes);
		if (!all.isEmpty()) {
			List<KrcdtDaiBreakTime> krcdtDaiBreakTimes = this.queryProxy()
					.query(SELECT_BY_EMPLOYEE_AND_DATE, KrcdtDaiBreakTime.class).setParameter("employeeId", breakTimes.getEmployeeId())
					.setParameter("ymd", breakTimes.getYmd()).getList();
			List<KrcdtDaiBreakTime> toUpdate = all.stream()
					.filter(c -> c.endStampTime != null && c.startStampTime != null).collect(Collectors.toList());
			List<KrcdtDaiBreakTime> toRemove = krcdtDaiBreakTimes.stream()
					.filter(c -> !toUpdate.stream().filter(tu -> tu.krcdtDaiBreakTimePK.breakFrameNo == c.krcdtDaiBreakTimePK.breakFrameNo
																&& tu.krcdtDaiBreakTimePK.breakType == c.krcdtDaiBreakTimePK.breakType)
										.findFirst().isPresent())
					.collect(Collectors.toList());
			
			toRemove.stream().forEach(c -> {
				commandProxy().remove(c);
			});
			commandProxy().updateAll(toUpdate);
		} else {
			this.delete(breakTimes.getEmployeeId(), breakTimes.getYmd());
		}
		this.getEntityManager().flush();
	}

	@Override
	public void update(List<BreakTimeOfDailyPerformance> breakTimes) {
		if(breakTimes.isEmpty()) {
			return;
		}
		List<KrcdtDaiBreakTime> all = breakTimes.stream().map(c -> KrcdtDaiBreakTime.toEntity(c)).flatMap(List::stream)
				.collect(Collectors.toList());
		if (!all.isEmpty()) {
			List<KrcdtDaiBreakTime> krcdtDaiBreakTimes = this.queryProxy()
					.query(SELECT_BY_EMPLOYEE_AND_DATE, KrcdtDaiBreakTime.class).setParameter("employeeId", breakTimes.get(0).getEmployeeId())
					.setParameter("ymd", breakTimes.get(0).getYmd()).getList();
			List<KrcdtDaiBreakTime> toUpdate = all.stream()
					.filter(c -> c.endStampTime != null && c.startStampTime != null).collect(Collectors.toList());
			List<KrcdtDaiBreakTime> toRemove = krcdtDaiBreakTimes.stream()
					.filter(c -> !toUpdate.stream().filter(tu -> tu.krcdtDaiBreakTimePK.breakFrameNo == c.krcdtDaiBreakTimePK.breakFrameNo
																&& tu.krcdtDaiBreakTimePK.breakType == c.krcdtDaiBreakTimePK.breakType)
										.findFirst().isPresent())
					.collect(Collectors.toList());
			
			toRemove.stream().forEach(c -> {
				commandProxy().remove(c);
			});
			// commandProxy().removeAll(toRemove);
			commandProxy().updateAll(toUpdate);
		} else {
			this.delete(breakTimes.get(0).getEmployeeId(), breakTimes.get(0).getYmd());
		}
		// commandProxy().updateAll(breakTimes.stream().map(c ->
		// KrcdtDaiBreakTime.toEntity(c)).flatMap(List::stream)
		// .collect(Collectors.toList()));
		this.getEntityManager().flush();
	}

	@Override
	public List<BreakTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<BreakTimeOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiBreakTime a ");
		query.append("WHERE a.krcdtDaiBreakTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiBreakTimePK.ymd <= :end AND a.krcdtDaiBreakTimePK.ymd >= :start ");
		TypedQueryWrapper<KrcdtDaiBreakTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiBreakTime.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
					.setParameter("end", ymd.end())
					.setParameter("start", ymd.start()).getList().stream()
					.collect(Collectors.groupingBy(c -> c.krcdtDaiBreakTimePK.employeeId + c.krcdtDaiBreakTimePK.ymd.toString()))
					.entrySet().stream().map(c -> group(c.getValue())).flatMap(List::stream).collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public Optional<BreakTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd, int breakType) {

		List<KrcdtDaiBreakTime> krcdtDaiBreakTimes = this.queryProxy().query(FIND, KrcdtDaiBreakTime.class)
				.setParameter("employeeId", employeeId).setParameter("ymd", ymd).setParameter("breakType", breakType)
				.getList();
		if (krcdtDaiBreakTimes == null || krcdtDaiBreakTimes.isEmpty()) {
			return Optional.empty();
		}

		return Optional.ofNullable(group(krcdtDaiBreakTimes).get(0));
	}

	@Override
	public void deleteByBreakType(String employeeId, GeneralDate ymd, int breakType) {
		this.getEntityManager().createQuery(REMOVE_BY_BREAKTYPE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("breakType", breakType).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public List<BreakTimeOfDailyPerformance> finds(Map<String, GeneralDate> param) {
		List<BreakTimeOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiBreakTime a ");
		query.append("WHERE a.krcdtDaiBreakTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiBreakTimePK.ymd IN :date");
		TypedQueryWrapper<KrcdtDaiBreakTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiBreakTime.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", new HashSet<>(p.values()))
					.getList().stream()
					.filter(c -> c.krcdtDaiBreakTimePK.ymd.equals(p.get(c.krcdtDaiBreakTimePK.employeeId)))
					.collect(Collectors.groupingBy(c -> c.krcdtDaiBreakTimePK.employeeId + c.krcdtDaiBreakTimePK.ymd.toString()))
					.entrySet().stream().map(c -> group(c.getValue())).flatMap(List::stream).collect(Collectors.toList()));
		});
		return result;
	}

}

package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
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
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
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
		BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = new BreakTimeOfDailyPerformance(
				value.get(0).krcdtDaiBreakTimePK.employeeId, EnumAdaptor.valueOf(type, BreakType.class),
				value.stream().map(item -> {
					WorkStamp startActualStamp = new WorkStamp(new TimeWithDayAttr(item.startStampRoundingTimeDay),
							new TimeWithDayAttr(item.startStampTime), new WorkLocationCD(item.startStampPlaceCode),
							EnumAdaptor.valueOf(item.startStampSourceInfo, StampSourceInfo.class));

					WorkStamp endActualStamp = new WorkStamp(new TimeWithDayAttr(item.endStampRoundingTimeDay),
							new TimeWithDayAttr(item.endStampTime), new WorkLocationCD(item.endStampPlaceCode),
							EnumAdaptor.valueOf(item.endStampSourceInfo, StampSourceInfo.class));

					return new BreakTimeSheet(new BreakFrameNo(item.krcdtDaiBreakTimePK.breakFrameNo), startActualStamp,
							endActualStamp, new AttendanceTime(0));
				}).collect(Collectors.toList()), value.get(0).krcdtDaiBreakTimePK.ymd);
		return breakTimeOfDailyPerformance;
	}

	@Override
	public void insert(BreakTimeOfDailyPerformance breakTimes) {
		commandProxy().insertAll(KrcdtDaiBreakTime.toEntity(breakTimes));
	}

	@Override
	public void insert(List<BreakTimeOfDailyPerformance> breakTimes) {
		commandProxy().insertAll(breakTimes.stream().map(c -> KrcdtDaiBreakTime.toEntity(c)).flatMap(List::stream)
				.collect(Collectors.toList()));
	}

	@Override
	public void update(BreakTimeOfDailyPerformance breakTimes) {
		commandProxy().updateAll(KrcdtDaiBreakTime.toEntity(breakTimes));
	}

	@Override
	public void update(List<BreakTimeOfDailyPerformance> breakTimes) {
		commandProxy().updateAll(breakTimes.stream().map(c -> KrcdtDaiBreakTime.toEntity(c)).flatMap(List::stream)
				.collect(Collectors.toList()));
	}

	@Override
	public List<BreakTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a ");
		query.append("FROM KrcdtDaiBreakTime a ");
		query.append("WHERE a.krcdtDaiBreakTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiBreakTimePK.ymd <= :end AND a.krcdtDaiBreakTimePK.ymd >= :start ");
		return queryProxy().query(query.toString(), KrcdtDaiBreakTime.class).setParameter("employeeId", employeeId)
				.setParameter("end", ymd.end()).setParameter("start", ymd.start()).getList().stream()
				.collect(Collectors.groupingBy(c -> c.krcdtDaiBreakTimePK.employeeId + c.krcdtDaiBreakTimePK.ymd.toString()))
				.entrySet().stream().map(c -> group(c.getValue())).flatMap(List::stream).collect(Collectors.toList());
	}

}

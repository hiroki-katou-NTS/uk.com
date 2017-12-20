package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaBreakTimeOfDailyPerformanceRepository extends JpaRepository
		implements BreakTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String DEL_BY_LIST_KEY;

	private static final String SELECT_BY_EMPLOYEE_AND_DATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE a.krcdtDaiBreakTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiBreakTime a ");
		builderString.append("WHERE WHERE a.krcdtDaiBreakTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiBreakTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

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
		List<BreakTimeOfDailyPerformance> breakTimeOfDailyPerformances = new ArrayList<>();
		List<KrcdtDaiBreakTime> krcdtDaiBreakTimes = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_AND_DATE, KrcdtDaiBreakTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getList();

		if (krcdtDaiBreakTimes == null || krcdtDaiBreakTimes.isEmpty()) {
			return breakTimeOfDailyPerformances;
		}

		Map<BigDecimal, List<KrcdtDaiBreakTime>> resultMap = krcdtDaiBreakTimes.stream()
				.collect(Collectors.groupingBy(item -> item.krcdtDaiBreakTimePK.breakType));

		resultMap.forEach((key, value) -> {
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = new BreakTimeOfDailyPerformance(employeeId,
					EnumAdaptor.valueOf(key.intValue(), BreakType.class), value.stream().map(item -> {
						WorkStamp startActualStamp = new WorkStamp(
								new TimeWithDayAttr(item.startStampRoundingTimeDay.intValue()),
								new TimeWithDayAttr(item.startStampTime.intValue()),
								new WorkLocationCD(item.startStampPlaceCode),
								EnumAdaptor.valueOf(item.startStampSourceInfo.intValue(), StampSourceInfo.class));

						WorkStamp endActualStamp = new WorkStamp(
								new TimeWithDayAttr(item.endStampRoundingTimeDay.intValue()),
								new TimeWithDayAttr(item.endStampTime.intValue()),
								new WorkLocationCD(item.endStampPlaceCode),
								EnumAdaptor.valueOf(item.endStampSourceInfo.intValue(), StampSourceInfo.class));

						return new BreakTimeSheet(new BreakFrameNo(item.krcdtDaiBreakTimePK.breakFrameNo),
								startActualStamp, endActualStamp);
					}).collect(Collectors.toList()), ymd);
			breakTimeOfDailyPerformances.add(breakTimeOfDailyPerformance);
		});

		return breakTimeOfDailyPerformances;
	}

}

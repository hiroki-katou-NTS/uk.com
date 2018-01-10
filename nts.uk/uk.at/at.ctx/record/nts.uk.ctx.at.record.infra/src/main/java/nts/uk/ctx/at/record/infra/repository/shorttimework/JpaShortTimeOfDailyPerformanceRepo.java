package nts.uk.ctx.at.record.infra.repository.shorttimework;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDaiShortWorkTime;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDaiShortWorkTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaShortTimeOfDailyPerformanceRepo extends JpaRepository implements ShortTimeOfDailyPerformanceRepository {

	@Override
	public Optional<ShortTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		List<ShortWorkingTimeSheet> shortTimeSheets = findEntities(employeeId, ymd).getList(
				c -> new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(c.krcdtDaiShortWorkTimePK.shortWorkTimeFrameNo),
						EnumAdaptor.valueOf(c.childCareAtr, ChildCareAttribute.class), new TimeWithDayAttr(c.startTime),
						new TimeWithDayAttr(c.endTime), new AttendanceTime(c.deductionTime),
						new AttendanceTime(c.time)));
		if (!shortTimeSheets.isEmpty()) {
			return Optional.of(new ShortTimeOfDailyPerformance(employeeId, shortTimeSheets, ymd));
		}
		return Optional.empty();
	}

	@Override
	public void updateByKey(ShortTimeOfDailyPerformance shortWork) {
		List<KrcdtDaiShortWorkTime> entities = findEntities(shortWork.getEmployeeId(), shortWork.getYmd()).getList();
		shortWork.getShortWorkingTimeSheets().stream().forEach(c -> {
			KrcdtDaiShortWorkTime current = entities.stream()
					.filter(x -> x.krcdtDaiShortWorkTimePK.shortWorkTimeFrameNo == c.getShortWorkTimeFrameNo().v())
					.findFirst().orElse(null);
			if(current != null){
				current.childCareAtr = c.getChildCareAttr().value;
				current.deductionTime = c.getDeductionTime() == null ? null : c.getDeductionTime().valueAsMinutes();
				current.endTime = c.getEndTime() == null ? null : c.getEndTime().valueAsMinutes();
				current.startTime = c.getStartTime() == null ? null : c.getStartTime().valueAsMinutes();
				current.time = c.getShortTime() == null ? null : c.getShortTime().valueAsMinutes();
			} else {
				entities.add(newEntities(shortWork.getEmployeeId(), shortWork.getYmd(), c));
			}
		});
		commandProxy().updateAll(entities);
	}

	@Override
	public void insert(ShortTimeOfDailyPerformance shortWork) {
		List<KrcdtDaiShortWorkTime> entities = shortWork.getShortWorkingTimeSheets().stream()
				.map(c -> newEntities(shortWork.getEmployeeId(), shortWork.getYmd(), c)).collect(Collectors.toList());
		commandProxy().insertAll(entities);
	}

	private KrcdtDaiShortWorkTime newEntities(String employeeId, GeneralDate ymd, ShortWorkingTimeSheet c) {
		return new KrcdtDaiShortWorkTime(new KrcdtDaiShortWorkTimePK(employeeId, ymd, c.getShortWorkTimeFrameNo().v()),
				c.getStartTime().valueAsMinutes(), c.getEndTime().valueAsMinutes(), c.getChildCareAttr().value,
				c.getShortTime().valueAsMinutes(), c.getDeductionTime().valueAsMinutes());
	}

	private TypedQueryWrapper<KrcdtDaiShortWorkTime> findEntities(String employeeId, GeneralDate ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT s FROM KrcdtDaiShortWorkTime s");
		query.append(" WHERE s.krcdtDaiShortWorkTimePK.sid = :employeeId");
		query.append(" AND s.krcdtDaiShortWorkTimePK.ymd = :ymd");
		query.append(" ORDER BY s.krcdtDaiShortWorkTimePK.shortWorkTimeFrameNo");
		return queryProxy().query(query.toString(), KrcdtDaiShortWorkTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd);
	}

}

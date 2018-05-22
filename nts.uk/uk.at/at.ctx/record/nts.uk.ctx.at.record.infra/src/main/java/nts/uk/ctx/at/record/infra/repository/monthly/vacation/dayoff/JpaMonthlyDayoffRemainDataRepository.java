package nts.uk.ctx.at.record.infra.repository.monthly.vacation.dayoff;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeDayoffRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.dayoff.KrcdtMonDayoffRemain;
@Stateless
public class JpaMonthlyDayoffRemainDataRepository extends JpaRepository implements MonthlyDayoffRemainDataRepository{
	private String QUERY_BY_SID_YM_STATUS = "SELECT c FROM KrcdtMonDayoffRemain　c "
			+ " WHERE c.pk.sid = :employeeId"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status";
	@Override
	public List<MonthlyDayoffRemainData> getDayOffDataBySidYmStatus(String employeeId, YearMonth ym,
			ClosureStatus status) {
		
		return  this.queryProxy().query(QUERY_BY_SID_YM_STATUS, KrcdtMonDayoffRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ym", ym.v())
				.setParameter("closureStatus", status.value)
				.getList(c -> toDomain(c));
	}
	private MonthlyDayoffRemainData toDomain(KrcdtMonDayoffRemain c) {
		return new MonthlyDayoffRemainData(c.getPk().getSid(),
				YearMonth.of(c.getPk().getYm()),
				c.getPk().getClosureId(),
				c.getPk().getClosureDay(),
				c.getPk().getIsLastDay() == 1 ? true : false,
				EnumAdaptor.valueOf(c.getClosureStatus(), ClosureStatus.class),
				c.getStartDate(),
				c.getEndDate(),
				new DayOffDayAndTimes(new AttendanceDaysMonth(c.getOccurredDays()), Optional.of(new TimeDayoffRemain(c.getOccurredTimes()))),
				new DayOffDayAndTimes(new AttendanceDaysMonth(c.getUsedDays()), Optional.of(new TimeDayoffRemain(c.getUsedTimes()))),
				new DayOffRemainDayAndTimes(new ReserveLeaveRemainingDayNumber(c.getRemainingDays()), Optional.of(new RemainingMinutes(c.getRemainingTimes()))),
				new DayOffRemainDayAndTimes(new ReserveLeaveRemainingDayNumber(c.getCarryforwardDays()), Optional.of(new RemainingMinutes(c.getCarryforwardTimes()))),
				new DayOffDayAndTimes(new AttendanceDaysMonth(c.getUnUsedDays()), Optional.of(new TimeDayoffRemain(c.getUnUsedTimes()))));
	}

}

package nts.uk.ctx.at.record.infra.repository.monthly.vacation.dayoff;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.RemainDataTimesMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.dayoff.KrcdtMonDayoffRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.dayoff.KrcdtMonDayoffRemainPK;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class JpaMonthlyDayoffRemainDataRepository extends JpaRepository implements MonthlyDayoffRemainDataRepository{
	
	private static final String QUERY_BY_SID_YM_STATUS = "SELECT c FROM KrcdtMonDayoffRemain　c "
			+ " WHERE c.pk.sid = :employeeId"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status"
			+ " ORDER BY c.endDate ASC";
	
	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonDayoffRemain a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ym = :yearMonth "
			+ "ORDER BY a.startDate ";
	
	@Override
	public List<MonthlyDayoffRemainData> getDayOffDataBySidYmStatus(String employeeId, YearMonth ym,
			ClosureStatus status) {
		
		return  this.queryProxy().query(QUERY_BY_SID_YM_STATUS, KrcdtMonDayoffRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ym", ym.v())
				.setParameter("status", status.value)
				.getList(c -> toDomain(c));
	}
	
	private MonthlyDayoffRemainData toDomain(KrcdtMonDayoffRemain c) {
		return new MonthlyDayoffRemainData(c.pk.sid,
				YearMonth.of(c.pk.ym),
				c.pk.closureId,
				c.pk.closureDay,
				c.pk.isLastDay == 1 ? true : false,
				EnumAdaptor.valueOf(c.closureStatus, ClosureStatus.class),
				c.startDate,
				c.endDate,
				new DayOffDayAndTimes(new RemainDataDaysMonth(c.occurredDays), Optional.of(new RemainDataTimesMonth(c.occurredTimes))),
				new DayOffDayAndTimes(new RemainDataDaysMonth(c.usedDays), Optional.of(new RemainDataTimesMonth(c.usedTimes))),
				new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(c.remainingDays), Optional.of(new RemainingMinutes(c.remainingTimes))),
				new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(c.carryforwardDays), Optional.of(new RemainingMinutes(c.carryforwardTimes))),
				new DayOffDayAndTimes(new RemainDataDaysMonth(c.unUsedDays), Optional.of(new RemainDataTimesMonth(c.unUsedTimes))));
	}

	@Override
	public List<MonthlyDayoffRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {

		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonDayoffRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> toDomain(c));
	}
	
	@Override
	public void persistAndUpdate(MonthlyDayoffRemainData domain) {
		
		// キー
		val key = new KrcdtMonDayoffRemainPK(
				domain.getSId(),
				domain.getYm().v(),
				domain.getClosureId(),
				domain.getClosureDay(),
				(domain.isLastDayis() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonDayoffRemain entity = this.getEntityManager().find(KrcdtMonDayoffRemain.class, key);
		if (entity == null){
			entity = new KrcdtMonDayoffRemain();
			entity.pk = new KrcdtMonDayoffRemainPK();
			entity.pk.sid = domain.getSId();
			entity.pk.ym = domain.getYm().v();
			entity.pk.closureId = domain.getClosureId();
			entity.pk.closureDay = domain.getClosureDay();
			entity.pk.isLastDay = domain.isLastDayis() ? 1 : 0;
			entity.closureStatus = domain.getClosureStatus().value;
			entity.startDate = domain.getStartDate();
			entity.endDate = domain.getEndDate();
			entity.occurredDays = domain.getOccurrenceDayTimes().getDay().v();
			if (domain.getOccurrenceDayTimes().getTime().isPresent()){
				entity.occurredTimes = domain.getOccurrenceDayTimes().getTime().get().v();
			}
			entity.usedDays = domain.getUseDayTimes().getDay().v();
			if (domain.getUseDayTimes().getTime().isPresent()){
				entity.usedTimes = domain.getUseDayTimes().getTime().get().v();
			}
			entity.remainingDays = domain.getRemainingDayTimes().getDays().v();
			if (domain.getRemainingDayTimes().getTimes().isPresent()){
				entity.remainingTimes = domain.getRemainingDayTimes().getTimes().get().v();
			}
			entity.carryforwardDays = domain.getCarryForWardDayTimes().getDays().v();
			if (domain.getCarryForWardDayTimes().getTimes().isPresent()){
				entity.carryforwardTimes = domain.getCarryForWardDayTimes().getTimes().get().v();
			}
			entity.unUsedDays = domain.getUnUsedDayTimes().getDay().v();
			if (domain.getUnUsedDayTimes().getTime().isPresent()){
				entity.unUsedTimes = domain.getUnUsedDayTimes().getTime().get().v();
			}
			this.getEntityManager().persist(entity);
		}
		else {
			entity.closureStatus = domain.getClosureStatus().value;
			entity.startDate = domain.getStartDate();
			entity.endDate = domain.getEndDate();
			entity.occurredDays = domain.getOccurrenceDayTimes().getDay().v();
			if (domain.getOccurrenceDayTimes().getTime().isPresent()){
				entity.occurredTimes = domain.getOccurrenceDayTimes().getTime().get().v();
			}
			entity.usedDays = domain.getUseDayTimes().getDay().v();
			if (domain.getUseDayTimes().getTime().isPresent()){
				entity.usedTimes = domain.getUseDayTimes().getTime().get().v();
			}
			entity.remainingDays = domain.getRemainingDayTimes().getDays().v();
			if (domain.getRemainingDayTimes().getTimes().isPresent()){
				entity.remainingTimes = domain.getRemainingDayTimes().getTimes().get().v();
			}
			entity.carryforwardDays = domain.getCarryForWardDayTimes().getDays().v();
			if (domain.getCarryForWardDayTimes().getTimes().isPresent()){
				entity.carryforwardTimes = domain.getCarryForWardDayTimes().getTimes().get().v();
			}
			entity.unUsedDays = domain.getUnUsedDayTimes().getDay().v();
			if (domain.getUnUsedDayTimes().getTime().isPresent()){
				entity.unUsedTimes = domain.getUnUsedDayTimes().getTime().get().v();
			}
		}
	}
	
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.commandProxy().remove(KrcdtMonDayoffRemain.class,
				new KrcdtMonDayoffRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)));
	}
}

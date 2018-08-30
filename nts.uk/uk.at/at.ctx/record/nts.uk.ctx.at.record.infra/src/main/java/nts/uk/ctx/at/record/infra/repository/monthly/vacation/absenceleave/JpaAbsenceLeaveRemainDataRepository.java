package nts.uk.ctx.at.record.infra.repository.monthly.vacation.absenceleave;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave.KrcdtMonSubOfHdRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave.KrcdtMonSubOfHdRemainPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class JpaAbsenceLeaveRemainDataRepository extends JpaRepository implements AbsenceLeaveRemainDataRepository{
	
	private static final String QUERY_BY_SID_YM_STATUS = "SELECT c FROM KrcdtMonSubOfHdRemain c"
			+ " WHERE c.pk.sId = :employeeId"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status"
			+ " ORDER BY c.endDate ASC";
	
	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonSubOfHdRemain a "
			+ "WHERE a.pk.sId = :employeeId "
			+ "AND a.pk.ym = :yearMonth "
			+ "ORDER BY a.startDate ";
	
	@Override
	public List<AbsenceLeaveRemainData> getDataBySidYmClosureStatus(String employeeId, YearMonth ym,
			ClosureStatus status) {
		return  this.queryProxy().query(QUERY_BY_SID_YM_STATUS, KrcdtMonSubOfHdRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ym", ym.v())
				.setParameter("status", status.value)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public List<AbsenceLeaveRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonSubOfHdRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> toDomain(c));
	}

	private AbsenceLeaveRemainData toDomain(KrcdtMonSubOfHdRemain c) {
		return new AbsenceLeaveRemainData(c.pk.sId,
				YearMonth.of(c.pk.ym),
				c.pk.closureId,
				c.pk.closureDay, 
				c.pk.isLastDay == 1 ? true : false,
				EnumAdaptor.valueOf(c.closureStatus, ClosureStatus.class),
				c.startDate,
				c.endDate,
				new RemainDataDaysMonth(c.occurredDays),
				new RemainDataDaysMonth(c.usedDays),
				new AttendanceDaysMonthToTal(c.remainingDays),
				new AttendanceDaysMonthToTal(c.carryForWardDays),
				new RemainDataDaysMonth(c.unUsedDays));
	}

	@Override
	public void persistAndUpdate(AbsenceLeaveRemainData domain) {
		// キー
		val key = new KrcdtMonSubOfHdRemainPK(
				domain.getSId(),
				domain.getYm().v(),
				domain.getClosureId(),
				domain.getClosureDay(),
				(domain.isLastDayIs() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonSubOfHdRemain entity = this.getEntityManager().find(KrcdtMonSubOfHdRemain.class, key);
		if (entity == null){
			entity = new KrcdtMonSubOfHdRemain();
			entity.pk = new KrcdtMonSubOfHdRemainPK();
			entity.pk.sId = domain.getSId();
			entity.pk.ym = domain.getYm().v();
			entity.pk.closureId = domain.getClosureId();
			entity.pk.closureDay = domain.getClosureDay();
			entity.pk.isLastDay = domain.isLastDayIs() ? 1 : 0;
			entity.closureStatus = domain.getClosureStatus().value;
			entity.startDate = domain.getStartDate();
			entity.endDate = domain.getEndDate();
			entity.occurredDays = domain.getOccurredDay().v();
			entity.usedDays = domain.getUsedDays().v();
			entity.remainingDays = domain.getRemainingDays().v();
			entity.carryForWardDays = domain.getCarryforwardDays().v();
			entity.unUsedDays = domain.getUnUsedDays().v();
			this.getEntityManager().persist(entity);
		}
		else {
			entity.closureStatus = domain.getClosureStatus().value;
			entity.startDate = domain.getStartDate();
			entity.endDate = domain.getEndDate();
			entity.occurredDays = domain.getOccurredDay().v();
			entity.usedDays = domain.getUsedDays().v();
			entity.remainingDays = domain.getRemainingDays().v();
			entity.carryForWardDays = domain.getCarryforwardDays().v();
			entity.unUsedDays = domain.getUnUsedDays().v();
		}
	}
	
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.commandProxy().remove(KrcdtMonSubOfHdRemain.class,
				new KrcdtMonSubOfHdRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)));
	}
}

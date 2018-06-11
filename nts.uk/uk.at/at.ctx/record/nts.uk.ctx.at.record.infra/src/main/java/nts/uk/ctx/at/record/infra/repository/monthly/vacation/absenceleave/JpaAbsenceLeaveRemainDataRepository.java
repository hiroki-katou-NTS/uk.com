package nts.uk.ctx.at.record.infra.repository.monthly.vacation.absenceleave;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainDataRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave.KrcdtMonSubOfHdRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave.KrcdtMonSubOfHdRemainPK;

@Stateless
public class JpaAbsenceLeaveRemainDataRepository extends JpaRepository implements AbsenceLeaveRemainDataRepository{
	
	private static final String QUERY_BY_SID_YM_STATUS = "SELECT c FROM KrcdtMonSubOfHdRemain c"
			+ " WHERE c.pk.sId = :employeeId"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status"
			+ " ORDER BY c.endDate ASC";
	
	@Override
	public List<AbsenceLeaveRemainData> getDataBySidYmClosureStatus(String employeeId, YearMonth ym,
			ClosureStatus status) {
		return  this.queryProxy().query(QUERY_BY_SID_YM_STATUS, KrcdtMonSubOfHdRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("ym", ym.v())
				.setParameter("closureStatus", status.value)
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
				new AttendanceDaysMonth(c.occurredDays),
				new AttendanceDaysMonth(c.usedDays),
				new AttendanceDaysMonth(c.remainingDays),
				new AttendanceDaysMonth(c.carryForWardDays),
				new AttendanceDaysMonth(c.unUsedDays));
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
}

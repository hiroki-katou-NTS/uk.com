package nts.uk.ctx.at.record.infra.repository.divergencetime;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergencetime.AttendanceItem;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.record.infra.entity.divergencetime.KmkmtDivergenceReason;
import nts.uk.ctx.at.record.infra.entity.divergencetime.KmnmtAttendanceItemSet;
import nts.uk.ctx.at.record.infra.entity.divergencetime.KmnmtDivergenceTime;
import nts.uk.ctx.at.record.infra.entity.divergencetime.KmnmtDivergenceTimePK;

@Stateless
public class JpaDivergenceTimeRepository extends JpaRepository implements DivergenceTimeRepository {

	private final String SELECT_FROM_DIVTIME = "SELECT c FROM KmnmtDivergenceTime c";
	private final String SELECT_ALL_DIVTIME = SELECT_FROM_DIVTIME 
			+ " WHERE c.kmnmtDivergenceTimePK.companyId = :companyId";
	private final String SELECT_FROM_DIVREASON = "SELECT c FROM KmkmtDivergenceReason c";
	private final String SELECT_ALL_DIVREASON = SELECT_FROM_DIVREASON
			+ " WHERE c.kmkmtDivergenceReasonPK.companyId = :companyId"
			+ " AND c.kmkmtDivergenceReasonPK.divTimeId = :divTimeId";
	private final String SELECT_FROM_ATTENDANCEITEM = "SELCECT c FROM KmnmtAttendanceItemSet c";
	private final String SELECT_ALL_ATTENDANTIEM = SELECT_FROM_ATTENDANCEITEM
			+ " WHERE c.kmnmtAttendanceItemSetPK.companyId = :companyId"
			+ " AND c.kmnmtAttendanceItemSetPK.divTimeId = :divTimeId";
	
	private static DivergenceTime toDomainDivTime(KmnmtDivergenceTime entity){
		val domain = DivergenceTime.createSimpleFromJavaType(
				entity.kmnmtDivergenceTimePK.companyId,
				entity.kmnmtDivergenceTimePK.divTimeId,
				entity.divTimeUseSet,
				entity.alarmTime,
				entity.errTime,
				entity.selectUseSet,
				entity.cancelErrSelReason,
				entity.inputUseSet,
				entity.cancelErrInputReason);
		return domain;
	}
	private static DivergenceReason toDomainDivReason(KmkmtDivergenceReason entity){
		val domain = DivergenceReason.createSimpleFromJavaType(
				entity.kmkmtDivergenceReasonPK.companyId,
				entity.kmkmtDivergenceReasonPK.divTimeId,
				entity.kmkmtDivergenceReasonPK.divReasonCode,
				entity.divReason,
				entity.requiredAtr);
		return domain;
	}
	private static AttendanceItem toDomainAttendanceItem(KmnmtAttendanceItemSet entity){
		val domain = AttendanceItem.createSimpleFromJavaType(
				entity.kmnmtAttendanceItemSetPK.companyId,
				entity.kmnmtAttendanceItemSetPK.divTimeId,
				entity.kmnmtAttendanceItemSetPK.attendanceId);
		return domain;
	}
	private static KmnmtDivergenceTime toEntityDivTime(DivergenceTime domain){
		val entity = new KmnmtDivergenceTime();
		entity.kmnmtDivergenceTimePK = new KmnmtDivergenceTimePK(
												domain.getCompanyId(),
												domain.getDivTimeId());
		entity.alarmTime = Integer.valueOf(domain.getAlarmTime().toString());
		entity.errTime = Integer.valueOf(domain.getErrTime().toString());
		entity.selectUseSet = Integer.valueOf(domain.getSelectSet().getSelectUseSet().toString());
		return entity;
	}
	/**
	 * get all divergence time
	 * @param companyId
	 * @return
	 */
	@Override
	public List<DivergenceTime> getAllDivTime(String companyId) {
		return this.queryProxy().query(SELECT_ALL_DIVTIME, KmnmtDivergenceTime.class)
				.setParameter("companyId", companyId)
				.getList(c->toDomainDivTime(c));
	}
	/**
	 * get all divergence reason
	 * @param companyId
	 * @param divTimeId
	 * @return
	 */
	@Override
	public List<DivergenceReason> getDivReasonByCode(String companyId, int divTimeId) {
		return this.queryProxy().query(SELECT_ALL_DIVREASON, KmkmtDivergenceReason.class)
				.setParameter("companyId", companyId)
				.setParameter("divTimeId", divTimeId)
				.getList(c->toDomainDivReason(c));
	}
	/**
	 * get all attendance item
	 * @param companyId
	 * @param divTimeCode
	 * @return
	 */
	@Override
	public List<AttendanceItem> getallItembyCode(String companyId, String divTimeId) {
		return this.queryProxy().query(SELECT_ALL_ATTENDANTIEM, KmnmtAttendanceItemSet.class)
				.setParameter("companyId", companyId)
				.setParameter("divTimeId", divTimeId)
				.getList(c->toDomainAttendanceItem(c));
	}
	/**
	 * update divergence time
	 * @param divTime
	 */
	@Override
	public void updateDivTime(DivergenceTime divTime) {
		this.commandProxy().update(toEntityDivTime(divTime));
	}
	/**
	 * add divergence reason
	 * @param divReason
	 */
	@Override
	public void addDivReason(DivergenceReason divReason) {
		
	}
	/**
	 * update divergence reason
	 * @param divReason
	 */
	@Override
	public void updateDivReason(DivergenceReason divReason) {
		
	}
	/**
	 * delete divergence reason
	 * @param divReason
	 */
	@Override
	public void deleteDivReason(DivergenceReason divReason) {
		
	}

}

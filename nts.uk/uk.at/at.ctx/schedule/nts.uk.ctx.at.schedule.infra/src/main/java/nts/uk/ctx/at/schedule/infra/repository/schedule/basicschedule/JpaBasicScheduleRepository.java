package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KsuppBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KsuptBasicSchedule;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
@Transactional
public class JpaBasicScheduleRepository extends JpaRepository implements BasicScheduleRepository {

//	private static final String SEL = "SELECT c FROM KsuptBasicSchedule c ";
//	private static final String SEL_BY_LIST_SID_AND_DATE = SEL
//			+ "WHERE c.ksuppBSchedulePK.sId IN :sId AND c.ksuppBSchedulePK.date >= :startDate AND c.ksuppBSchedulePK.date <= :endDate "
//			+ "ORDER BY c.ksuppBSchedulePK.date ASC";

	/**
	 * Convert Domain to Entity
	 * 
	 * @param domain
	 * @return
	 */
	private static KsuptBasicSchedule toEntity(BasicSchedule domain) {
		val entity = new KsuptBasicSchedule();

		entity.ksuppBSchedulePK = new KsuppBasicSchedulePK(domain.getSId(), domain.getDate());
		entity.workTimeCd = domain.getWorkTimeCd().v();
		entity.workTypeCd = domain.getWorkTypeCd().v();
		return entity;
	}

	/**
	 * Convert Entity to Domain
	 * 
	 * @param entity
	 * @return
	 */
	private static BasicSchedule toDomain(KsuptBasicSchedule entity) {
		val domain = BasicSchedule.createFromJavaType(entity.ksuppBSchedulePK.sId, entity.ksuppBSchedulePK.date,
				entity.workTypeCd, entity.workTimeCd);
		return domain;
	}

	/**
	 * Get data from BasicSchedule base on list Sid, startDate and endDate
	 */
//	@Override
//	public List<BasicSchedule> getByListSidAndDate(List<String> sId, GeneralDate startDate, GeneralDate endDate) {
//		return this.queryProxy().query(SEL_BY_LIST_SID_AND_DATE, KsuptBasicSchedule.class).setParameter("sId", sId)
//				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(x -> toDomain(x));
//	}

	/**
	 * Insert data to Basic Schedule
	 * 
	 * @param bSchedule
	 */
	@Override
	public void insertBSchedule(BasicSchedule bSchedule) {
		this.commandProxy().insert(toEntity(bSchedule));
	}

	/**
	 * update data to Basic Schedule
	 * 
	 * @param bSchedule
	 */
	@Override
	public void updateBSchedule(BasicSchedule bSchedule) {
		KsuppBasicSchedulePK pk = new KsuppBasicSchedulePK(bSchedule.getSId(), bSchedule.getDate());
		KsuptBasicSchedule entity = new KsuptBasicSchedule();
		entity.ksuppBSchedulePK = pk;
		entity.workTimeCd = bSchedule.getWorkTimeCd().v();
		entity.workTypeCd = bSchedule.getWorkTypeCd().v();

		this.commandProxy().update(entity);
	}

	/**
	 * Get BasicSchedule
	 */
	@Override
	public Optional<BasicSchedule> getByPK(String sId, GeneralDate date) {
		return this.queryProxy().find(new KsuppBasicSchedulePK(sId, date), KsuptBasicSchedule.class)
				.map(x -> toDomain(x));
	}
}

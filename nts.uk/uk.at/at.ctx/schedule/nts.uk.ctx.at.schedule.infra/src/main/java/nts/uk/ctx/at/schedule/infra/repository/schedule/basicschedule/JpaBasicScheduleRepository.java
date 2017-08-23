package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdpBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
@Transactional
public class JpaBasicScheduleRepository extends JpaRepository implements BasicScheduleRepository {

	/**
	 * Convert Domain to Entity
	 * 
	 * @param domain
	 * @return
	 */
	private static KscdtBasicSchedule toEntity(BasicSchedule domain) {
		val entity = new KscdtBasicSchedule();

		entity.kscdpBSchedulePK = new KscdpBasicSchedulePK(domain.getSId(), domain.getDate());
		entity.workTimeCode = domain.getWorkTimeCode();
		entity.workTypeCode = domain.getWorkTypeCode();
		return entity;
	}

	/**
	 * Convert Entity to Domain
	 * 
	 * @param entity
	 * @return
	 */
	private static BasicSchedule toDomain(KscdtBasicSchedule entity) {
		val domain = BasicSchedule.createFromJavaType(entity.kscdpBSchedulePK.sId, entity.kscdpBSchedulePK.date,
				entity.workTypeCode, entity.workTimeCode);
		return domain;
	}

	/**
	 * Insert data to Basic Schedule
	 * 
	 * @param bSchedule
	 */
	@Override
	public void insert(BasicSchedule bSchedule) {
		this.commandProxy().insert(toEntity(bSchedule));
	}

	/**
	 * update data to Basic Schedule
	 * 
	 * @param bSchedule
	 */
	@Override
	public void update(BasicSchedule bSchedule) {
		KscdpBasicSchedulePK pk = new KscdpBasicSchedulePK(bSchedule.getSId(), bSchedule.getDate());
		KscdtBasicSchedule entity = new KscdtBasicSchedule();
		entity.kscdpBSchedulePK = pk;
		entity.workTimeCode = bSchedule.getWorkTimeCode();
		entity.workTypeCode = bSchedule.getWorkTypeCode();

		this.commandProxy().update(entity);
	}

	/**
	 * Get BasicSchedule
	 */
	@Override
	public Optional<BasicSchedule> find(String sId, GeneralDate date) {
		return this.queryProxy().find(new KscdpBasicSchedulePK(sId, date), KscdtBasicSchedule.class)
				.map(x -> toDomain(x));
	}
}

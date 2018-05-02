/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK;

/**
 * The Class JpaEmpTransLaborTimeRepository.
 */
@Stateless
public class JpaEmpTransLaborTimeRepository extends JpaRepository implements EmpTransWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void add(EmpTransLaborTime domain) {
		KshstEmpTransLabTime entity = new KshstEmpTransLabTime();
		domain.saveToMemento(new JpaEmpTransLaborTimeSetMemento(entity));
		commandProxy().insert(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void update(EmpTransLaborTime domain) {
		KshstEmpTransLabTime entity = this.queryProxy()
				.find(new KshstEmpTransLabTimePK(domain.getCompanyId().v(),
						domain.getEmploymentCode().v()), KshstEmpTransLabTime.class).get();
		domain.saveToMemento(new JpaEmpTransLaborTimeSetMemento(entity));
		commandProxy().update(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void delete(String cid, String emplId) {
		commandProxy().remove(KshstEmpTransLabTime.class, new KshstEmpTransLabTimePK(cid, emplId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#findByCidAndEmplId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmpTransLaborTime> find(String cid, String emplId) {
		Optional<KshstEmpTransLabTime> optEntity = this.queryProxy().find(new KshstEmpTransLabTimePK(cid, emplId), KshstEmpTransLabTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp trans work time
	 */
	private EmpTransLaborTime toDomain(KshstEmpTransLabTime entity) {
		return new EmpTransLaborTime(new JpaEmpTransLaborTimeGetMemento(entity));
	}

}

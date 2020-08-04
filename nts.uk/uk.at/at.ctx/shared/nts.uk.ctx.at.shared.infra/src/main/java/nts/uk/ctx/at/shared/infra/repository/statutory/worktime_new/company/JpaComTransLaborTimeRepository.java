/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaComTransLaborTimeRepository.
 */
@Stateless
public class JpaComTransLaborTimeRepository extends JpaRepository
		implements ComTransLaborTimeRepository {

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComTransLaborTime)
	 */
	@Override
	public void create(ComTransLaborTime setting) {
		KshstComTransLabTime entity = new KshstComTransLabTime();
		setting.saveToMemento(new JpaComTransLaborTimeSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComTransLaborTime)
	 */
	@Override
	public void update(ComTransLaborTime setting) {
		KshstComTransLabTime entity = this.queryProxy().find(setting.getCompanyId().v(), KshstComTransLabTime.class).get();
		setting.saveToMemento(new JpaComTransLaborTimeSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KshstComTransLabTime.class, companyId);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborTimeRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComTransLaborTime> find(String companyId) {

		Optional<KshstComTransLabTime> optEntity = this.queryProxy().find(companyId,
				KshstComTransLabTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entities
	 * @return the com trans labor time
	 */
	private ComTransLaborTime toDomain(KshstComTransLabTime entity) {
		return new ComTransLaborTime(new JpaComTransLaborTimeGetMemento(entity));
	}
}

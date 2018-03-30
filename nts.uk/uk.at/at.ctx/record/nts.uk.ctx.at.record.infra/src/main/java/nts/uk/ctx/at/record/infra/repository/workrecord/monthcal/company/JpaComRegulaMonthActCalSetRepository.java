/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComRegMCalSet;

/**
 * The Class JpaComRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaComRegulaMonthActCalSetRepository extends JpaRepository
		implements ComRegulaMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComRegulaMonthActCalSet> find(String companyId) {
		// Get info
		Optional<KrcstComRegMCalSet> optEntity = this.queryProxy().find(companyId,
				KrcstComRegMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ComRegulaMonthActCalSet(
				new JpaComRegulaMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.company.ComRegulaMonthActCalSet)
	 */
	@Override
	public void add(ComRegulaMonthActCalSet domain) {
		// Create new entity
		KrcstComRegMCalSet entity = new KrcstComRegMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaComRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.company.ComRegulaMonthActCalSet)
	 */
	@Override
	public void update(ComRegulaMonthActCalSet domain) {
		// Get info
		Optional<KrcstComRegMCalSet> optEntity = this.queryProxy().find(domain.getCompanyId().v(),
				KrcstComRegMCalSet.class);

		KrcstComRegMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaComRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String cId) {
		this.commandProxy().remove(KrcstComRegMCalSet.class, cId);
	}

}

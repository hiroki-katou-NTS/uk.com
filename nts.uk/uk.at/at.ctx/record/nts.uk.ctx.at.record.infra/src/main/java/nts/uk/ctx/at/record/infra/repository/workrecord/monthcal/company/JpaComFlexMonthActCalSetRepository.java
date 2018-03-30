/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComFlexMCalSet;

/**
 * The Class JpaComFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaComFlexMonthActCalSetRepository extends JpaRepository implements ComFlexMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComFlexMonthActCalSet> find(String companyId) {
		// Get info
		Optional<KrcstComFlexMCalSet> optEntity = this.queryProxy().find(companyId, KrcstComFlexMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ComFlexMonthActCalSet(new JpaComFlexMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.company.ComFlexMonthActCalSet)
	 */
	@Override
	public void add(ComFlexMonthActCalSet domain) {
		// Create new entity
		KrcstComFlexMCalSet entity = new KrcstComFlexMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaComFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.company.ComFlexMonthActCalSet)
	 */
	@Override
	public void update(ComFlexMonthActCalSet domain) {
		// Get info
		Optional<KrcstComFlexMCalSet> optEntity = this.queryProxy().find(domain.getCompanyId().v(),
				KrcstComFlexMCalSet.class);

		KrcstComFlexMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaComFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KrcstComFlexMCalSet.class, companyId);
	}

}

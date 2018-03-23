/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComDeforMCalSet;

/**
 * The Class JpaComDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaComDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ComDeforLaborMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComDeforLaborMonthActCalSet> find(String companyId) {
		// Get info
		Optional<KrcstComDeforMCalSet> optEntity = this.queryProxy().find(companyId,
				KrcstComDeforMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ComDeforLaborMonthActCalSet(
				new JpaComDeforLaborMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.company.ComDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(ComDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcstComDeforMCalSet entity = new KrcstComDeforMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaComDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.company.ComDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(ComDeforLaborMonthActCalSet domain) {
		// Get info
		Optional<KrcstComDeforMCalSet> optEntity = this.queryProxy().find(domain.getCompanyId().v(),
				KrcstComDeforMCalSet.class);

		KrcstComDeforMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaComDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KrcstComDeforMCalSet.class, companyId);
	}

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSetPK;

/**
 * The Class JpaWkpDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements WkpDeforLaborMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(WkpDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcstWkpDeforMCalSet entity = new KrcstWkpDeforMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaWkpDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(WkpDeforLaborMonthActCalSet domain) {
		// Get info
		KrcstWkpDeforMCalSetPK pk = new KrcstWkpDeforMCalSetPK(domain.getCompanyId().toString(),
				domain.getWorkplaceId().toString());
		Optional<KrcstWkpDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpDeforMCalSet.class);

		KrcstWkpDeforMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaWkpDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WkpDeforLaborMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcstWkpDeforMCalSetPK pk = new KrcstWkpDeforMCalSetPK(cid, wkpId);
		Optional<KrcstWkpDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpDeforMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new WkpDeforLaborMonthActCalSet(new JpaWkpDeforLaborMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		Optional<KrcstWkpDeforMCalSet> optEntity = this.queryProxy().find(new KrcstWkpDeforMCalSetPK(cid, wkpId),
				KrcstWkpDeforMCalSet.class);
		KrcstWkpDeforMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}

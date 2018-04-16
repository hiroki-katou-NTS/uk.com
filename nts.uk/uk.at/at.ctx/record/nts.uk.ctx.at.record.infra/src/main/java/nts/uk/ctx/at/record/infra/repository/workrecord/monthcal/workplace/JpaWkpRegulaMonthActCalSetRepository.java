/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpRegMCalSetPK;

/**
 * The Class JpaWkpRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpRegulaMonthActCalSetRepository extends JpaRepository implements WkpRegulaMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.workplace.WkpRegulaMonthActCalSet)
	 */
	@Override
	public void add(WkpRegulaMonthActCalSet domain) {
		// Create new entity
		KrcstWkpRegMCalSet entity = new KrcstWkpRegMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaWkpRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpRegulaMonthActCalSet)
	 */
	@Override
	public void update(WkpRegulaMonthActCalSet domain) {
		// Get info
		KrcstWkpRegMCalSetPK pk = new KrcstWkpRegMCalSetPK(domain.getCompanyId().toString(),
				domain.getWorkplaceId().toString());
		Optional<KrcstWkpRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpRegMCalSet.class);

		KrcstWkpRegMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaWkpRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WkpRegulaMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcstWkpRegMCalSetPK pk = new KrcstWkpRegMCalSetPK(cid, wkpId);
		Optional<KrcstWkpRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpRegMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new WkpRegulaMonthActCalSet(new JpaWkpRegulaMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		Optional<KrcstWkpRegMCalSet> optEntity = this.queryProxy().find(new KrcstWkpRegMCalSetPK(cid, wkpId),
				KrcstWkpRegMCalSet.class);
		KrcstWkpRegMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}

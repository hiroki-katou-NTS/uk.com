/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSetPK;

/**
 * The Class JpaWkpFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpFlexMonthActCalSetRepository extends JpaRepository implements WkpFlexMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void add(WkpFlexMonthActCalSet domain) {
		// Create new entity
		KrcstWkpFlexMCalSet entity = new KrcstWkpFlexMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaWkpFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void update(WkpFlexMonthActCalSet domain) {
		// Get info
		KrcstWkpFlexMCalSetPK pk = new KrcstWkpFlexMCalSetPK(domain.getCompanyId().toString(),
				domain.getWorkplaceId().toString());
		Optional<KrcstWkpFlexMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpFlexMCalSet.class);

		KrcstWkpFlexMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaWkpFlexMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpFlexMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcstWkpFlexMCalSetPK pk = new KrcstWkpFlexMCalSetPK(cid, wkpId);
		Optional<KrcstWkpFlexMCalSet> optEntity = this.queryProxy().find(pk, KrcstWkpFlexMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new WkpFlexMonthActCalSet(new JpaWkpFlexMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		Optional<KrcstWkpFlexMCalSet> optEntity = this.queryProxy().find(new KrcstWkpFlexMCalSetPK(cid, wkpId),
				KrcstWkpFlexMCalSet.class);
		KrcstWkpFlexMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSetPK;

/**
 * The Class JpaShaRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaShaRegulaMonthActCalSetRepository extends JpaRepository implements ShaRegulaMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void add(ShaRegulaMonthActCalSet domain) {
		// Create new entity
		KrcstShaRegMCalSet entity = new KrcstShaRegMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaShaRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void update(ShaRegulaMonthActCalSet domain) {
		// Get info
		KrcstShaRegMCalSetPK pk = new KrcstShaRegMCalSetPK(domain.getCompanyId().toString(),
				domain.getEmployeeId().toString());
		Optional<KrcstShaRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstShaRegMCalSet.class);

		KrcstShaRegMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaShaRegulaMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<ShaRegulaMonthActCalSet> find(String cid, String sid) {
		// Get info
		KrcstShaRegMCalSetPK pk = new KrcstShaRegMCalSetPK(cid, sid);
		Optional<KrcstShaRegMCalSet> optEntity = this.queryProxy().find(pk, KrcstShaRegMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ShaRegulaMonthActCalSet(new JpaShaRegulaMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String sId) {
		Optional<KrcstShaRegMCalSet> optEntity = this.queryProxy().find(new KrcstShaRegMCalSetPK(cid, sId),
				KrcstShaRegMCalSet.class);
		KrcstShaRegMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);
	}

}

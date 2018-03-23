/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSetPK;

/**
 * The Class JpaShaDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaShaDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ShaDeforLaborMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(ShaDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcstShaDeforMCalSet entity = new KrcstShaDeforMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaShaDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(ShaDeforLaborMonthActCalSet domain) {
		// Get info
		KrcstShaDeforMCalSetPK pk = new KrcstShaDeforMCalSetPK(domain.getCompanyId().toString(),
				domain.getEmployeeId().toString());
		Optional<KrcstShaDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstShaDeforMCalSet.class);

		KrcstShaDeforMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaShaDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<ShaDeforLaborMonthActCalSet> find(String cid, String empId) {
		// Get info
		KrcstShaDeforMCalSetPK pk = new KrcstShaDeforMCalSetPK(cid, empId);
		Optional<KrcstShaDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstShaDeforMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new ShaDeforLaborMonthActCalSet(new JpaShaDeforLaborMonthActCalSetGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cId, String sId) {
		Optional<KrcstShaDeforMCalSet> optEntity = this.queryProxy().find(new KrcstShaDeforMCalSetPK(cId, sId),
				KrcstShaDeforMCalSet.class);
		KrcstShaDeforMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}

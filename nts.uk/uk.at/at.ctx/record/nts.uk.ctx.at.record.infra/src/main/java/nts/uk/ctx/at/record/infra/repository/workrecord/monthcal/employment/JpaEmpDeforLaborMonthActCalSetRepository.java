/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSetPK;

/**
 * The Class JpaEmpDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements EmpDeforLaborMonthActCalSetRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(EmpDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcstEmpDeforMCalSet entity = new KrcstEmpDeforMCalSet();

		// Transfer data
		domain.saveToMemento(new JpaEmpDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(EmpDeforLaborMonthActCalSet domain) {
		// Get info
		KrcstEmpDeforMCalSetPK pk = new KrcstEmpDeforMCalSetPK(domain.getCompanyId().toString(),
				domain.getEmploymentCode().toString());
		Optional<KrcstEmpDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpDeforMCalSet.class);

		KrcstEmpDeforMCalSet entity = optEntity.get();

		// Transfer data
		domain.saveToMemento(new JpaEmpDeforLaborMonthActCalSetSetMemento(entity));

		// Insert into DB
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<EmpDeforLaborMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcstEmpDeforMCalSetPK pk = new KrcstEmpDeforMCalSetPK(cid, empCode);
		Optional<KrcstEmpDeforMCalSet> optEntity = this.queryProxy().find(pk, KrcstEmpDeforMCalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new EmpDeforLaborMonthActCalSet(new JpaEmpDeforLaborMonthActCalSetGetMemento(optEntity.get())));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		Optional<KrcstEmpDeforMCalSet> optEntity = this.queryProxy().find(new KrcstEmpDeforMCalSetPK(cid, empCode),
				KrcstEmpDeforMCalSet.class);
		KrcstEmpDeforMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

}
